package com.soydasm.taskmanagement.config;

import com.soydasm.taskmanagement.controllers.CacheController;
import com.soydasm.taskmanagement.model.grant.Grant;
import com.soydasm.taskmanagement.model.grant.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomSimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{

    @Autowired
    CacheController cacheController;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException
    {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        List<Role> roleList = cacheController.getAllRoles();

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/unauthorized.html";
        for (final GrantedAuthority grantedAuthority : authorities)
        {
            String authorityName = grantedAuthority.getAuthority();
            String requestMethod = request.getMethod();
            String requestUrl = request.getRequestURI();
            Optional<Role> optionalRole = roleList.stream()
                    .filter(role1 -> role1.getName().equals(authorityName))
                    .findFirst();
            if(optionalRole.isPresent())
            {
                if(optionalRole.get().getGrants() != null)
                {
                    Optional<Grant> optionalGrant = optionalRole.get().getGrants().stream().filter(grant ->
                    {
                        if(grant.getId() != null && grant.getEndPoint() != null && grant.getEndPoint().getId() != null && requestMethod.equals(grant.getOperation().getName()))
                        {
                            String grantedUrl = "https://" + grant.getEndPoint().getDomain() +  ":" + grant.getEndPoint().getPort() + grant.getEndPoint().getSuffix();
                            if(requestUrl.equals(grantedUrl))
                            {
                                return true;
                            }
                        }
                        return false;
                    }).findFirst();

                    if(optionalGrant.isPresent())
                    {
                        redirectUrl = optionalGrant.get().getEndPoint().getSuffix();
                    }
                }
            }
        }

        return redirectUrl;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}

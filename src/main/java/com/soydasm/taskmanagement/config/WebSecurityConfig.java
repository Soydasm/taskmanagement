package com.soydasm.taskmanagement.config;

import com.soydasm.taskmanagement.service.AuthenticationFacade;
import com.soydasm.taskmanagement.service.UserService;
import com.soydasm.taskmanagement.service.impl.AuthenticationFacadeImpl;
import com.soydasm.taskmanagement.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
//@EnableWebSecurity
//@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers( "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
                //Handled by the SecurityRestConfig so permit from here
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/plan-overview")
                .successForwardUrl("/")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }


    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService)
    {
        return new CustomUserDetailsServiceImpl(userService);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFacade authenticationFacade() {

        return new AuthenticationFacadeImpl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                .antMatchers("swagger-ui.html", "/webjars/**",
                        "/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/h2-console/**");
    }
}
package com.soydasm.taskmanagement.model.dto;/*
package model.dto;

import model.User;
import model.grant.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsDTO implements UserDetails
{
    private String userName;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public UserDetailsDTO(User user)
    {
        this.userName = user.getUsername();
        this.password = user.getPassword();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(UserRole userRole : user.getUserRoles())
        {
            authorityList.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        this.authorities = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
*/

package com.soydasm.taskmanagement.service.impl;

import com.soydasm.taskmanagement.model.User;
import com.soydasm.taskmanagement.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public class CustomUserDetailsServiceImpl implements UserDetailsService
{

    private UserService userService;



    public CustomUserDetailsServiceImpl(UserService _userService)
    {
        if(_userService == null)
        {
            this.userService = _userService;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {

        Optional<User> optionalUser = userService.findByUserName(userName);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true,
                true, user.getAuthorities());
    }

}

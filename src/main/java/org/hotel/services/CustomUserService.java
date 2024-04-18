package org.hotel.services;

import org.hotel.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel byUsername = userService.findByUsername(username);
        return User.builder()
                .username(byUsername.getUsername())
                .password(byUsername.getPassword())
                .roles(byUsername.getRole().name())
                .build();
    }
}

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
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserModel byLogin = userService.findByLogin(login);
        return User.builder()
                .username(byLogin.getLogin())
                .password(byLogin.getPassword())
                .roles(byLogin.getRole())
                .build();
    }
}

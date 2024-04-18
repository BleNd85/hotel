package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.UserModel;
import org.hotel.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public UserModel registerUser(String login, String password, String email) {
        if (login == null || password == null) {
            return null;
        } else {
            if (userRepository.findFirstByLogin(login).isPresent()) {
                return null;
            }
            if (userRepository.findFirstByEmail(email).isPresent()) {
                return null;
            }
            UserModel userModel = new UserModel();
            userModel.setLogin(login);
            userModel.setPassword(bCryptPasswordEncoder.encode(password));
            userModel.setEmail(email);
            userModel.setRole("USER");
            return userRepository.save(userModel);
        }
    }

    public UserModel findByLogin(String login) throws UsernameNotFoundException {
        return userRepository.findFirstByLogin(login).orElse(null);
    }
}

package org.hotel.services;

//import jakarta.annotation.PostConstruct;

import jakarta.transaction.Transactional;
import org.hotel.models.RoleModel;
import org.hotel.models.UserModel;
import org.hotel.repositories.UserRepository;
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
            if (userRepository.findFirstByUsername(login).isPresent()) {
                return null;
            }
            if (userRepository.findFirstByEmail(email).isPresent()) {
                return null;
            }
            UserModel userModel = new UserModel();
            userModel.setUsername(login);
            userModel.setPassword(bCryptPasswordEncoder.encode(password));
            userModel.setEmail(email);
            userModel.setRole(RoleModel.USER);
            return userRepository.save(userModel);
        }
    }
    //delete on release
//    @PostConstruct
//    public void postConstruct() {
//        UserModel userAdmin = new UserModel();
//        userAdmin.setRole(RoleModel.ADMIN);
//        userAdmin.setLogin("admin");
//        userAdmin.setPassword(bCryptPasswordEncoder.encode("admin"));
//        userRepository.save(userAdmin);
//    }


    public UserModel findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username).orElse(null);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}

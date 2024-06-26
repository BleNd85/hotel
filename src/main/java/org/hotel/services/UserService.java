package org.hotel.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.hotel.models.RoleModel;
import org.hotel.models.UserModel;
import org.hotel.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public UserModel registerUser(String username, String password, String email, String name, String surname) {
        if (username == null || password == null) {
            return null;
        } else {
            if (userRepository.findFirstByUsername(username).isPresent()) {
                return null;
            }
            if (userRepository.findFirstByEmail(email).isPresent()) {
                return null;
            }
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setName(name);
            userModel.setSurname(surname);
            userModel.setPassword(bCryptPasswordEncoder.encode(password));
            userModel.setEmail(email);
            userModel.setRole(RoleModel.USER);
            return userRepository.save(userModel);
        }
    }

    @PostConstruct
    public void postConstruct() {
        if(userRepository.findFirstByUsername("admin").isEmpty()){
            UserModel userAdmin = new UserModel();
            userAdmin.setRole(RoleModel.ADMIN);
            userAdmin.setUsername("admin");
            userAdmin.setPassword(bCryptPasswordEncoder.encode("admin"));
            userAdmin.setEmail("admin@gmail.com");
            userAdmin.setName("Admin");
            userAdmin.setSurname("Adminchenko");
            userRepository.save(userAdmin);
        }
    }


    public UserModel findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username).orElse(null);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public UserModel updateUser(Integer id, String username, String email, String name, String surname) {
        UserModel userModel = userRepository.findById(id).orElse(null);
        if (userModel == null) {
            return null;
        }
        if (userRepository.findFirstByUsername(username).isPresent() && !userModel.getUsername().equals(username)) {
            return null;
        }
        if (userRepository.findFirstByEmail(email).isPresent() && !userModel.getEmail().equals(email)) {
            return null;
        }
        userModel.setUsername(username);
        userModel.setEmail(email);
        userModel.setName(name);
        userModel.setSurname(surname);
        return userRepository.save(userModel);
    }

    public UserModel findById(Integer id) {
        return userRepository.findFirstById(id).orElse(null);
    }
    public List<UserModel> searchUsers(String keyword) {
        return userRepository.findByUsernameContainingOrNameContainingOrSurnameContainingOrEmailContaining(
                keyword, keyword, keyword, keyword);
    }

}

package org.hotel.controllers;

import org.hotel.models.UserModel;
import org.hotel.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user-management/edit")
    public String editUser(@RequestParam Integer userId, @RequestParam String username,
                           @RequestParam String name, @RequestParam String surname,
                           @RequestParam String email, Model model) {
        UserModel updatedUser = userService.updateUser(userId, username, email, name, surname);
        if (updatedUser == null) {
            model.addAttribute("error", "An error occurred while updating the user.");
            return "error_page";
        }
        return "redirect:/user-management";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UserModel());
        return "registration";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/user-management")
    public String getCustomerManagement(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            Model model) {

        List<UserModel> users;

        if (keyword != null && !keyword.isEmpty()) {
            users = userService.searchUsers(keyword);
        } else {
            users = userService.getAll();
        }

        if ("name".equals(sortBy)) {
            users.sort(Comparator.comparing(UserModel::getName));
        } else if ("surname".equals(sortBy)) {
            users.sort(Comparator.comparing(UserModel::getSurname));
        }

        model.addAttribute("users", users);
        return "user_management";
    }

    @PostMapping("/user-management/registerByAdmin")
    public String registerUserFromAdminPanel(@ModelAttribute UserModel userModel) {
        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getEmail(), userModel.getName(), userModel.getSurname());
        return registeredUser == null ? "error_page" : "redirect:/user-management";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel) {
        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getEmail(), userModel.getName(), userModel.getSurname());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @GetMapping("/personal_page")
    public String getPersonalPage(Model model, Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "personal_page";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/home_page";
    }

    @PostMapping("/user-management/delete-user")
    public String deleteUser(@RequestParam Integer userId) {
        userService.deleteUser(userId);
        return "redirect:/user-management";
    }
}

package org.hotel.controllers;

import org.hotel.models.UserModel;
import org.hotel.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public String getCustomerManagement(Model model) {
        List<UserModel> users = userService.getAll();
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
        System.out.println("register request" + userModel);
        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getEmail(), userModel.getName(), userModel.getSurname());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @GetMapping("/personal_page")
    public String getPersonalPage() {
        return "personal_page";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/home_page";
    }
    @PostMapping("/user-management/delete-user")
    public String deleteUser(@RequestParam Integer userId){
        userService.deleteUser(userId);
        return "redirect:/user-management";
    }


}

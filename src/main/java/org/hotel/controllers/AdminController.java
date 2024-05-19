package org.hotel.controllers;

import org.hotel.models.UserModel;
import org.hotel.services.HotelService;
import org.hotel.services.RoomService;
import org.hotel.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final HotelService hotelService;
    private final RoomService roomService;

    public AdminController(UserService userService, HotelService hotelService, RoomService roomService) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping("/adminPanel")
    public String getAdminPanelPage() {
        return "admin_panel";
    }
    @GetMapping("/user-management")
    public String getCustomerManagement(Model model) {
        List<UserModel> users = userService.getAll();
        model.addAttribute("users", users);
        return "user_management";
    }
    @GetMapping("/hotelManagement")
    public <hotelModel> String getHotelManagement(Model model) {
        List<HotelModel> hotels = hotelService.getAll();
        model.addAttribute("hotels", hotels);
        return "hotel_management";
    }
    @GetMapping("/roomManagement")
    public <roomModel> String getRoomManagement(Model model) {
        List<RoomModel> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "room_management";
    }

    @PostMapping("/registerByAdmin")
    public String registerUserFromAdminPanel(@ModelAttribute UserModel userModel) {
        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getEmail(), userModel.getName(), userModel.getSurname());
        return registeredUser == null ? "error_page" : "redirect:/adminPanel";
    }

//    @GetMapping("/deleteUserByAdmin")
//    public String deleteUserByAdmin(@ModelAttribute UserModel userModel) {
//        userService.deleteUser(userModel.getId());
//    }
}

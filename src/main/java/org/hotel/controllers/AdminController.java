package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
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

    public AdminController(UserService userService, HotelService hotelService, RoomService roomService) {
        this.userService = userService;

    }

    @GetMapping("/admin-panel")
    public String getAdminPanelPage() {
        return "admin_panel";
    }

//    @GetMapping("/deleteUserByAdmin")
//    public String deleteUserByAdmin(@ModelAttribute UserModel userModel) {
//        userService.deleteUser(userModel.getId());
//    }
}

package org.hotel.controllers;

import org.hotel.models.RoomModel;
import org.hotel.models.HotelModel;
import org.hotel.services.RoomService;
import org.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/room-management")
    public String getHotelManagement(Model model) {
        List<RoomModel> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "room_management";
    }
}

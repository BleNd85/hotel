package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.services.HotelService;
import org.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;


    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping("/room-management")
    public String getRoomManagement(Model model) {
        List<RoomModel> rooms = roomService.getAll();
        List<HotelModel> hotels = hotelService.getAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("hotels", hotels);
        return "room_management";
    }

    @PostMapping("/room-management/add-room")
    public String addRoom(@ModelAttribute RoomModel roomModel) {
        RoomModel registeredRoom = roomService.addRoom(roomModel.getId(), roomModel.getHotel(), roomModel.getName(),
                roomModel.getPricePerNight(), roomModel.getRoomNumber(), roomModel.getType(), roomModel.getDescription());
        return registeredRoom == null ? "error_page" : "redirect:/room-management";
    }
}

package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.services.HotelService;
import org.hotel.services.RoomService;
import org.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;
    private final UserService userService;


    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService, UserService userService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.userService = userService;
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
    public String addRoom(@ModelAttribute RoomModel roomModel, @RequestParam Integer hotelId) {
        HotelModel hotelModel = hotelService.findById(hotelId);
        roomModel.setHotel(hotelModel);
        RoomModel registeredRoom = roomService.addRoom(roomModel.getHotel(), roomModel.getName(), roomModel.getPricePerNight(),
                roomModel.getRoomNumber(), roomModel.getType(), roomModel.getDescription(), roomModel.getAmountOfPlaces());
        return registeredRoom == null ? "error_page" : "redirect:/room-management";
    }

    // Mapping for user to view all rooms
    @GetMapping("/view-rooms")
    public String viewRooms(Model model) {
        List<RoomModel> rooms = roomService.getAll();
        model.addAttribute("rooms", rooms);
        return "view_rooms";
    }

    // Mapping for user to view room details
    @GetMapping("/room/{id}")
    public String viewRoomDetails(@PathVariable("id") Integer id, Model model, Principal principal) {
        RoomModel room = roomService.findById(id);
        UserModel user = userService.findByUsername(principal.getName());
        if (room != null) {
            model.addAttribute("room", room);
            model.addAttribute("user", user);
            return "view_room_details";
        } else {
            return "error_page"; // Handle the case where the room is not found
        }
    }

    @PostMapping("/room-management/delete-room")
    public String deleteRoom(@RequestParam Integer roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/room-management";
    }
}

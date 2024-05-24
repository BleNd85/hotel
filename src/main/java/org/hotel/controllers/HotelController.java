package org.hotel.controllers;

import org.hotel.models.BookingModel;
import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.services.BookingService;
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
public class HotelController {

    private final HotelService hotelService;
    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public HotelController(HotelService hotelService, RoomService roomService, UserService userService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping("/hotel-management")
    public String getHotelManagement(Model model) {
        List<HotelModel> hotels = hotelService.getAll();
        model.addAttribute("hotels", hotels);
        return "hotel_management";
    }

    @PostMapping("/hotel-management/add-hotel")
    public String addHotel(@ModelAttribute HotelModel hotelModel) {
        HotelModel registeredHotel = hotelService.addHotel(hotelModel.getName(), hotelModel.getDescription(), hotelModel.getLocation());
        return registeredHotel == null ? "error_page" : "redirect:/hotel-management";
    }

    @PostMapping("/hotel-management/delete-hotel")
    public String deleteHotel(@RequestParam Integer hotelId) {
        List<RoomModel> rooms = roomService.findAllByHotel(hotelService.findById(hotelId));
        for (RoomModel roomModel : rooms) {
            roomService.deleteRoom(roomModel.getId());
        }
        hotelService.deleteHotel(hotelId);
        return "redirect:/hotel-management";
    }

    @GetMapping("/hotel/{id}")
    public String viewHotelDetails(@PathVariable("id") Integer id, Model model) {
        HotelModel hotel = hotelService.findById(id);
        List<RoomModel> rooms = roomService.findAllByHotel(hotel);
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        return "view_hotel_details";
    }

    @GetMapping("/view-hotels")
    public String viewHotels(Model model) {
        List<HotelModel> hotels = hotelService.getAll();
        model.addAttribute("hotels", hotels);
        return "view_hotels";
    }
}


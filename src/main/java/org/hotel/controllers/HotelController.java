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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;
    private final RoomService roomService;

    @Autowired
    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
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
}

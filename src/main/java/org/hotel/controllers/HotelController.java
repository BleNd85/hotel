package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel-management")
    public String getHotelManagement(Model model) {
        List<HotelModel> hotels = hotelService.getAll();
        model.addAttribute("hotels", hotels);
        return "hotel_management";
    }
}

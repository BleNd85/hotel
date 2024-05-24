package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/")
@Controller
public class HomeController {
    private final HotelService hotelService;

    public HomeController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        model.addAttribute("amountOfPlace", hotelService.countPlaces());
        model.addAttribute("hotelCount", hotelService.countHotels());
        return "home_page";
    }
}

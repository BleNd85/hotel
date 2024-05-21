package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
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

    @PostMapping("/hotel-management/add-hotel")
    public String addHotel(@ModelAttribute HotelModel hotelModel) {
        HotelModel registeredHotel = hotelService.addHotel(hotelModel.getName(), hotelModel.getDescription(), hotelModel.getLocation());
        return registeredHotel == null ? "error_page" : "redirect:/hotel-management";
    }

    @PostMapping("/hotel-management/delete-hotel")
    public String deleteUser(@RequestParam Integer hotelId) {
        hotelService.deleteHotel(hotelId);
        return "redirect:/hotel-management";
    }

    @GetMapping("/hotel/{id}")
    public String viewHotelDetails(@PathVariable("id") Integer id, Model model) {
        HotelModel hotel = hotelService.findById(id);
        model.addAttribute("hotel", hotel);
        return "view_hotel_details";
    }
    @GetMapping("/view-hotels")
    public String viewHotels(Model model) {
        List<HotelModel> hotels = hotelService.getAll();
        model.addAttribute("hotels", hotels);
        return "view_hotels";
    }
}

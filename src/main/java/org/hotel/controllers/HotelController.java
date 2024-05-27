// HotelController.java
package org.hotel.controllers;

import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.services.HotelService;
import org.hotel.services.RoomService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class HotelController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final HotelService hotelService;
    private final RoomService roomService;

    @Autowired
    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping("/hotel-management")
    public String getHotelManagement(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            Model model) {
        List<HotelModel> hotels;

        if (keyword != null && !keyword.isEmpty()) {
            hotels = hotelService.searchHotels(keyword);
        } else {
            hotels = hotelService.getAll();
        }

        if ("name".equals(sortBy)) {
            hotels.sort(Comparator.comparing(HotelModel::getName));
        } else if ("location".equals(sortBy)) {
            hotels.sort(Comparator.comparing(HotelModel::getLocation));
        }

        model.addAttribute("hotels", hotels);
        return "hotel_management";
    }

    @PostMapping("/hotel-management/add-hotel")
    public String addHotel(@ModelAttribute HotelModel hotelModel, @RequestParam("image") MultipartFile imageFile, Model model) {
        String imagePath = null;
        if (!imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(((Path) filePath).getParent());
                Files.copy(imageFile.getInputStream(), filePath);
                imagePath = "/images/hotelsAndRoomsImages/" + fileName;
                hotelModel.setImagePath(imagePath);
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Error uploading image: " + e.getMessage());
                return "error_page";
            }
        }
        HotelModel registeredHotel = hotelService.addHotel(hotelModel.getName(), hotelModel.getDescription(), hotelModel.getLocation(), hotelModel.getImagePath());
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
    public String viewHotels(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<HotelModel> hotels;
        if (keyword != null && !keyword.isEmpty()) {
            hotels = hotelService.searchHotels(keyword);
        } else {
            hotels = hotelService.getAll();
        }
        model.addAttribute("hotels", hotels);
        return "view_hotels";
    }

    @GetMapping("/hotel-management/hotel-count")
    @ResponseBody
    public long getHotelCount() {
        return hotelService.countHotels();
    }
}

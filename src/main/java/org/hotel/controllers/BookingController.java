package org.hotel.controllers;

import org.hotel.models.BookingModel;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.services.BookingService;
import org.hotel.services.RoomService;
import org.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, RoomService roomService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @GetMapping("/booking-management")
    public String getBookingManagement(Model model) {
        List<BookingModel> bookings = bookingService.getAll();
        model.addAttribute("bookings", bookings);
        return "booking-management";
    }

    @PostMapping("/room/add-booking")
    public String addBooking(@ModelAttribute BookingModel bookingModel, @RequestParam Integer roomId, @RequestParam Integer userId) {
        RoomModel roomModel = roomService.findById(roomId);
        UserModel userModel = userService.findById(userId);
        bookingModel.setRoom(roomModel);
        bookingModel.setUser(userModel);
        BookingModel registeredBooking = bookingService.addBooking(bookingModel.getStartDate(), bookingModel.getEndDate(), bookingModel.getRoom(), bookingModel.getUser());
        return registeredBooking == null ? "error_page" : "view_room_details";
    }

    @PostMapping("/booking-management/delete-booking")
    public String deleteBooking(@RequestParam Integer bookingId) {
        bookingService.deleteBooking(bookingId);
        return "redirect:/booking-management";
    }
}

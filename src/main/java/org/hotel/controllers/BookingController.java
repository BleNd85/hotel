package org.hotel.controllers;

import org.hotel.models.BookingModel;
import org.hotel.models.BookingStatus;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.services.BookingService;
import org.hotel.services.RoomService;
import org.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import javax.xml.crypto.Data;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
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
        List<BookingModel> pendingBookings = bookingService.findByStatus(BookingStatus.PENDING);
        List<BookingModel> acceptedBookings = bookingService.findByStatus(BookingStatus.ACCEPTED);
        List<BookingModel> canceledBookings = bookingService.findByStatus(BookingStatus.CANCELED);
        List<BookingModel> completeBookings = bookingService.findByStatus(BookingStatus.COMPLETE);
        List<BookingStatus> bookingStatuses = bookingService.getAllStatuses();
        model.addAttribute("bookingStatus", bookingStatuses);
        model.addAttribute("pendingBookings", pendingBookings);
        model.addAttribute("acceptedBookings", acceptedBookings);
        model.addAttribute("canceledBookings", canceledBookings);
        model.addAttribute("completeBookings", completeBookings);
        return "booking-management";
    }

    @PostMapping("/booking-management/delete-booking")
    public String deleteBooking(@RequestParam Integer bookingId) {
        bookingService.deleteBooking(bookingId);
        return "redirect:/booking-management";
    }

    @PostMapping("/room/{id}/add-booking")
    public String addBooking(@ModelAttribute BookingModel bookingModel, @PathVariable("id") Integer roomId, @RequestParam Integer userId) {
        RoomModel roomModel = roomService.findById(roomId);
        UserModel userModel = userService.findById(userId);
        bookingModel.setRoom(roomModel);
        bookingModel.setUser(userModel);
        bookingModel.setBookingStatus(BookingStatus.PENDING);
        BookingModel registeredBooking = bookingService.addBooking(bookingModel.getStartDate(), bookingModel.getEndDate(), bookingModel.getRoom(), bookingModel.getUser(), bookingModel.getComment());
        if (registeredBooking == null) {
            return "error_page";
        } else {
            return "redirect:/room/" + roomId;
        }
    }

    @PostMapping("/booking-management/change-booking-status")
    public String changeBookingStatus(@RequestParam Integer bookingId, @RequestParam BookingStatus bookingStatus) throws NameNotFoundException {
        BookingModel bookingModel = bookingService.findFirstById(bookingId);
        bookingModel.setBookingStatus(bookingStatus);
        bookingService.save(bookingModel);
        return "redirect:/booking-management";
    }

    @GetMapping("/view-bookings")
    public String viewBookings(Model model, Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        List<BookingModel> bookings = bookingService.findByUsername(user.getName());
        model.addAttribute("PENDING", BookingStatus.PENDING);
        model.addAttribute("ACCEPTED", BookingStatus.ACCEPTED);
        model.addAttribute("CANCELED", BookingStatus.CANCELED);
        model.addAttribute("COMPLETE", BookingStatus.COMPLETE);
        model.addAttribute("bookings", bookings);
        return "view_bookings";
    }

    @GetMapping("/view-bookings/booking/{id}")
    public String viewBookingDetails(@PathVariable Integer id, Model model, Principal principal) throws NameNotFoundException {
        BookingModel booking = bookingService.findFirstById(id);
        UserModel user = userService.findByUsername(principal.getName());
        if (user.getId().equals(booking.getUser().getId())) {
            model.addAttribute("booking", booking);
            model.addAttribute("PENDING", BookingStatus.PENDING);
            model.addAttribute("ACCEPTED", BookingStatus.ACCEPTED);
            model.addAttribute("CANCELED", BookingStatus.CANCELED);
            model.addAttribute("COMPLETE", BookingStatus.COMPLETE);
            return "view_booking_details";
        }
        return "error_page";
    }

    @PostMapping("/view-bookings/booking/{id}/edit")
    public String editBooking(@PathVariable Integer id, String comment, Principal principal) throws NameNotFoundException {
        UserModel user = userService.findByUsername(principal.getName());
        BookingModel booking = bookingService.findFirstById(id);
        if (booking.getUser().equals(user)) {
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setComment(comment);
            bookingService.save(booking);
            return "redirect:/view-bookings/booking/" + id;
        }
        return "error_page";
    }

    @PostMapping("/view-bookings/booking/{id}/cancel")
    public String deleteBooking(@PathVariable("id") Integer id, Principal principal) throws NameNotFoundException {
        BookingModel booking = bookingService.findFirstById(id);
        UserModel user = userService.findByUsername(principal.getName());
        if (user.getId().equals(booking.getUser().getId())) {
            booking.setBookingStatus(BookingStatus.CANCELED);
            bookingService.save(booking);
            return "redirect:/view-bookings";
        }
        return "error_page";
    }
}


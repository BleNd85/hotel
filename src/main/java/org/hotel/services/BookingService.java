package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.BookingModel;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.time.LocalDate;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingModel addBooking(Integer id, LocalDate startDate, LocalDate endDate, RoomModel room, UserModel user) {
        if (id == null) {
            return null;
        } else {
            if (bookingRepository.findFirstById(id).isPresent()) {
                return null;
            }
        }
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(id);
        bookingModel.setStartDate(startDate);
        bookingModel.setEndDate(endDate);
        bookingModel.setUser(user);
        bookingModel.setRoom(room);
        return bookingRepository.save(bookingModel);
    }

    public BookingModel findFirstById(Integer id) throws NameNotFoundException {
        return bookingRepository.findFirstById(id).orElse(null);
    }
    public BookingModel findByRoom(RoomModel roomModel){
        return bookingRepository.findFirstByRoom(roomModel);
    }

    public void deleteBooking(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}

package org.booking.services;

import jakarta.transaction.Transactional;
import org.booking.models.BookingModel;
import org.booking.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;


@Service
public class Service {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingModel addBooking(int id, String User,int room, float StartDate, float EndDate) {
        if (id == null) {
            return null;
        } else {
            if (bookingRepository.findFirstById(id).isPresent()) {
                return null;
            }
        }
        BookingModel bookingModel = new BookingModel();
        bookingModel.id(id);
        bookingModel.setUser(user);
        bookingModel.setDate(date);
        bookingModel.setDate(room);
        bookingModel.setStart(startdate);
        bookingModel.setEnd(enddate);
        return bookingRepository.save(bookingModel);
    }

    public BookingModel findFirstById(int id) throws NameNotFoundException {
        return bookingRepository.findFirstById(id).orElse(null);
    }

    public void deleteBooking(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}

package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.BookingModel;
import org.hotel.models.BookingStatus;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingModel addBooking(Date startDate, Date endDate, RoomModel room, UserModel user, String comment) {
        BookingModel bookingModel = new BookingModel();

        bookingModel.setStartDate(startDate);
        bookingModel.setEndDate(endDate);
        bookingModel.setUser(user);
        bookingModel.setRoom(room);
        bookingModel.setBookingStatus(BookingStatus.PENDING);
        bookingModel.setComment(comment);
        bookingModel.setPrice(calculateTotalPrice(startDate, endDate, room.getPricePerNight()));
        return bookingRepository.save(bookingModel);
    }

    public BookingModel findFirstById(Integer id) throws NameNotFoundException {
        return bookingRepository.findFirstById(id).orElse(null);
    }

    private Double calculateTotalPrice(Date startDate, Date endDate, double pricePerNight) {
        long diffInMil = Math.abs(startDate.getTime() - endDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS);
        return pricePerNight * diff;
    }

    public List<BookingModel> findByUser(UserModel userModel) {
        return bookingRepository.findByUser(userModel);
    }

    public void deleteBooking(Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public List<BookingModel> getAll() {
        return bookingRepository.findAll();
    }

    public List<BookingModel> findByStatus(BookingStatus bookingStatus) {
        return bookingRepository.findByBookingStatus(bookingStatus);
    }

    public List<BookingStatus> getAllStatuses() {
        return Arrays.asList(BookingStatus.values());
    }

    public void save(BookingModel bookingModel) {
        bookingRepository.save(bookingModel);
    }

    public List<BookingModel> findByUsername(String username) {
        return bookingRepository.findByUserName(username);
    }
}

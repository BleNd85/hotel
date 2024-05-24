package org.hotel.repositories;

import org.hotel.models.BookingModel;
import org.hotel.models.BookingStatus;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Integer> {
    Optional<BookingModel> findFirstById(Integer id);
    List<BookingModel> findByRoom(RoomModel roomModel);
    List<BookingModel> findByUser(UserModel userModel);
    List<BookingModel> findByBookingStatus(BookingStatus bookingStatus);
    List<BookingModel> findByUserName(String username);
}



package org.hotel.repositories;

import org.hotel.models.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Integer> {
    Optional<BookingModel> findFirstById(Integer id);
    Optional<BookingModel> findFirstByDate(Integer date);
}



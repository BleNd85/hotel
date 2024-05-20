package org.hotel.repositories;

import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Integer> {
    Optional<RoomModel> findFirstById(Integer id);
    Optional<RoomModel> findByHotel(HotelModel hotel);
    List<RoomModel> findAllByHotelId(Integer hotelId);
}

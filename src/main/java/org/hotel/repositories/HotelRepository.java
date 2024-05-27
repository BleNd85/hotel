package org.hotel.repositories;

import org.hotel.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelModel, Integer> {

    Optional<HotelModel> findByName(String name);
    Optional<HotelModel> findFirstById(Integer id);

    List<HotelModel> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String description, String location);
}

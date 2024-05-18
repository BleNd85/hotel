package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.HotelModel;
import org.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;


@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public HotelModel addHotel(String name, String description, String location) {
        if (name == null) {
            return null;
        } else {
            if (hotelRepository.findByName(name).isPresent()) {
                return null;
            }
        }
        HotelModel hotelModel = new HotelModel();
        hotelModel.setName(name);
        hotelModel.setDescription(description);
        hotelModel.setLocation(location);
        return hotelRepository.save(hotelModel);
    }

    public HotelModel findByName(String name) throws NameNotFoundException {
        return hotelRepository.findByName(name).orElse(null);
    }

    public void deleteHotel(Integer hotelId) {
        hotelRepository.deleteById(hotelId);
    }
}
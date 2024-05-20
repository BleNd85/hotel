package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.models.UserModel;
import org.hotel.repositories.HotelRepository;
import org.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;


@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
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


    public List<HotelModel> getAll() {
        return hotelRepository.findAll();
    }

    public void getAmountOfPlaces(Integer hotelId) {
        HotelModel hotel = hotelRepository.findById(hotelId).orElse(null);
        List<RoomModel> roomModels = roomRepository.findAllByHotelId(hotelId);
        int totalPlaces = 0;
        for (RoomModel roomModel : roomModels) {
            totalPlaces += roomModel.getAmountOfPlaces();
        }
        assert hotel != null;
        hotel.setAmountOfPlaces(totalPlaces);
        hotelRepository.save(hotel);
    }
}

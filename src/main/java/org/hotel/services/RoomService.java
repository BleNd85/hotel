package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public RoomService(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    @Transactional
    public RoomModel addRoom(HotelModel hotel, String name, Double pricePerNight, Integer roomNumber, String type, String description, Integer amountOfPlaces) {
        RoomModel roomModel = new RoomModel();
        roomModel.setName(name);
        roomModel.setPricePerNight(pricePerNight);
        roomModel.setRoomNumber(roomNumber);
        roomModel.setType(type);
        roomModel.setDescription(description);
        roomModel.setHotel(hotel);
        roomModel.setAmountOfPlaces(amountOfPlaces);
        hotelService.getAmountOfPlaces(hotel.getId());
        return roomRepository.save(roomModel);
    }

    public RoomModel findById(Integer id) throws NameNotFoundException {
        return roomRepository.findById(id).orElse(null);
    }

    public Optional<RoomModel> findByHotel(HotelModel hotelModel) {
        return roomRepository.findByHotel(hotelModel);
    }

    public List<RoomModel> findAllByHotel(HotelModel hotelModel) {
        return roomRepository.findAllByHotelId(hotelModel.getId());
    }

    public void deleteRoom(Integer roomId) {
        RoomModel roomModel = roomRepository.findById(roomId).orElse(null);
        if (roomModel != null) {
            HotelModel hotel = roomModel.getHotel();
            hotelService.getAmountOfPlaces(hotel.getId());
        }
        roomRepository.deleteById(roomId);
    }


    public List<RoomModel> getAll() {
        System.out.println(roomRepository.findAll());
        return roomRepository.findAll();
    }
}

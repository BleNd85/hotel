package org.hotel.services;

import jakarta.transaction.Transactional;
import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.repositories.HotelRepository;
import org.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RoomModel addRoom(HotelModel hotel, String name, Double pricePerNight, Integer roomNumber, String type, String description, Integer amountOfPlaces, String imagePath) {
        RoomModel roomModel = new RoomModel();
        roomModel.setName(name);
        roomModel.setPricePerNight(pricePerNight);
        roomModel.setRoomNumber(roomNumber);
        roomModel.setType(type);
        roomModel.setDescription(description);
        roomModel.setHotel(hotel);
        roomModel.setAmountOfPlaces(amountOfPlaces);
        roomModel.setImagePath(imagePath);
        hotel.setAmountOfPlaces(amountOfPlaces);
        return roomRepository.save(roomModel);
    }

    public RoomModel findById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Optional<RoomModel> findByHotel(HotelModel hotelModel) {
        return roomRepository.findByHotel(hotelModel);
    }

    public List<RoomModel> findAllByHotel(HotelModel hotelModel) {
        return roomRepository.findAllByHotelId(hotelModel.getId());
    }

    @Transactional
    public void deleteRoom(Integer roomId) {
        RoomModel roomModel = roomRepository.findById(roomId).orElse(null);
        if (roomModel != null) {
            HotelModel hotel = roomModel.getHotel();
            hotel.setAmountOfPlaces(-roomModel.getAmountOfPlaces());
            roomRepository.deleteById(roomId);
        }
    }

    public List<RoomModel> getAll() {
        return roomRepository.findAll();
    }
}

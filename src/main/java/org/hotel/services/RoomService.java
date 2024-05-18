package org.hotel.services;
import jakarta.transaction.Transactional;
import org.hotel.models.RoomModel;
import org.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;

public class RoomService {
    private final RoomRepository  roomRepository;

    public HotelService(HotelRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RooModel addRoom(String id, String description, String hotel) {
        if (id == null) {
            return null;
        } else {
            if (roomRepository.findById(id).isPresent()) {
                return null;
            }
        }
        roomModel roomModel = new RoomModel();
        roomModel.setId(id);
        roomModel.setDescription(description);
        roomModel.setHotel(hotel);
        return roomRepository.save(roomModel);
    }

    public HotelModel findById(String id) throws NameNotFoundException {
        return roomRepository.findById(id).orElse(null);
    }

    public void deleteRoom(Integer roomId) {
        roomRepository.deleteById(roomId);
    }
}

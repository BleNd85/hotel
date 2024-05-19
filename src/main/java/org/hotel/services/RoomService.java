package org.hotel.services;
import jakarta.transaction.Transactional;
import org.hotel.models.HotelModel;
import org.hotel.models.RoomModel;
import org.hotel.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.Optional;
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RoomModel addRoom(Integer id, HotelModel hotel, String name, Double pricePerNight, Integer roomNumber, String type, String description) {
        if (id == null) {
            return null;
        } else {
            if (roomRepository.findById(id).isPresent()) {
                return null;
            }
        }
        RoomModel roomModel = new RoomModel();
        roomModel.setId(id);
        roomModel.setName(name);
        roomModel.setPricePerNight(pricePerNight);
        roomModel.setRoomNumber(roomNumber);
        roomModel.setType(type);
        roomModel.setDescription(description);
        roomModel.setHotel(hotel);
        return roomRepository.save(roomModel);
    }

    public RoomModel findById(Integer id) throws NameNotFoundException {
        return roomRepository.findById(id).orElse(null);
    }
    public Optional<RoomModel> findByHotel(HotelModel hotelModel){
        return roomRepository.findByHotel(hotelModel);
    }

    public void deleteRoom(Integer roomId) {
        roomRepository.deleteById(roomId);
    }
}

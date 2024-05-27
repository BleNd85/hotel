package org.hotel.services;

import jakarta.persistence.criteria.CriteriaBuilder;
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

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public HotelModel addHotel(String name, String description, String location, String imagePath) {
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
        hotelModel.setImagePath(imagePath);
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

    public HotelModel findById(Integer id) {
        return hotelRepository.findFirstById(id).orElse(null);
    }

    public Long countHotels() {
        return hotelRepository.count();
    }

    public Integer countPlaces() {
        List<HotelModel> hotelModelList = hotelRepository.findAll();
        int amountOfPlaces = 0;
        for (HotelModel hotelModel : hotelModelList) {
            amountOfPlaces += hotelModel.getAmountOfPlaces();
        }
        return amountOfPlaces;
    }

    public List<HotelModel> searchHotels(String keyword) {
        return hotelRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword, keyword);
    }

}


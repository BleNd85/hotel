package org.hotel.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "rooms_table")
public class RoomModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "hotels_id")
    private HotelModel hotel;
    private Integer roomNumber;
    private String name;
    private String type;
    private String description;
    private Double pricePerNight;
    private Boolean isAvailable;
    private Integer amountOfPlaces = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Integer getAmountOfPlaces() {
        return amountOfPlaces;
    }

    public void setAmountOfPlaces(Integer amountOfPlaces) {
        this.amountOfPlaces += amountOfPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomModel roomModel = (RoomModel) o;
        return Objects.equals(id, roomModel.id) && Objects.equals(hotel, roomModel.hotel) && Objects.equals(roomNumber, roomModel.roomNumber) && Objects.equals(name, roomModel.name) && Objects.equals(type, roomModel.type) && Objects.equals(description, roomModel.description) && Objects.equals(pricePerNight, roomModel.pricePerNight) && Objects.equals(isAvailable, roomModel.isAvailable) && Objects.equals(amountOfPlaces, roomModel.amountOfPlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel, roomNumber, name, type, description, pricePerNight, isAvailable, amountOfPlaces);
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", roomNumber=" + roomNumber +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", isAvailable=" + isAvailable +
                ", amountOfPlaces=" + amountOfPlaces +
                '}';
    }
}

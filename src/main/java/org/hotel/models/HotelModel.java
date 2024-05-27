package org.hotel.models;


import jakarta.persistence.*;


import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "hotels_table")
public class HotelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String location;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date created_at;
    private Integer amountOfPlaces = 0;
    private String imagePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAmountOfPlaces() {
        return amountOfPlaces;
    }

    public void setAmountOfPlaces(Integer amountOfPlaces) {
        this.amountOfPlaces += amountOfPlaces;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelModel that = (HotelModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(location, that.location) && Objects.equals(created_at, that.created_at) && Objects.equals(amountOfPlaces, that.amountOfPlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, location, created_at, amountOfPlaces);
    }

    @Override
    public String toString() {
        return "HotelModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", created_at=" + created_at +
                ", amountOfPlaces=" + amountOfPlaces +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public Date getCreated_at() {
        return created_at;
    }

    @PrePersist
    public void onCreate() {
        created_at = new Date();
    }

    public void setCreated_at(Date date) {
        this.created_at = date;
    }

}

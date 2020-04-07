package com.tsystems.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * City entity
 */

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    /**
     * @wagonList - what city wagon locate
     */
    @OneToMany(mappedBy = "current_city")
    private List<Wagon> wagonList;

    @OneToMany(mappedBy = "city_from", cascade = CascadeType.MERGE)
    private List<Cargo> city_from;

    @OneToMany(mappedBy = "city_to", cascade = CascadeType.MERGE)
    private List<Cargo> city_to;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "current_city")
    private List<Driver> current_city;

    public City() {
    }

    public City(Integer id, Double longitude, Double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public City(String name) {
        this.name = name;
    }

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name) &&
                Objects.equals(longitude, city.longitude) &&
                Objects.equals(latitude, city.latitude) &&
                Objects.equals(wagonList, city.wagonList) &&
                Objects.equals(city_from, city.city_from) &&
                Objects.equals(city_to, city.city_to) &&
                Objects.equals(current_city, city.current_city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, longitude, latitude, wagonList, city_from, city_to, current_city);
    }
}

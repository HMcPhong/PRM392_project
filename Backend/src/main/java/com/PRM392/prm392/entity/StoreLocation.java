package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "StoreLocations")
public class StoreLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationID")
    Integer locationId;

    @Column(name = "Latitude")
    Double latitude;

    @Column(name = "Longitude")
    Double longitude;

    @Column(name = "Address")
    String address;

}

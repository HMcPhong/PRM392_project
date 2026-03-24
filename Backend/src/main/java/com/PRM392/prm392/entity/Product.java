package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    Integer productID;

    @Column(name = "ProductName", nullable = false)
    String productName;

    @Column(name = "BriefDescription")
    String briefDescription;

    @Column(name = "FullDescription")
    String fullDescription;

    @Column(name = "TechnicalSpecifications")
    String technicalSpecifications;

    @Column(name = "Price", nullable = false)
    Double price;

    @Column(name = "ImageURL")
    String imageUrl;

    @JoinColumn(name = "CategoryID")
    Integer categoryID;

}

package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Integer productID;

    @Column(name = "product_name", nullable = false)
    String productName;

    @Column(name = "brief_description")
    String briefDescription;

    @Column(name = "full_description")
    String fullDescription;

    @Column(name = "technical_specifications")
    String technicalSpecifications;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "image_url")
    String imageUrl;

    @JoinColumn(name = "category_id")
    Integer categoryID;

}

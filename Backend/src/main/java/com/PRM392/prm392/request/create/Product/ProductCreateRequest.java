package com.PRM392.prm392.request.create.Product;

import com.PRM392.prm392.entity.Categories;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {

    @NotBlank
    String productName;

    String briefDescription;

    String fullDescription;

    String technicalSpecification;

    @NotBlank
    Double price;

    String imageURL;

    Categories category;

}

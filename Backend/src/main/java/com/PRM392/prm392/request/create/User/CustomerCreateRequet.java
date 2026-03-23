package com.PRM392.prm392.request.create.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCreateRequet {

    @NotBlank
    String userName;

    @NotBlank
    String password;

    @Email
    String email;

    String phoneNumber;

    String address;

}

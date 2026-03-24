package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    Integer userID;

    @Column(name = "Username", nullable = false)
    String userName;

    @Column(name = "PasswordHash", nullable = false)
    String passwordHash;

    @Email
    @Column(name = "Email", nullable = false)
    String email;

    @Column(name = "PhoneNumber")
    String phoneNumber;

    @Column(name = "Address")
    String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    Role role;

}

package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    Integer notificationId;

    @JoinColumn(name = "UserID")
    Integer userId;

    @Column(name = "Message")
    String message;

    @Column(name = "IsRead")
    Boolean isRead;

    @Column(name = "CreatedAt")
    Date createdAt;

}

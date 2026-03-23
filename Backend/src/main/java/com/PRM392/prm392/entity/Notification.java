package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    Integer notificationId;

    @JoinColumn(name = "user_id")
    Integer userId;

    @Column(name = "message")
    String message;

    @Column(name = "is_read")
    Boolean isRead;

    @Column(name = "created_at")
    Date createdAt;

}

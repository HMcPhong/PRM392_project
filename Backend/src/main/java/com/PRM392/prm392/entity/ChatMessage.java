package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ChatMessages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChatMessageID")
    Integer chatMessageId;

    @JoinColumn(name = "UserID")
    Integer userId;

    @Column(name = "Message")
    String message;

    @Column(name = "SentAt")
    Date sentAt;

}

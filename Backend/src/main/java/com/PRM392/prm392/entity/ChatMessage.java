package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    Integer chatMessageId;

    @JoinColumn(name = "user_id")
    Integer userId;

    @Column(name = "message")
    String message;

    @Column(name = "sent_at")
    Date sentAt;

}

package com.diasonti.descriptiontinder.data.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserAccount sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserAccount receiver;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private MmMatch match;

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MmMatch getMatch() {
        return match;
    }

    public void setMatch(MmMatch match) {
        this.match = match;
    }
}

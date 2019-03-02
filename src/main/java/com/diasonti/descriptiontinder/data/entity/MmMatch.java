package com.diasonti.descriptiontinder.data.entity;

import com.diasonti.descriptiontinder.data.enums.MatchType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "matchmaking_match")
public class MmMatch extends BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "first_user_id")
    private UserAccount firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user_id")
    private UserAccount secondUser;

    @ManyToOne
    @JoinColumn(name = "first_user_choice_id")
    private MmChoice firstUserChoice;

    @ManyToOne
    @JoinColumn(name = "second_user_choice_id")
    private MmChoice secondUserChoice;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MatchType type;

    @OneToMany(mappedBy = "match")
    @OrderBy("sentAt ASC")
    private List<ChatMessage> messages;

    public MmMatch() {
    }

    public MmMatch(MmChoice firstUserChoice, MmChoice secondUserChoice) {
        this.createdAt = LocalDateTime.now();
        this.firstUserChoice = Objects.requireNonNull(firstUserChoice, "First user's choice must not be null");
        this.secondUserChoice = Objects.requireNonNull(secondUserChoice, "Second user's choice must not be null");
        this.firstUser = firstUserChoice.getSource();
        this.secondUser = secondUserChoice.getSource();
        this.type = MatchType.MUTUAL;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserAccount getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(UserAccount firstUser) {
        this.firstUser = firstUser;
    }

    public UserAccount getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(UserAccount secondUser) {
        this.secondUser = secondUser;
    }

    public MmChoice getFirstUserChoice() {
        return firstUserChoice;
    }

    public void setFirstUserChoice(MmChoice firstUserChoice) {
        this.firstUserChoice = firstUserChoice;
    }

    public MmChoice getSecondUserChoice() {
        return secondUserChoice;
    }

    public void setSecondUserChoice(MmChoice secondUserChoice) {
        this.secondUserChoice = secondUserChoice;
    }

    public MatchType getType() {
        return type;
    }

    public void setType(MatchType type) {
        this.type = type;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Transient
    public boolean hasUserWithId(Long userId) {
        return firstUser.getId().equals(userId) || secondUser.getId().equals(userId);
    }
}

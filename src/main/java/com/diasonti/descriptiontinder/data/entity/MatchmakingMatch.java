package com.diasonti.descriptiontinder.data.entity;

import com.diasonti.descriptiontinder.data.enums.MatchType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matchmaking_match")
public class MatchmakingMatch extends BaseEntity {

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
    private MatchmakingChoice firstUserChoice;

    @ManyToOne
    @JoinColumn(name = "second_user_choice_id")
    private MatchmakingChoice secondUserChoice;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MatchType type;

    public MatchmakingMatch() {

    }

    public MatchmakingMatch(MatchmakingChoice firstUserChoice, MatchmakingChoice secondUserChoice) {
        this.createdAt = LocalDateTime.now();
        this.firstUser = firstUserChoice.getSource();
        this.secondUser = secondUserChoice.getSource();
        this.firstUserChoice = firstUserChoice;
        this.secondUserChoice = secondUserChoice;
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

    public MatchmakingChoice getFirstUserChoice() {
        return firstUserChoice;
    }

    public void setFirstUserChoice(MatchmakingChoice firstUserChoice) {
        this.firstUserChoice = firstUserChoice;
    }

    public MatchmakingChoice getSecondUserChoice() {
        return secondUserChoice;
    }

    public void setSecondUserChoice(MatchmakingChoice secondUserChoice) {
        this.secondUserChoice = secondUserChoice;
    }

    public MatchType getType() {
        return type;
    }

    public void setType(MatchType type) {
        this.type = type;
    }
}

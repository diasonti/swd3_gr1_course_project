package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.data.enums.MatchType;

import java.time.LocalDateTime;

public class MmMatchForm extends BaseForm {

    private LocalDateTime matchedAt;

    private UserProfileForm matchedUser;

    private MatchType type;

    private ChatMessageForm lastMessage;

    public LocalDateTime getMatchedAt() {
        return matchedAt;
    }

    public void setMatchedAt(LocalDateTime matchedAt) {
        this.matchedAt = matchedAt;
    }

    public UserProfileForm getMatchedUser() {
        return matchedUser;
    }

    public void setMatchedUser(UserProfileForm matchedUser) {
        this.matchedUser = matchedUser;
    }

    public MatchType getType() {
        return type;
    }

    public void setType(MatchType type) {
        this.type = type;
    }

    public ChatMessageForm getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatMessageForm lastMessage) {
        this.lastMessage = lastMessage;
    }
}

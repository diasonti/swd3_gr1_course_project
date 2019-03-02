package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.data.entity.ChatMessage;

import java.time.LocalDateTime;

public class ChatMessageForm extends BaseForm {

    private UserProfileForm sender;

    private UserProfileForm receiver;

    private LocalDateTime sentAt;

    private String text;

    public static ChatMessageForm of(ChatMessage message) {
        ChatMessageForm form = null;
        if(message != null) {
            form = new ChatMessageForm();
            form.setId(message.getId());
            form.setSender(UserProfileForm.of(message.getSender()));
            form.setReceiver(UserProfileForm.of(message.getReceiver()));
            form.setSentAt(message.getSentAt());
            form.setText(message.getText());
        }
        return form;
    }

    public UserProfileForm getSender() {
        return sender;
    }

    public void setSender(UserProfileForm sender) {
        this.sender = sender;
    }

    public UserProfileForm getReceiver() {
        return receiver;
    }

    public void setReceiver(UserProfileForm receiver) {
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
}

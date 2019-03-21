package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.data.entity.ChatMessage;

import java.time.format.DateTimeFormatter;

public class ChatMessageForm extends BaseForm {

    private UserProfileForm sender;

    private UserProfileForm receiver;

    private String sentAt;

    private String text;

    private boolean mine;

    public static ChatMessageForm of(ChatMessage message) {
        ChatMessageForm form = null;
        if(message != null) {
            form = new ChatMessageForm();
            form.setId(message.getId());
            form.setSender(UserProfileForm.of(message.getSender()));
            form.setReceiver(UserProfileForm.of(message.getReceiver()));
            form.setSentAt(message.getSentAt().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")));
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

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}

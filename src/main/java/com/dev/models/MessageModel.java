package com.dev.models;

import com.dev.objects.Message;

import java.text.SimpleDateFormat;

public class MessageModel {
    private int id;
/*    private String title;*/
    private String content;
    private String sendDate;
    private UserModel sender;
    private boolean sendByMe;

    public MessageModel () {
    }

    public MessageModel (Message message, int userID) {
        this.id = message.getId();
/*
        this.title = message.getTitle();
*/
        this.content = message.getContent();
        this.sender = new UserModel(message.getSender());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.sendDate = simpleDateFormat.format(message.getSendDate());
        if (userID == this.sender.getId()){
            this.sendByMe = true;
        }
    }

    public boolean isSendByMe() {
        return sendByMe;
    }

    public void setSendByMe(boolean sendByMe) {
        this.sendByMe = sendByMe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSenderUsername(UserModel sender) {
        this.sender = sender;
    }
}

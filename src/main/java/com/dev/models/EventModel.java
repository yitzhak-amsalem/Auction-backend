package com.dev.models;

import com.dev.objects.User;

public class EventModel extends UserModel {
    private String event;

    public EventModel(User user, String event) {
        super(user);
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

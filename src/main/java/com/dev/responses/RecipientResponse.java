package com.dev.responses;

import com.dev.models.UserModel;
import com.dev.objects.User;

import java.util.ArrayList;
import java.util.List;

public class RecipientResponse extends BasicResponse{
    private List<UserModel> recipients;

    public RecipientResponse(List<User> users) {
        this.recipients = new ArrayList<>();
        for (User user: users){
            this.recipients.add(new UserModel(user));
        }
    }

    public List<UserModel> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<UserModel> recipients) {
        this.recipients = recipients;
    }
}

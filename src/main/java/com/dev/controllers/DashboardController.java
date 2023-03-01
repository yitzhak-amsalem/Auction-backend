/*
package com.dev.controllers;

import com.dev.objects.Message;
import com.dev.objects.User;
import com.dev.responses.BasicResponse;
import com.dev.responses.MessagesResponse;
import com.dev.responses.RecipientResponse;
import com.dev.responses.UsernameResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev.utils.Errors.ERROR_NO_SUCH_RECIPIENT;
import static com.dev.utils.Errors.ERROR_NO_SUCH_TOKEN;

@RestController
public class DashboardController {

    @Autowired
    private Persist persist;
    @Autowired
    private LiveUpdateController liveUpdatesController;

    @RequestMapping (value = "get-username", method = RequestMethod.GET)
    public BasicResponse getUsername (String token) {
        User user = persist.getUserByToken(token);
        BasicResponse basicResponse = null;
        if (user != null) {
            basicResponse = new UsernameResponse(true, null, user.getUsername());
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;
    }

    @RequestMapping(value = "get-latest-messages", method = RequestMethod.GET)  // get X
    public BasicResponse getLatestMessages (String token) {
        List<Message> userMessages = persist.getMessagesByToken(token);
        User user = persist.getUserByToken(token);
        MessagesResponse messagesResponse = new MessagesResponse(userMessages, user.getId());
        return messagesResponse;
    }
    @RequestMapping(value = "get-recipients", method = RequestMethod.GET)
    public BasicResponse getRecipients (String token) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        if (user != null){
            List<User> allUsers = persist.getAllUsers();
            List<User> recipients = allUsers.stream().filter(recipient -> recipient.getId() != user.getId()).collect(Collectors.toList());
            basicResponse = new RecipientResponse(recipients);
            basicResponse.setSuccess(true);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;
    }





    // SSEvents
    @RequestMapping(value = "send-message", method = RequestMethod.POST)
    public BasicResponse sendMessage (String token, int recipientID, String content) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        if (user != null){
            User recipient = persist.getUserByID(recipientID);
            if (recipient != null){
                Message message = new Message(user, recipient, content);
                liveUpdatesController.sendConversationMessage(user.getId(), recipientID, message);
                basicResponse = new BasicResponse(true, null);
            } else {
                basicResponse = new BasicResponse(false, ERROR_NO_SUCH_RECIPIENT);
            }
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;

    }

    @RequestMapping (value = "/start-typing", method = RequestMethod.POST)
    public BasicResponse startTyping (String token, int recipientID) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        if (user != null) {
            liveUpdatesController.sendStartTypingEvent(user.getId(), recipientID);
            basicResponse = new BasicResponse(true, null);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;
    }
}
*/

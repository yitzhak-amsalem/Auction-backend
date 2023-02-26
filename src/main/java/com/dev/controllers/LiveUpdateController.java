package com.dev.controllers;

import com.dev.models.EventModel;
import com.dev.models.MessageModel;
import com.dev.objects.Message;
import com.dev.objects.User;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;

import static com.dev.utils.Constants.*;


@Controller
public class LiveUpdateController {

    private HashMap<String, SseEmitter> emitterMap = new HashMap<>();
    @Autowired
    private Persist persist;

/*    @PostConstruct
    public void init(){
        new Thread(() ->{
//            while (true) {
//                for (SseEmitter sseEmitter: emitterList){
//                    try {
//                        sseEmitter.send(new Date().toString());
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                try {
//                    Thread.sleep(SECOND);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
        }).start();
    }*/

    @RequestMapping(value = "/sse-handler", method = RequestMethod.GET)
    public SseEmitter handle(String token, int recipientID){
        User user = persist.getUserByToken(token);
        SseEmitter sseEmitter = null;
        if (user != null){
            System.out.println(recipientID);
            String key = createKey(user.getId(), recipientID);
            sseEmitter = this.emitterMap.get(key);
            if (sseEmitter == null){
                sseEmitter = new SseEmitter(30L * MINUTE);
                System.out.println(key);
                this.emitterMap.put(key, sseEmitter);
            }
        }
        System.out.println(sseEmitter);
        return sseEmitter;
    }

    private String createKey(int senderID, int recipientID){
        return (senderID + "_" + recipientID);
    }

    public void sendStartTypingEvent(int senderId, int recipientId) {
        String key = createKey(recipientId, senderId);
/*        System.out.println(this.senderKey);
        System.out.println(this.recipientKey);
        System.out.println(this.senderKey.equals(this.recipientKey));*/
        SseEmitter conversationEmitter = this.emitterMap.get(key);
        System.out.println(conversationEmitter);
        if (conversationEmitter != null) {
            //User sender = persist.getUserByID(senderId);
            //EventModel event = new EventModel(sender, EVENT_TYPING);
            try {
                conversationEmitter.send(senderId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendConversationMessage (int senderId, int recipientId, Message message) {
        String key = createKey(recipientId, senderId);
        SseEmitter conversationEmitter = this.emitterMap.get(key);
        if (conversationEmitter != null) {
            //User sender = persist.getUserByID(senderId);
            MessageModel messageModel = new MessageModel(message, recipientId);
            try {
                conversationEmitter.send(messageModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

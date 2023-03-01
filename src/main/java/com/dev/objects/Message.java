package com.dev.objects;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "messages")
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "send_date")
    private Date sendDate;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column
    private String content;

    public Message(){

    }
    public Message(User sender, User recipient, String content){
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.sendDate = new Date();

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

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(Message o) {
        return (this.sendDate.compareTo(o.sendDate));
    }
}

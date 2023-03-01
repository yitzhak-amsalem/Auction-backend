
package com.dev.utils;

import com.dev.objects.Message;
import com.dev.objects.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Persist {

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public User getUserByUsername (String username) {
        User found = null;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return found;
    }

    public void saveUser (User user) { // save X
        Session session = sessionFactory.openSession();
        session.save(user);
        session.close();
    }

    public User getUserByUsernameAndToken (String username, String token) {
        User found = null;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username " +
                        "AND token = :token")
                .setParameter("username", username)
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return found;
    }

    public List<User> getAllUsers () {
        Session session = sessionFactory.openSession();
        List<User> allUsers = session.createQuery("FROM User ").list();
        session.close();
        return allUsers;
    }

    public User getUserByToken (String token) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("From User WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return user;
    }

    public User getUserByID (int id) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("From User WHERE id = :id")
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return user;
    }

    public List<Message> getMessagesByToken (String token) { // get X by token
        Session session = sessionFactory.openSession();
        List<Message> messages = session.createQuery("FROM Message WHERE recipient.token = :token ")
                .setParameter("token", token)
                .list();
        session.close();
        return messages;
    }

}

package ro.unibuc.hello.dto;

import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.dto.Notification;

import java.util.List;

public class Account {
    private String status;
    private User user;
    private List<Game> games;
    private List<Notification> notifications;

    public Account(String status, User user, List<Game> games, List<Notification> notifications){
        this.status = status;
        this.user = user;
        this.games = games;
        this.notifications = notifications;
    }

    public Account(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public User getUser(){
        return this.user;
    }

    public List<Game> getGames(){
        return this.games;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}

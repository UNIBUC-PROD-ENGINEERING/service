package ro.unibuc.hello.dto;

import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.dto.Game;
import java.util.List;

public class Account {
    private String status;
    private User user;
    private List<Game> games;

    public Account(String status, User user, List<Game> games){
        this.status = status;
        this.user = user;
        this.games = games;
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

}

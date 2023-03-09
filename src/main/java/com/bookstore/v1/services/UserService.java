package com.bookstore.v1.services;

import com.bookstore.v1.data.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUsers() {
        return List.of(new User("1", "a", "a", "a"));
    }
}

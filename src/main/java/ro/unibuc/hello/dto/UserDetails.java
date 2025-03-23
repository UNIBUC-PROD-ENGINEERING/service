package ro.unibuc.hello.dto;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.hello.data.UserEntity;

public class UserDetails {

    private String id;
    private String name;
    private String username;
    private List<Item> ownedItems = new ArrayList<>();

    public UserDetails() {}

    public UserDetails(String id, String name, String username, List<Item> items) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.ownedItems = items;
    }

    public UserDetails(UserEntity entity, List<Item> items) {
        this(entity.getId(), entity.getName(), entity.getUsername(), items);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    public void addItem(Item item) {
        this.ownedItems.add(item);
    }

    public void eraseItem(Item item) {
        this.ownedItems.remove(item);
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder(
                "UserDetailss: " + "Name: " + name + " UserDetailsname: " + username + " Owned items: ");
        
        if(ownedItems != null){
            for (Item item : ownedItems) {
                print.append(" ").append(item.toString());
            }
        }

        return print.toString();
    }
}

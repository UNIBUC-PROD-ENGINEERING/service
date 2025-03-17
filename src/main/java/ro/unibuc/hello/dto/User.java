package ro.unibuc.hello.dto;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.hello.data.ItemEntity;

public class User {

    private String name;
    private String username;
    private String Id;

    private List<Item> ownedItems = new ArrayList<>();

    public User() {
    }

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public User(String Id, String name, String username, List<ItemEntity> items) {
        this.Id = Id;
        this.name = name;
        this.username = username;

        if (items != null) {
            items.forEach(
                    item -> {
                        Item newItem = new Item(item.getName(), item.getDescription(), item.getOwner().getName());
                        this.ownedItems.add(newItem);
                    });
        }
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

    public String getName() {
        return name;
    }

    public String getId() {
        return Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder(
                "Users: " + "Name: " + name + " Username: " + username + " Owned items: ");
        
                if(ownedItems != null){
        for (Item item : ownedItems) {
            print.append(" ").append(item.toString());
        }
    }
        return print.toString();
    }
}
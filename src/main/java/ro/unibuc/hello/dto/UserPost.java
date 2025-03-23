package ro.unibuc.hello.dto;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.hello.data.ItemEntity;

public class UserPost{
    
    private String name;
    private String username;
    private String password;
    private String sessionId;

    private List<Item> ownedItems = new ArrayList<>();

    public UserPost(){}

    public UserPost(String sessionId, String name,String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserPost(String name, String username, List<ItemEntity> items) {
        this.name = name;
        this.username = username;
        
        items.forEach(
            item -> {
                Item newItem = new Item( item.getName(), item.getDescription(), item.getOwner().getName());
                this.ownedItems.add(newItem);
            }
        );
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
    
    public String getName() {
        return name;
    }
   
    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
   

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("Users: " + "Name: " + name + " Username: " + username + " Owned items: ");
        for (Item item : ownedItems) {
            print.append(" ").append(item.toString());
        }
        return print.toString();
    }
}
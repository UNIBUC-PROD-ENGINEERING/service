package ro.unibuc.hello.data;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ro.unibuc.hello.dto.Item;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class UserEntity {
    
    @Id
    private String id;
    private String name;
    private String password;

    @Indexed(unique = true)
    private String username;

    @JsonManagedReference
    private List<ItemEntity> items = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(String name, String password, String username) {
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public UserEntity(String Id, String name, String password, String username) {
        this.id = Id;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public UserEntity(UserEntity other) {
        this.id = other.id;
        this.name = other.name;
        this.password = other.password;
        this.username = other.username;

        // Deep copy for items list
        if (other.items != null) {
            this.items = new ArrayList<>(other.items);
        }
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   public List<ItemEntity> getItems() {
       return items;
   }

   public void addItem(ItemEntity item) {
       this.items.add(item);
   }

   public void eraseItem(ItemEntity item) {
       this.items.remove(item);
   }

    public final String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public List<ItemEntity> updateItems(List<Item> items) {
        this.items.clear();
        if(items != null){
            for (Item item : items) {
            ItemEntity newItem = new ItemEntity(item.getName(), item.getDescription());
            newItem.setOwner(this);
            this.items.add(newItem);  
            }
        }

        return this.items;
    }


    // @Override
    // public String toString() {
    //     StringBuilder print = new StringBuilder("Users: " + "Name: " + name + " Username: " + username + " Owned items: ");
    //     for (ItemEntity item : items) {
    //         print.append(" ").append(item.toString());
    //     }
    //     return print.toString();
    // }
}
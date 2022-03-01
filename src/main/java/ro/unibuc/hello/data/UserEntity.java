package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Objects;

@Document
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    @DBRef(lazy = true)
    private ArrayList<ReviewEntity> reviews;
    @DBRef(lazy = true)
    private ArrayList<WatchItemEntity> watchItems;

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserEntity(String name, String email, ArrayList<ReviewEntity> reviews, ArrayList<WatchItemEntity> watchItems) {
        this.name = name;
        this.email = email;
        this.reviews = reviews;
        this.watchItems = watchItems;
    }

    public UserEntity() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<WatchItemEntity> getWatchItems() {
        return watchItems;
    }

    public void setWatchItems(ArrayList<WatchItemEntity> watchItems) {
        this.watchItems = watchItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

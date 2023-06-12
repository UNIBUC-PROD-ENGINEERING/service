package ro.unibuc.hello.data;

import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class Student {
    @Id
    public String id;
    private String name;
    private String email;
    private Gender gender;
    private Address address;
    private List<String> favoriteSubjects;
    private LocalDateTime created;

    public Student(String name,
                   String email,
                   Gender gender,
                   Address address,
                   List<String> favoriteSubjects,
                   LocalDateTime created) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.favoriteSubjects = favoriteSubjects;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getFavoriteSubjects() {
        return favoriteSubjects;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFavoriteSubjects(List<String> favoriteSubjects) {
        this.favoriteSubjects = favoriteSubjects;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}

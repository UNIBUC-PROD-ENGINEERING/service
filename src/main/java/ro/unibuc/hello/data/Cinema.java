package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cinema")
public class Cinema {

    @Id
    private String id;
    private String name;
    private String email;
    private String street;
    private int number;
    private String city;


    public Cinema() {
    }

    public Cinema(String id, String name, String email, String street, int number, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.street = street;
        this.number = number;
        this.city = city;
    }

    
    public Cinema(String name, String email, String street, int number, String city) {
        this.name = name;
        this.email = email;
        this.street = street;
        this.number = number;
        this.city = city;
    }

    public String getId(){
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

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getStreet(){
        return street;
    }

    public void setStreet(String street){
        this.street = street;
    }
    
    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }
    
    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    @Override
    public String toString(){
        return "Cinema{" + 
                "id='" + id + '\'' +
                ", name='" + name + '\'' + 
                ", email='" + email + '\'' +
                ", street='" + street + '\'' +
                ", number='" + Integer.toString(number) + '\'' +
                ", city='" + city + '}';
    }

}
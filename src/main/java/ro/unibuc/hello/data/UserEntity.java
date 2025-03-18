package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userEntity")
public class UserEntity {

    @Id
    private String id;

    private String nume;
    private String prenume;
    private String email;

    public UserEntity(){}

    public UserEntity(String nume, String prenume, String email){
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public UserEntity(String id, String nume, String prenume, String email){
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNume(){
        return nume;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public String getPrenume(){
        return prenume;
    }

    public void setPrenume(String prenume){
        this.prenume = prenume;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', nume='%s', prenume='%s', email='%s']",
                id, nume, prenume, email);
    }
}

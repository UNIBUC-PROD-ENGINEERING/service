package main.java.ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "proprietarEntity")
public class ProprietarEntity {

    @Id
    private String id;

    private String nume;
    private String prenume;
    private String email;
    private String cnp;

    public ProprietarEntity(){}

    public ProprietarEntity(String nume, String prenume, String email, String cnp){
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.cnp = cnp;
    }

    public ProprietarEntity(String id, String nume, String prenume, String email, String cnp){
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.cnp = cnp;
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
    public String getCnp(){
        return cnp;
    }

    public void setCnp(String cnp){
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return String.format(
                "Proprietar[id='%s', nume='%s', prenume='%s', email='%s',  cnp='%s']",
                id, nume, prenume, email, cnp);
    }
}

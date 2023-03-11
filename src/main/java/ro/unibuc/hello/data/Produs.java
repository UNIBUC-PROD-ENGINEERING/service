package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produse")
public class Produs {

    @Id
    private String id;

    private String nume;
    private String pret;

    public Produs() {
    }

    public Produs(String id, String nume, String pret) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }
}

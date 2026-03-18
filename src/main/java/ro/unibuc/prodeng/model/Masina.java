package ro.unibuc.prodeng.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "masini")
public class Masina {

    @Id
    private String id;
    private String marca;
    private String model;
    private int an;
    private double pret;
    private int kilometri;
    private String combustibil;
    private int putereCp;
    private MasinaStatus status;
    private String ownerEmail;

    public Masina() {}

    public Masina(String id, String marca, String model, int an, double pret, int kilometri, String combustibil, int putereCp, MasinaStatus status, String ownerEmail) {
        this.id = id;
        this.marca = marca;
        this.model = model;
        this.an = an;
        this.pret = pret;
        this.kilometri = kilometri;
        this.combustibil = combustibil;
        this.putereCp = putereCp;
        this.status = status;
        this.ownerEmail = ownerEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getKilometri() {
        return kilometri;
    }

    public void setKilometri(int kilometri) {
        this.kilometri = kilometri;
    }

    public String getCombustibil() {
        return combustibil;
    }

    public void setCombustibil(String combustibil) {
        this.combustibil = combustibil;
    }

    public int getPutereCp() {
        return putereCp;
    }

    public void setPutereCp(int putereCp) {
        this.putereCp = putereCp;
    }

    public MasinaStatus getStatus() {
        return status;
    }

    public void setStatus(MasinaStatus status) {
        this.status = status;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}

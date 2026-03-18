package ro.unibuc.prodeng.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class MasinaRequest {

    @NotBlank(message = "Marca este obligatorie")
    private String marca;

    @NotBlank(message = "Modelul este obligatoriu")
    private String model;

    @Min(value = 1900, message = "Anul trebuie sa fie minim 1900")
    private int an;

    private double pret;

    @Min(value = 0, message = "Rulajul in kilometri nu poate fi negativ")
    private int kilometri;

    @NotBlank(message = "Combustibilul este obligatoriu")
    private String combustibil;

    @Min(value = 1, message = "Puterea trebuie sa fie de minim 1 CP")
    private int putereCp;

    @NotBlank(message = "Emailul proprietarului este obligatoriu")
    private String ownerEmail;

    public MasinaRequest() {}

    public MasinaRequest(String marca, String model, int an, double pret, int kilometri, String combustibil, int putereCp, String ownerEmail) {
        this.marca = marca;
        this.model = model;
        this.an = an;
        this.pret = pret;
        this.kilometri = kilometri;
        this.combustibil = combustibil;
        this.putereCp = putereCp;
        this.ownerEmail = ownerEmail;
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

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}

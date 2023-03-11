package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.Produs;

public class ProdusDTO {
    private String id;

    private String nume;
    private String pret;

    public ProdusDTO() {
    }

    public ProdusDTO(String id, String nume, String pret) {
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

package main.java.ro.unibuc.hello.dto;

public class Proprietar {
    private String id;
    private String nume;
    private String prenume;
    private String email;

    public Proprietar() {}

    public Proprietar(String id, String nume, String prenume, String email) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
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

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

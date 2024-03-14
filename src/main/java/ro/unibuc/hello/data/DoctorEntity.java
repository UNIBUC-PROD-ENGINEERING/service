package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class DoctorEntity {
    @Id
    public String id;
    public String nume;
    public String specializare;

    public DoctorEntity() {
    }

    public DoctorEntity(String nume, String specializare) {
        this.nume = nume;
        this.specializare = specializare;
    }

    @Override
    public String toString() {
        return String.format(
                "Doctor[id=%s, nume='%s', specializare='%s']",
                id, nume, specializare);
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

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }
}

package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class DoctorEntity {
    @Id
    public String id;
    public String nume;
    public String specializare;

    public DoctorEntity() {}

    public DoctorEntity(String nume, String specializare) {
        this.nume = nume;
        this.specializare = specializare;
    }

    @Override
    public String toString() {
        return String.format(
                "Doctor[nume='%s', specializare='%s']",
                id, nume, specializare);
    }


    public void setId(String id)
    {
        this.id = id;
    }

}

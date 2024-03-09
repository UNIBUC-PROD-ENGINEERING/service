package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;


public class Doctor {


    @Id
    public Integer id;

    public String nume;
    public String specializare;

    public Doctor() {}

    public Doctor(String nume, String specializare) {
        this.nume = nume;
        this.specializare = specializare;
    }


    @Override
    public String toString() {
        return String.format(
                "Doctor[nume='%s', specializare='%s']",
                id, nume, specializare);
    }

}

package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class PacientEntity {
    @Id
    public String id;
    public String nume;

    public PacientEntity() {
    }

    public PacientEntity(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return String.format(
                "Doctor[id=%s, nume='%s']",
                id, nume);
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
}

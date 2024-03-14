package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class IntervalOrarEntity {
    @Id
    public String id;
    public int ora;
    public int minut;

    public IntervalOrarEntity() {
    }

    public IntervalOrarEntity(int ora, int minut) {
        this.ora = ora;
        this.minut = minut;
    }

    @Override
    public String toString() {
        return String.format(
                "IntervalOrar[id='%s', ora='%d', minut='%d']",
                id, ora, minut);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
    }
}

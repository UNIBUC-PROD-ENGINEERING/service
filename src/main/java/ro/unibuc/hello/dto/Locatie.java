package ro.unibuc.hello.dto;

public class Locatie {
    private String id;
    private String tara;
    private String oras;
    private String strada;

    public Locatie() {}

    public Locatie(String id, String tara, String oras, String strada) {
        this.id = id;
        this.tara = tara;
        this.oras = oras;
        this.strada = strada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }
}

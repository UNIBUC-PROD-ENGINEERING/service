package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "locatieEntity")
public class LocatieEntity{
    @Id
    private String id;

    private String tara;
    private String oras;
    private String strada;

    public LocatieEntity(){}

    public LocatieEntity(String tara, String oras, String strada){
        this.tara = tara;
        this.oras = oras;
        this.strada = strada;
    }

    public LocatieEntity(String id, String tara, String oras, String strada){
        this.id = id;
        this.tara = tara;
        this.oras = oras;
        this.strada = strada;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTara(){
        return tara;
    }

    public void setTara(String tara){
        this.tara = tara;
    }

    public String getOras(){
        return oras;
    }

    public void setOras(String oras){
        this.oras = oras;
    }

    public String getStrada(){
        return strada;
    }

    public void setStrada(String strada){
        this.strada = strada;
    }

    @Override
    public String toString() {
        return String.format(
                "Locatie[id='%s', tara='%s', oras='%s', strada='%s']",
                id, tara, oras, strada);
    }
}
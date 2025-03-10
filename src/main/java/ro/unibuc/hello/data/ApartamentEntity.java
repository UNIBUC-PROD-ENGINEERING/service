package main.java.ro.unibuc.hello.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

public class ApartamentEntity {
    private String id;
    private String idProprietar;
    private String idLocatie;
    private int numarApartament;
    private int etaj;
    private int numarCamere;
    private int numarPaturi;
    private int numarBai;
    private String tara;
    private String oras;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdProprietar() { return idProprietar; }
    public void setIdProprietar(String idProprietar) { this.idProprietar = idProprietar; }
    public String getIdLocatie() { return idLocatie; }
    public void setIdLocatie(String idLocatie) { this.idLocatie = idLocatie; }
    public int getNumarApartament() { return numarApartament; }
    public void setNumarApartament(int numarApartament) { this.numarApartament = numarApartament; }
    public int getEtaj() { return etaj; }
    public void setEtaj(int etaj) { this.etaj = etaj; }
    public int getNumarCamere() { return numarCamere; }
    public void setNumarCamere(int numarCamere) { this.numarCamere = numarCamere; }
    public int getNumarPaturi() { return numarPaturi; }
    public void setNumarPaturi(int numarPaturi) { this.numarPaturi = numarPaturi; }
    public int getNumarBai() { return numarBai; }
    public void setNumarBai(int numarBai) { this.numarBai = numarBai; }
    public String getTara() { return tara; }
    public void setTara(String tara) { this.tara = tara; }
    public String getOras() { return oras; }
    public void setOras(String oras) { this.oras = oras; }
}

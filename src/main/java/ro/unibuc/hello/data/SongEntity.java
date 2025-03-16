package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songs")
public class SongEntity {
    @Id
    private String id;
    private String title;
    private String artist;
    private String partyId;  // Reference to Party
    private String path;

    public SongEntity() {}

    public SongEntity(String title, String artist, String partyId, String path) {
        this.title = title;
        this.artist = artist;
        this.partyId = partyId;
        this.path = path;
    }

    //Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getArtist(){
        return artist;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public String getPartyId(){
        return partyId;
    }

    public void setPartyId(String partyId){
        this.partyId = partyId;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

}
package ro.unibuc.booking.dto;
import java.util.List;
import java.util.Map;


public class Artist {
    
    private String id;
    private String name;
    private List<String> photos; // first photo is the profile photo, the rest are in the gallery
    private String description;
    private Map<String, Number> prices;
    private List<String> eventsContent; // links to photos and videos from events


    public Artist() {}

    public Artist(String name, List<String> photos, String description, Map<String, Number> prices, List<String> eventsContent) {
        this.name = name;
        this.photos = photos;
        this.description = description;
        this.prices = prices;
        this.eventsContent = eventsContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Number> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Number> prices) {
        this.prices = prices;
    }

    public List<String> getEventsContent() {
        return eventsContent;
    }

    public void setEventsContent(List<String> eventsContent) {
        this.eventsContent = eventsContent;
    }

}

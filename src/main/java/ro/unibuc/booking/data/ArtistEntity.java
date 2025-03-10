package ro.unibuc.booking.data;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;

public class ArtistEntity {

    @Id
    private String id;

    private String name;
    private List<String> photos; // first photo is the profile photo, the rest are in the gallery
    private String description;
    private Map<String, Number> prices;
    private List<String> eventsContent; // links to photos and videos from events


    public ArtistEntity() {}

    public ArtistEntity(String name, List<String> photos, String description, Map<String, Number> prices, List<String> eventsContent) {
        this.name = name;
        this.photos = photos;
        this.description = description;
        this.prices = prices;
        this.eventsContent = eventsContent;
    }

    public ArtistEntity(String id, String name, List<String> photos, String description, Map<String, Number> prices, List<String> eventsContent) {
        this.id = id;
        this.name = name;
        this.photos = photos;
        this.description = description;
        this.prices = prices;
        this.eventsContent = eventsContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format(
                "Artist[id='%s', name='%s', photos='%s', description='%s', prices='%s', eventsContent='%s']",
                id, name, photos, description, prices, eventsContent);
    }

    
}

package ro.unibuc.link.data;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class CollectionEntity {
    @Id
    private String collectionName;
    private List<UrlEntity> urls;
    private String privateWord;

    public CollectionEntity(String collectionName, List<UrlEntity> urls, String privateWord) {
        this.collectionName = collectionName;
        this.urls = urls;
        this.privateWord = privateWord;
    }
}
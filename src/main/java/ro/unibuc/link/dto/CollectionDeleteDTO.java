package ro.unibuc.link.dto;

import lombok.Data;

@Data
public class CollectionDeleteDTO {
    private String collectionName;
    private String privateWord;

    public CollectionDeleteDTO(String collectionName, String privateWord){
        this.collectionName = collectionName;
        this.privateWord = privateWord;
    }
}
package ro.unibuc.link.dto;

import lombok.Data;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.UrlEntity;

import java.util.List;

@Data
public class WrapperDTO {
    private CollectionDeleteDTO collectionDeleteDTO;
    private UrlEntity urlEntity;

    public WrapperDTO(CollectionDeleteDTO collectionDeleteDTO, UrlEntity urlEntity){
        this.collectionDeleteDTO = collectionDeleteDTO;
        this.urlEntity = urlEntity;
    }

}
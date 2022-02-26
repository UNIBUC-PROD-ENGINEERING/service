package ro.unibuc.link.dto;

import lombok.Data;
import ro.unibuc.link.data.UrlEntity;

@Data
public class UrlShowDTO {
    private String internalUrl;
    private String externalUrl;

    public UrlShowDTO(String internalUrl, String externalUrl) {
        this.internalUrl = internalUrl;
        this.externalUrl = externalUrl;
    }

    public UrlShowDTO(UrlEntity entity) {
        this.internalUrl = entity.getInternalUrl();
        this.externalUrl = entity.getExternalUrl();
    }
}

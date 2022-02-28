package ro.unibuc.link.dto;

import lombok.Data;

@Data
public class UrlDeleteDTO {
    private String internalUrl;
    private String deleteWord;

    public UrlDeleteDTO(String internalUrl, String deleteWord) {
        this.internalUrl = internalUrl;
        this.deleteWord = deleteWord;
    }
}

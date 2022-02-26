package ro.unibuc.link.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.dto.UrlShowDTO;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    public String getRedirectMapping(String url) {
        var redirectedUrl = urlRepository.findById(url)
                .map(entity -> "redirect:" + entity.getExternalUrl());
        if (redirectedUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
        }
        return redirectedUrl.get();
    }

    public UrlShowDTO setRedirectMapping(UrlEntity urlEntity) {
        if (urlRepository.findById(urlEntity.getInternalUrl()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Url already occupied");
        }
        return new UrlShowDTO(urlRepository.save(urlEntity));
    }

    public boolean checkInternalUrlIsAvailable(String url) {
        return urlRepository.findById(url).isEmpty();
    }

    public UrlShowDTO deleteRedirectMapping(String internalUrl, String deleteWord) {
        if (internalUrl == null || deleteWord == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required Information Is Lacking");
        }
        var optionalUrl = urlRepository.findById(internalUrl);
        if (optionalUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
        }
        var result = optionalUrl.get();
        if (!deleteWord.equals(result.getDeleteWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Delete word doesn't match requested url");
        }
        urlRepository.delete(result);
        return new UrlShowDTO(result);
    }
}
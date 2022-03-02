package ro.unibuc.link.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.CollectionRepository;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.dto.CollectionDeleteDTO;
import ro.unibuc.link.dto.CollectionShowDTO;
import ro.unibuc.link.dto.UrlShowDTO;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    public CollectionShowDTO setCollection(CollectionEntity collectionEntity) {
        if (collectionRepository.findById(collectionEntity.getCollectionName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Collection name already exists");
        }
        return new CollectionShowDTO(collectionRepository.save(collectionEntity));
    }

    public boolean checkCollectionNameIsAvailable(String name) {
        return collectionRepository.findById(name).isEmpty();
    }

    public CollectionShowDTO deleteCollection(String collectionName, String privateWord) {
        if (collectionName == null || privateWord == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required Information Is Lacking");
        }
        var optionalName = collectionRepository.findById(collectionName);
        if (optionalName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        var result = optionalName.get();
        if (!privateWord.equals(result.getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collectionRepository.delete(result);
        return new CollectionShowDTO(result);
    }

    public CollectionShowDTO addUrlToCollection(CollectionDeleteDTO collectionDeleteDTO, UrlEntity urlEntity) {
        var optionalName = collectionRepository.findById(collectionDeleteDTO.getCollectionName());
        if (optionalName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        var collection = optionalName.get();
        if (!collectionDeleteDTO.getPrivateWord().equals(collection.getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collection.getUrls().add(urlEntity);
        collectionRepository.save(collection);
        return new CollectionShowDTO(collection);
    }

    public CollectionShowDTO removeUrlFromCollection(CollectionDeleteDTO collectionDeleteDTO, UrlEntity urlEntity) {
        var optionalName = collectionRepository.findById(collectionDeleteDTO.getCollectionName());
        if (optionalName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        var collection = optionalName.get();
        if (!collectionDeleteDTO.getPrivateWord().equals(collection.getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collection.getUrls().remove(urlEntity);
        collectionRepository.save(collection);
        return new CollectionShowDTO(collection);
    }
}
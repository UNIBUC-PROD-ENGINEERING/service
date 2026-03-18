package ro.unibuc.prodeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.prodeng.model.RestaurantEntity;
import ro.unibuc.prodeng.repository.RestaurantRepository;
import ro.unibuc.prodeng.response.RestaurantResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<RestaurantResponse> getAll() {
        return restaurantRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RestaurantResponse getById(String id) {
        return restaurantRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurantul nu a fost gasit"));
    }

    // Aici este metoda cu Business Logic (Validare Rating)
    public RestaurantResponse create(RestaurantResponse dto) {
        if (dto.getRating() != null && (dto.getRating() < 1.0 || dto.getRating() > 5.0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating-ul trebuie sa fie intre 1.0 si 5.0");
        }
        
        RestaurantEntity entity = new RestaurantEntity(
            null, 
            dto.getName(), 
            dto.getAddress(), 
            dto.getCuisineType(), 
            dto.getRating()
        );
        
        RestaurantEntity saved = restaurantRepository.save(entity);
        return mapToResponse(saved);
    }

    public void delete(String id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nu s-a putut sterge: Restaurantul nu exista");
        }
        restaurantRepository.deleteById(id);
    }

    private RestaurantResponse mapToResponse(RestaurantEntity entity) {
        return new RestaurantResponse(
            entity.getId(), 
            entity.getName(), 
            entity.getAddress(), 
            entity.getCuisineType(), 
            entity.getRating()
        );
    }
}
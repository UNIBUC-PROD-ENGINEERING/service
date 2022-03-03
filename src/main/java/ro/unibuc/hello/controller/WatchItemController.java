package ro.unibuc.hello.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.WatchItemDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WatchItemController {

    @Autowired
    private WatchItemRepository watchItemRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/watchItem/getAll")
    @ResponseBody
    public List<WatchItemDTO> getWatchItems() {
        ArrayList<WatchItemDTO> WatchItemDTOs = new ArrayList<>();
        watchItemRepository.findAll().forEach(watchItemEntity -> WatchItemDTOs.add(new WatchItemDTO(watchItemEntity)));
        return WatchItemDTOs;
    }

    @GetMapping("/watchItem/get")
    @ResponseBody
    public WatchItemDTO getWatchItem(@RequestParam(name="id") String id) {
        if(id != null) {
            WatchItemEntity watchItem = watchItemRepository.findById(id).orElse(null);
            if(watchItem != null) {
                return new WatchItemDTO(watchItem);
            }
        }
        return null;
    }

    @PostMapping("/watchItem/insert")
    @ResponseBody
    public WatchItemDTO insertWatchItem(@RequestParam(name="movieId") String movieId, @RequestParam(name="userId") String userId) {
        MovieEntity movie = movieRepository.findById(String.valueOf(new ObjectId(movieId))).orElse(null);
        UserEntity user = userRepository.findById(String.valueOf(new ObjectId(userId))).orElse(null);
        if(movie != null && user != null)
            return new WatchItemDTO(watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie, user))));
        else 
            return null;
    }

    @PutMapping("/watchItem/update")
    @ResponseBody
    public WatchItemDTO updateWatchItem(
            @RequestParam(name="id") String id,
            @RequestParam(name="newMovieId", required = false) String newMovieId, @RequestParam(name="newUserId", required = false) String newUserId
                                        ) {
        WatchItemEntity watchItem = null;
        if(id != null)
            watchItem = watchItemRepository.findById(id).orElse(null);
        if(watchItem != null) {
            if(newMovieId != null) {
                MovieEntity newMovie = movieRepository.findById(String.valueOf(new ObjectId(newMovieId))).orElse(null);
                if(newMovie != null)
                    watchItem.setCompositeKey(new WatchItemEntity.CompositeKey(newMovie, watchItem.getCompositeKey().getUser()));
            }
            if(newUserId != null) {
                UserEntity newUser = userRepository.findById(String.valueOf(new ObjectId(newUserId))).orElse(null);
                if(newUser != null)
                    watchItem.setCompositeKey(new WatchItemEntity.CompositeKey(watchItem.getCompositeKey().getMovie(), newUser));
            }
            return new WatchItemDTO(watchItemRepository.save(watchItem));
        } else
            return  null;
    }

    @DeleteMapping("/watchItem/delete")
    @ResponseBody
    public void deleteWatchItem(@RequestParam(name="id") String id) {
            watchItemRepository.deleteById(id);
    }
}

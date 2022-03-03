package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.UserDTO;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WatchItemRepository watchItemRepository;

    @GetMapping("/user/getAll")
    @ResponseBody
    public List<UserDTO> getUsers() {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        userRepository.findAll().forEach(userEntity -> userDTOs.add(new UserDTO(userEntity)));
        return userDTOs;
    }

    @GetMapping("/user/get")
    @ResponseBody
    public UserDTO getUser(@RequestParam(name="id") String id) {
        UserEntity user = userRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(user != null)
            return new UserDTO(user);
        else
            return null;
    }

    @PostMapping("/user/insert")
    @ResponseBody
    public UserDTO insertUser(
            @RequestParam(name="name") String name, @RequestParam(name="email") String email,
            @RequestParam(name="reviewIds") List<String> reviewIds, @RequestParam(name="watchItemIds") List<String> watchItemIds
    ) {
        UserEntity user = new UserEntity(name, email);
        ArrayList<ReviewEntity> reviewEntities = new ArrayList<>();
        ArrayList<WatchItemEntity> watchItemEntities = new ArrayList<>();

        if(!reviewIds.isEmpty())
            reviewIds.forEach(id -> reviewEntities.add(reviewRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            user.setReviews(null);

        if(!watchItemIds.isEmpty())
            watchItemIds.forEach(id -> watchItemEntities.add(watchItemRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            user.setWatchItems(null);

        if(!reviewEntities.isEmpty())
            user.setReviews(reviewEntities);

        if(!watchItemEntities.isEmpty())
            user.setWatchItems(watchItemEntities);

        return new UserDTO(userRepository.save(user));
    }

    @PutMapping("/user/update")
    @ResponseBody
    public UserDTO updateUser(
            @RequestParam(name="id") String id, @RequestParam(name="name", required = false) String name, @RequestParam(name="email", required = false) String email,
            @RequestParam(name="reviewIds") List<String> reviewIds, @RequestParam(name="watchItemIds") List<String> watchItemIds
    ) {
        UserEntity user = userRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(user != null) {
            if(name != null)
                user.setName(name);

            if(email != null)
                user.setEmail(email);

            if(!reviewIds.isEmpty()) {
                ArrayList<ReviewEntity> reviewEntities = new ArrayList<>();
                reviewIds.forEach(reviewId -> reviewEntities.add(reviewRepository.findById(String.valueOf(new ObjectId(reviewId))).orElse(null)));
                if(!reviewEntities.isEmpty())
                    user.setReviews(reviewEntities);
            }
            else
                user.setReviews(null);

            if(!watchItemIds.isEmpty()) {
                ArrayList<WatchItemEntity> watchItemEntities = new ArrayList<>();
                watchItemIds.forEach(watchItemId -> watchItemEntities.add(watchItemRepository.findById(String.valueOf(new ObjectId(watchItemId))).orElse(null)));
                if(!watchItemEntities.isEmpty())
                    user.setWatchItems(watchItemEntities);
            }
            else
                user.setWatchItems(null);

            return new UserDTO(userRepository.save(user));
        } else
            return  null;
    }

    @DeleteMapping("/user/delete")
    @ResponseBody
    public void deleteUser(@RequestParam(name="id") String id) {
        userRepository.deleteById(String.valueOf(new ObjectId(id)));
    }

}

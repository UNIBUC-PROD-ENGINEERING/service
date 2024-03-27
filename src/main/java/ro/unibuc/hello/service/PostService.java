package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.PostRepository;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

@Component
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired UserRepository userRepository;

    public PostEntity getPost(String id) throws EntityNotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND.toString()));
    }

    public String addPost(PostDto post) {
        PostEntity postEntity = new PostEntity(
            post.getTitle(), 
            post.getLocation(), 
            LocalDateTime.now(),
            post.getTotalNumberOfPlayers()
        );
        postRepository.save(postEntity);
        return "Post added";
    }

    public String deletePostById(String id) {
        postRepository.deleteById(id);
        return "Post deleted";
    }

    public String registerUserToPost(String userId, String postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException(HttpStatus.NOT_FOUND.toString()));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(HttpStatus.NOT_FOUND.toString()));

        post.addUser(user);
        postRepository.save(post);
        return "User registered to post";
    }
}
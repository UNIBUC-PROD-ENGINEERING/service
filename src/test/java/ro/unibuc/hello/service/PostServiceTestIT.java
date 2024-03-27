package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.data.PostRepository;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("IT")
class PostServiceTestIT {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Test
    void testGetPost() throws Exception {
        PostEntity postEntity = new PostEntity("Fotbal Sintetic", "Vaslui", LocalDateTime.now(), 10);
        postRepository.save(postEntity);

        PostEntity retrievedPost = postService.getPost(postEntity.id);

        assertNotNull(retrievedPost);
        assertEquals(postEntity.id, retrievedPost.id);
        assertEquals(postEntity.title, retrievedPost.title);
        assertEquals(postEntity.location, retrievedPost.location);
        assertEquals(postEntity.totalNumberOfPlayers, retrievedPost.totalNumberOfPlayers);
    }

    @Test
    void testGetPostNonExistent() {

        assertThrows(EntityNotFoundException.class, () -> postService.getPost("nonexistent_id"));
    }

    @Test
    void testAddPost() {
        PostDto newPost = new PostDto("Fotbalul Intergalactic", "Calea Lactee", 269);
        Long initialCount = postRepository.count();

        String result = postService.addPost(newPost);

        assertEquals("Post added", result);
        assertEquals(initialCount + 1, postRepository.count());
    }

    @Test
    void testDeletePostById() {
        PostEntity postEntity = new PostEntity("Fotbal pe sate", "Zapodeni City", LocalDateTime.now(), 10);
        postRepository.save(postEntity);
        Long initialCount = postRepository.count();

        String result = postService.deletePostById(postEntity.id);

        assertEquals("Post deleted", result);
        assertEquals(initialCount - 1, postRepository.count());
    }

}

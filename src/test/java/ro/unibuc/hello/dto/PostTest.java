package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PostDtoTest {

    String title = "Fotbal 5+1";
    String location = "Strand Arena";
    Integer totalNumberOfPlayers = 18;
    PostDto postDto = new PostDto(title, location, totalNumberOfPlayers);

    @Test
    void testGetters() {
        Assertions.assertEquals(title, postDto.getTitle());
        Assertions.assertEquals(location, postDto.getLocation());
        Assertions.assertEquals(totalNumberOfPlayers, postDto.getTotalNumberOfPlayers());
    }

    @Test
    void testSetters() {
        PostDto thePost = new PostDto(title, location, totalNumberOfPlayers);
        String newTitle = "Fotbal FC Vaslui";
        thePost.setTitle(newTitle);
        Assertions.assertEquals(newTitle, thePost.getTitle());

        Integer newTotalNumberOfPlayers = 20;
        thePost.setTotalNumberOfPlayers(newTotalNumberOfPlayers);
        Assertions.assertEquals(newTotalNumberOfPlayers, thePost.getTotalNumberOfPlayers());
    }

    @Test
    void testNullFields() {
        PostDto nullPostDto = new PostDto(null, null, null);

        Assertions.assertNull(nullPostDto.getTitle());
        Assertions.assertNull(nullPostDto.getLocation());
        Assertions.assertNull(nullPostDto.getTotalNumberOfPlayers());
    }

    @Test
    void testEquality() {
        PostDto samePostDto = new PostDto(title, location, totalNumberOfPlayers);
        Assertions.assertEquals(postDto, samePostDto);
    }

    @Test
    void testInequality() {
        PostDto differentPostDto = new PostDto("Fotbal Sintetic", "Bazar Areba", 24);
        Assertions.assertNotEquals(postDto, differentPostDto);
    }
}

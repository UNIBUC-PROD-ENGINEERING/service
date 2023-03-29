package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.WishlistCreationDTO;
import com.bookstore.v1.dto.WishlistDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("IT")
@DisplayName("Wishlist Service Integration Test")
public class WishlistServiceTestIT {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private BookRepository bookRepository;

    private User user1;
    private Wishlist wishlist1;
    private Book book1;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        wishlistRepository.deleteAll();
        bookRepository.deleteAll();

        user1 = new User("John Doe", "john.doe@example.com", "1234567890");
        userRepository.save(user1);

        wishlist1 = new Wishlist("Fiction");
        wishlist1.setUser(user1);
        wishlistRepository.save(wishlist1);

        book1 = new Book("The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", "1234567890123", LocalDate.of(1951, 7, 16));
        bookRepository.save(book1);
    }

    @Test
    public void testAddWishlist() {
        WishlistCreationDTO dto = new WishlistCreationDTO(null, "Test Wishlist", user1.getId());

        WishlistDTO result = wishlistService.addWishlist(dto);

        assertNotNull(result);
        assertEquals(result.getTitle(),dto.getTitle());

        Wishlist savedWishlist = wishlistRepository.findById(result.getId()).orElse(null);
        assertNotNull(savedWishlist);
        assertEquals(savedWishlist.getTitle(),dto.getTitle());
    }

    @Test
    public void testUpdateWishlist() {
        Wishlist wishlist = new Wishlist("Initial Title");
        wishlist.setUser(user1);
        wishlist = wishlistRepository.save(wishlist);

        WishlistCreationDTO dto = new WishlistCreationDTO(wishlist.getId(), "Updated Title", user1.getId());

        WishlistDTO result = wishlistService.updateWishlist(dto);

        assertNotNull(result);
        assertEquals(dto.getTitle(), result.getTitle());

        Wishlist updatedWishlist = wishlistRepository.findById(result.getId()).orElse(null);
        assertNotNull(updatedWishlist);
        assertEquals(dto.getTitle(), updatedWishlist.getTitle());
    }

    @Test
    public void addBookTest() throws EntityNotFoundException, DuplicateObjectException {
        WishlistDTO wishlistDTO = wishlistService.addBook(wishlist1.getId(), book1.getId());
        assertNotNull(wishlistDTO);
        assertEquals(1, wishlistDTO.getBooks().size());
        assertEquals("The Catcher in the Rye", wishlistDTO.getBooks().get(0).getTitle());
    }

    @Test
    public void getWishlistsTest() {
        List<WishlistDTO> wishlists = wishlistService.getWishlists();
        assertNotNull(wishlists);
        assertEquals(1, wishlists.size());
        assertEquals("Fiction", wishlists.get(0).getTitle());
    }


    @Test
    public void testDeleteWishlistById() {
        Wishlist wishlist = new Wishlist("Test Wishlist");
        wishlist.setUser(user1);
        wishlist = wishlistRepository.save(wishlist);

        wishlistService.deleteWishlistById(wishlist.getId());

        Wishlist deletedWishlist = wishlistRepository.findById(wishlist.getId()).orElse(null);
        assertNull(deletedWishlist);
    }
}
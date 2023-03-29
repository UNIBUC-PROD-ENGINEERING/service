package com.bookstore.v1.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.WishlistCreationDTO;
import com.bookstore.v1.dto.WishlistDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.services.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private User testUser;
    private Book testBook;
    private Wishlist testWishlist;

    @BeforeEach
    void setUp() {
        testUser = new User("1", "John Doe", "john@example.com", "1234567890");
        testBook = new Book("1", "The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", "1234567890123", LocalDate.of(1951, 7, 16));
        testWishlist = new Wishlist("1", "Test Wishlist");
        testWishlist.setUser(testUser);
        testWishlist.addBook(testBook);
    }

    @Test
    void testAddWishlist() throws EmptyFieldException, InvalidDoubleRange, EntityNotFoundException {
        // Arrange
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO(testUser.getId(), "1");
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(testWishlist);

        // Act
        WishlistDTO result = wishlistService.addWishlist(wishlistCreationDTO);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getTitle());
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testUpdateWishlist() throws EmptyFieldException, InvalidDoubleRange, EntityNotFoundException {
        // Arrange
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO("Updated Title", testUser.getId());
        wishlistCreationDTO.setId(testWishlist.getId());
        when(wishlistRepository.findById(testWishlist.getId())).thenReturn(Optional.of(testWishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(testWishlist);

        // Act
        WishlistDTO result = wishlistService.updateWishlist(wishlistCreationDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(wishlistRepository, times(1)).findById(testWishlist.getId());
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testDeleteWishlistById() throws EntityNotFoundException {
        // Arrange
        when(wishlistRepository.findById(testWishlist.getId())).thenReturn(Optional.of(testWishlist));

        // Act
        wishlistService.deleteWishlistById(testWishlist.getId());

        // Assert
        verify(wishlistRepository, times(1)).findById(testWishlist.getId());
        verify(wishlistRepository, times(1)).delete(testWishlist);
    }

    @Test
    void testGetWishlists() {
        // Arrange
        List<Wishlist> wishlists = Collections.singletonList(testWishlist);
        when(wishlistRepository.findAll()).thenReturn(wishlists);

        // Act
        List<WishlistDTO> result = wishlistService.getWishlists();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testWishlist.getId(), result.get(0).getId());
        assertEquals(testWishlist.getTitle(), result.get(0).getTitle());
        verify(wishlistRepository, times(1)).findAll();
    }

    @Test
    void testGetUserWishlists() throws EntityNotFoundException {
        // Arrange
        List<Wishlist> wishlists = Collections.singletonList(testWishlist);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(wishlistRepository.findAllByUser(testUser)).thenReturn(wishlists);

        // Act
        List<WishlistDTO> result = wishlistService.getUserWishlists(testUser.getId());

        // Assert
        assertEquals(1, result.size());
        assertEquals(testWishlist.getId(), result.get(0).getId());
        assertEquals(testWishlist.getTitle(), result.get(0).getTitle());
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(wishlistRepository, times(1)).findAllByUser(testUser);
    }

    @Test
    void testAddBook() throws EntityNotFoundException, DuplicateObjectException {
        // Arrange
        String newBookId = "2";
        Book newBook = new Book(newBookId, "New Book", "New Author", "New Publisher", "New ISBN", LocalDate.of(1951, 7, 16));
        when(wishlistRepository.findById(testWishlist.getId())).thenReturn(Optional.of(testWishlist));
        when(bookRepository.findById(newBookId)).thenReturn(Optional.of(newBook));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(testWishlist);

        // Act
        WishlistDTO result = wishlistService.addBook(testWishlist.getId(), newBookId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getBooks().size());
        assertEquals(newBook.getId(), result.getBooks().get(1).getId());
        assertEquals(newBook.getTitle(), result.getBooks().get(1).getTitle());
        verify(wishlistRepository, times(1)).findById(testWishlist.getId());
        verify(bookRepository, times(1)).findById(newBookId);
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }
}
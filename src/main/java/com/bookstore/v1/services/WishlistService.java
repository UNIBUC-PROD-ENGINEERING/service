package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.*;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.validations.ReviewValidations;
import com.bookstore.v1.validations.WishlistValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public WishlistDTO addWishlist(WishlistCreationDTO wishlistCreationDTO) throws EmptyFieldException, InvalidDoubleRange,
            EntityNotFoundException {
        // validate wishlist creation dto without the id since it will be auto-generated
        WishlistValidations.validateWishlistCreationDTO(wishlistCreationDTO, false);

        Optional<User> user = userRepository.findById(wishlistCreationDTO.getUserId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user");
        }

        Wishlist wishlist = wishlistCreationDTO.toWishlist(true);

        wishlist.setUser(user.get());
        wishlistRepository.save(wishlist);
        return new WishlistDTO(wishlist, true);
    }

    public WishlistDTO updateWishlist(WishlistCreationDTO wishlistCreationDTO) throws EmptyFieldException, InvalidDoubleRange,
            EntityNotFoundException {
        // validate wishlist update dto with the id since the object should already exist
        WishlistValidations.validateWishlistCreationDTO(wishlistCreationDTO, true);

        Optional<Wishlist> oldWishlistOpt = wishlistRepository.findById(wishlistCreationDTO.getId());
        if (oldWishlistOpt.isEmpty()) {
            throw new EntityNotFoundException("wishlist");
        }

        Wishlist newWishlist = oldWishlistOpt.get();
        newWishlist.setTitle(wishlistCreationDTO.getTitle());
        wishlistRepository.save(newWishlist);

        return new WishlistDTO(newWishlist, true);
    }

    public void deleteWishlistById(String wishlistId) throws EntityNotFoundException {
        Optional<Wishlist> wishlistToDelete = wishlistRepository.findById(wishlistId);
        if (wishlistToDelete.isEmpty()) {
            throw new EntityNotFoundException("wishlist");
        }
        wishlistRepository.delete(wishlistToDelete.get());
    }

    public List<WishlistDTO> getWishlists() {
        return wishlistRepository
                .findAll()
                .stream()
                .map(wishlist -> new WishlistDTO(wishlist, true))
                .collect(Collectors.toList());
    }

    public List<WishlistDTO> getUserWishlists(String userId) throws EntityNotFoundException{
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user");
        }
        return wishlistRepository
                .findAllByUser(user.get())
                .stream()
                .map(wishlist -> new WishlistDTO(wishlist, true))
                .collect(Collectors.toList());
    }

    public WishlistDTO addBook(String wishlistId, String bookId)  throws EntityNotFoundException, DuplicateObjectException{
        Optional<Wishlist> wishlistOpt = wishlistRepository.findById(wishlistId);
        if (wishlistOpt.isEmpty()) {
            throw new EntityNotFoundException("wishlist");
        }
        Wishlist wishlist = wishlistOpt.get();
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new EntityNotFoundException("book");
        }
        Book book = bookOpt.get();
        for(Book b : wishlist.getBooks()){
            System.out.println(b);
            if(Objects.equals(b.getId(), book.getId())){
                throw new DuplicateObjectException("duplicated book in wishlist");
            }
        }

        wishlist.addBook(book);
        wishlistRepository.save(wishlist);
        return new WishlistDTO(wishlist,true);
    }
}

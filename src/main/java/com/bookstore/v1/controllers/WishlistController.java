package com.bookstore.v1.controllers;


import com.bookstore.v1.dto.WishlistCreationDTO;
import com.bookstore.v1.dto.WishlistDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.services.ReviewService;
import com.bookstore.v1.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;
    @GetMapping("/get-wishlists")
    @ResponseBody
    public List<WishlistDTO> getWishlists(){
        return wishlistService.getWishlists();
    }

    @GetMapping("{userId}/get-wishlists")
    @ResponseBody
    public List<WishlistDTO> getUserWishlists(@PathVariable String userId){
        return wishlistService.getUserWishlists(userId);
    }

    @PostMapping("/add-wishlist")
    @ResponseBody
    public WishlistDTO addWishlist(@RequestBody WishlistCreationDTO wishlistCreationDTO) throws EmptyFieldException,
            InvalidDoubleRange, EntityNotFoundException {
        return wishlistService.addWishlist(wishlistCreationDTO);
    }

    @PutMapping("/update-wishlist")
    @ResponseBody
    public WishlistDTO updateWishlist(@RequestBody WishlistCreationDTO wishlistCreationDTO) throws EmptyFieldException,
            InvalidDoubleRange, EntityNotFoundException {
        return wishlistService.updateWishlist(wishlistCreationDTO);
    }

    @DeleteMapping("/delete-wishlist/{wishlistId}")
    @ResponseBody
    public void deleteWishlist(@PathVariable String wishlistId) throws EntityNotFoundException {
        wishlistService.deleteWishlistById(wishlistId);
    }

    @PostMapping("/{wishlistId}/add-book/{bookId}")
    @ResponseBody
    public WishlistDTO addBook(@PathVariable String wishlistId, @PathVariable String bookId) {
        return wishlistService.addBook(wishlistId,bookId);
    }

}

package com.bookstore.v1.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WishlistDTOTest {


    @Test
    public void test_constructor1(){
        WishlistDTO wishlistDTO = new WishlistDTO("TestWishlistDTO");
        Assertions.assertEquals("TestWishlistDTO", wishlistDTO.getTitle());
    }
    @Test
    public void test_constructor2(){
        BookDTO bookDTO1 = new BookDTO();
        BookDTO bookDTO2 = new BookDTO();
        List<BookDTO> booksList = new ArrayList<BookDTO>(Arrays.asList(bookDTO1,bookDTO2));
        UserDTO userDTO = new UserDTO();
        WishlistDTO wishlistDTO = new WishlistDTO("idDTOTest","TestWishlistDTO","idUserTest", userDTO, booksList);

        Assertions.assertEquals("TestWishlistDTO", wishlistDTO.getTitle());
        Assertions.assertEquals("idDTOTest", wishlistDTO.getId());
        Assertions.assertEquals("idUserTest", wishlistDTO.getUserId());
        Assertions.assertEquals(userDTO, wishlistDTO.getUser());
        Assertions.assertEquals(booksList, wishlistDTO.getBooks());
    }

    @Test
    public void test_id(){
        WishlistDTO wishlistDTO=new WishlistDTO();
        wishlistDTO.setId("SettingId");
        Assertions.assertEquals("SettingId", wishlistDTO.getId());
    }
    @Test
    public void test_title(){
        WishlistDTO wishlistDTO=new WishlistDTO();
        wishlistDTO.setTitle("SettingTitle");
        Assertions.assertEquals("SettingTitle", wishlistDTO.getTitle());
    }
    @Test
    public void test_userId(){
        WishlistDTO wishlistDTO=new WishlistDTO();
        wishlistDTO.setUserId("SettingUserId");
        Assertions.assertEquals("SettingUserId", wishlistDTO.getUserId());
    }
    @Test
    public void test_user(){
        WishlistDTO wishlistDTO=new WishlistDTO();
        UserDTO userDTO = new UserDTO();
        wishlistDTO.setUser(userDTO);
        Assertions.assertEquals(userDTO, wishlistDTO.getUser());
    }
    @Test
    public void test_books(){
        WishlistDTO wishlistDTO=new WishlistDTO();
        BookDTO bookDTO1 = new BookDTO();
        BookDTO bookDTO2 = new BookDTO();
        List<BookDTO> booksList = new ArrayList<BookDTO>(Arrays.asList(bookDTO1,bookDTO2));
        wishlistDTO.setBooks(booksList);
        Assertions.assertEquals(booksList, wishlistDTO.getBooks());
    }
}

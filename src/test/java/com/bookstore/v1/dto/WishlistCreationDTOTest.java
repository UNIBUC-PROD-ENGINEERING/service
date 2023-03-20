package com.bookstore.v1.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class WishlistCreationDTOTest {

    @Test
    public void test_constructor1(){
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO();
    }
    @Test
    public void test_constructor2(){
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO("TitleTest", "UserIdTest");
        Assertions.assertEquals("TitleTest", wishlistCreationDTO.getTitle());
        Assertions.assertEquals("UserIdTest", wishlistCreationDTO.getUserId());
    }
    @Test
    public void test_constructor3(){
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO("IdTest","TitleTest", "UserIdTest");
        Assertions.assertEquals("IdTest", wishlistCreationDTO.getId());
        Assertions.assertEquals("TitleTest", wishlistCreationDTO.getTitle());
        Assertions.assertEquals("UserIdTest", wishlistCreationDTO.getUserId());
    }
    @Test
    public void test_id(){
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO();
        wishlistCreationDTO.setId("IdTest");
        Assertions.assertEquals("IdTest", wishlistCreationDTO.getId());
    }
    @Test
    public void test_title(){
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO();
        wishlistCreationDTO.setTitle("TitleTest");
        Assertions.assertEquals("TitleTest", wishlistCreationDTO.getTitle());
    }
    @Test
    public void test_userId(){
        WishlistCreationDTO wishlistCreationDTO = new WishlistCreationDTO();
        wishlistCreationDTO.setUserId("IdTest");
        Assertions.assertEquals("IdTest", wishlistCreationDTO.getUserId());
    }

}

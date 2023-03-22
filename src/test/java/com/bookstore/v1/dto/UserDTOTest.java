package com.bookstore.v1.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class UserDTOTest {

    @Test
    public void test_constructor1(){
        UserDTO userDTO = new UserDTO();
    }
    @Test
    public void test_constructor2(){
        UserDTO userDTO = new UserDTO("IdTest", "UserNameTest", "EmailTest", "PhoneNumberTest");
        Assertions.assertEquals("IdTest", userDTO.getId());
        Assertions.assertEquals("UserNameTest", userDTO.getUserName());
        Assertions.assertEquals("EmailTest", userDTO.getEmail());
        Assertions.assertEquals("PhoneNumberTest", userDTO.getPhoneNumber());
    }
    @Test
    public void test_id(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId("IdTest");
        Assertions.assertEquals("IdTest", userDTO.getId());
    }
    @Test
    public void test_userName(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("UserNameTest");
        Assertions.assertEquals("UserNameTest", userDTO.getUserName());
    }
    @Test
    public void test_email(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("EmailTest");
        Assertions.assertEquals("EmailTest", userDTO.getEmail());
    }
    @Test
    public void test_phoneNumber(){
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("PhoneNumberTest");
        Assertions.assertEquals("PhoneNumberTest", userDTO.getPhoneNumber());
    }

}

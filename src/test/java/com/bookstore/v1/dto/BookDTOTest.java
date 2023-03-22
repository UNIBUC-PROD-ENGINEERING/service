package com.bookstore.v1.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDate;

public class BookDTOTest {

    @Test
    public void test_constructor1(){
        BookDTO bookDTO = new BookDTO();
    }
    @Test
    public void test_constructor2(){
        BookDTO bookDTO = new BookDTO("IdTest", "TitleTest", "AuthorTest", "PublisherTest", "IsbnTest",  LocalDate.now());
        Assertions.assertEquals("IdTest", bookDTO.getId());
        Assertions.assertEquals("TitleTest", bookDTO.getTitle());
        Assertions.assertEquals("AuthorTest", bookDTO.getAuthor());
        Assertions.assertEquals("PublisherTest", bookDTO.getPublisher());
        Assertions.assertEquals("IsbnTest", bookDTO.getIsbn());
        Assertions.assertEquals(LocalDate.now(), bookDTO.getPublishedDate());
    }
    @Test
    public void test_id(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId("IdTest");
        Assertions.assertEquals("IdTest", bookDTO.getId());
    }
    @Test
    public void test_Title(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("BookTitle");
        Assertions.assertEquals("BookTitle", bookDTO.getTitle());
    }
    @Test
    public void test_author(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("AuthorTest");
        Assertions.assertEquals("AuthorTest", bookDTO.getAuthor());
    }
    @Test
    public void test_publisher(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setPublisher("PublisherTest");
        Assertions.assertEquals("PublisherTest", bookDTO.getPublisher());
    }
    @Test
    public void test_isbn(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("IsbnTest");
        Assertions.assertEquals("IsbnTest", bookDTO.getIsbn());
    }
    @Test
    public void test_publishedDate(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setPublishedDate(LocalDate.now());
        Assertions.assertEquals(LocalDate.now(), bookDTO.getPublishedDate());
    }

}
package com.bookstore.v1.dto;

import com.bookstore.v1.data.Book;

import java.time.LocalDate;

public class BookDTO {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String isban;
    private LocalDate publishedDate;

    public BookDTO() {}

    public BookDTO(String id, String title, String author, String publisher, String isban, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isban = isban;
        this.publishedDate = publishedDate;
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isban = book.getIsbn();
        this.publishedDate = book.getPublishedDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsban() {
        return isban;
    }

    public void setIsban(String isban) {
        this.isban = isban;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}

package com.bookstore.v1.dto;

import com.bookstore.v1.data.Book;

import java.time.LocalDate;

public class BookDTO {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private LocalDate publishedDate;

    public BookDTO() {}

    public BookDTO(String id, String title, String author, String publisher, String isbn, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Book toBook(Boolean withoutId) {
        Book book = new Book();
        if (!withoutId) {
            book.setId(id);
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setIsbn(isbn);
        book.setPublishedDate(publishedDate);
        return book;
    }
}
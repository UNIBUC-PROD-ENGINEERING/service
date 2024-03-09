package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class BookEntity {
    @Id
    private String id;

    private String title;
    private String author;

    public BookEntity() {}

    public BookEntity(String title, String author){
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
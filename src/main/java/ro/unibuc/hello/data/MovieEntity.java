package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Objects;

@Document
public class MovieEntity {
    @Id
    private String id;
    private String title;
    private String director;
    private String writer;
    private Integer year;
    private Integer duration;
    @DBRef(lazy = true)
    private ArrayList<ReviewEntity> reviews;
    @DBRef(lazy = true)
    private ArrayList<WatchItemEntity> watchItems;

    public MovieEntity(String title, String director, String writer, Integer year, Integer duration) {
        this.title = title;
        this.director = director;
        this.writer = writer;
        this.year = year;
        this.duration = duration;
    }

    public MovieEntity(String title, String director, String writer, Integer year, Integer duration, ArrayList<ReviewEntity> reviews, ArrayList<WatchItemEntity> watchItems) {
        this.title = title;
        this.director = director;
        this.writer = writer;
        this.year = year;
        this.duration = duration;
        this.reviews = reviews;
        this.watchItems = watchItems;
    }

    public MovieEntity() {
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ArrayList<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<WatchItemEntity> getWatchItems() {
        return watchItems;
    }

    public void setWatchItems(ArrayList<WatchItemEntity> watchItems) {
        this.watchItems = watchItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return id.equals(that.id) && Objects.equals(title, that.title) && Objects.equals(director, that.director) && Objects.equals(writer, that.writer) && Objects.equals(year, that.year) && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, writer, year, duration);
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", year=" + year +
                ", duration=" + duration + " minutes}";
    }
}

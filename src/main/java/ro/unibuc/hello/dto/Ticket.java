package ro.unibuc.hello.dto;

import org.apache.tomcat.jni.Local;
import ro.unibuc.hello.data.MovieEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Ticket {

    LocalDateTime dateTime;

    Movie movie;


    public Ticket(MovieEntity movieEntity, LocalDateTime dateTime){
        Movie movie1 = new Movie(movieEntity.title, movie.description, movie.runtime);
        movie = movie1;

        this.dateTime = dateTime;

    }
    public Movie getMovie(){
        return movie;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

}

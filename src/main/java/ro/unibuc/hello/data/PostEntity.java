package ro.unibuc.hello.data;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class PostEntity {

    @Id
    public String id;
    public String title;
    public String location;
    public LocalDateTime dateTime;
    public Integer totalNumberOfPlayers;
    public Integer playersJoined;


    
}
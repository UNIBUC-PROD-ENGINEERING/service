package ro.unibuc.hello.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.dto.Location;

public class CinemaRoomEntity {

    @Id
    public String id;

    public Integer number;
    @DBRef
    public LocationEntity location;

    public CinemaRoomEntity(LocationEntity location, Integer number){
        this.location = location;
        this.number = number;
    }
    @Override
    public String toString(){
        return String.format(
                "CinemaRoom[location='%s']", location);
    }


}

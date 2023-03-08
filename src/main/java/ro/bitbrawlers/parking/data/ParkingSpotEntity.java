package ro.bitbrawlers.parking.data;

import org.springframework.data.annotation.Id;

public class ParkingSpotEntity {
    @Id
    public Integer id;
    public Person person;

    public ParkingSpotEntity() {
    }

    public ParkingSpotEntity(Integer id, Person person) {
        this.id = id;
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "ParkingSlotEntity{" +
                "id=" + id +
                ", person=" + person +
                '}';
    }
}

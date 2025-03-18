package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class RezervareEntity {

    @Id
    private String id;

    private ApartamentEntity apartament;
    private UserEntity user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public RezervareEntity() {}

    public String getId() {
        return id;
    }

    public ApartamentEntity getApartament() {
        return apartament;
    }

    public ApartamentEntity setApartament(ApartamentEntity apartament) {
        this.apartament = apartament;
    }

    public UserEntity getUser() {
        return user;
    }

    public UserEntity setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean getActive() {
        return active;
    }

    public boolean setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[id='%s']",
                );
    }
}

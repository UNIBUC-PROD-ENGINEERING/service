package main.java.ro.unibuc.hello.dto;

import java.time.LocalDate;

public class RezervareDto {

    private String id;
    private ApartamentEntity apartament;
    private UserEntity user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public RezervareDto() {}

    public RezervareDto(String id, ApartamentEntity apartament, UserEntity user, LocalDate startDate, LocalDate endDate, boolean active) {
        this.id = id;
        this.apartament = apartament;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
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

}

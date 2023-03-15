package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.Cinema;

public class CinemaDTO {

    private String id;
    private String name;
    private String email;
    private String street;
    private int number;
    private String city;


    public CinemaDTO() {
    }

    public CinemaDTO(String id, String name, String email, String street, int number, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.street = street;
        this.number = number;
        this.city = city;
    }

   public CinemaDTO(Cinema cinema){
    this.id = cinema.getId();
    this.name = cinema.getName();
    this.email = cinema.getEmail();
    this.street = cinema.getStreet();
    this.number = cinema.getNumber();
    this.city = cinema.getCity();
   }

   public String getId(){
    return id;

   }
   public void setId(String id) {
    this.id = id;
}

public String getName() {
   return name;
}


public void setName(String name) {
    this.name = name;
}

public String getEmail(){
    return email;
}

public String getStreet(){
    return street;
}

public int getNumber(){
    return number;
}

public String getCity(){
    return city;
}

public Cinema toCinema(Boolean hasId){
    Cinema cinema = new Cinema();
    if (hasId){
        cinema.setId(id);
    }
    cinema.setName(name);
    cinema.setEmail(email);
    cinema.setStreet(street);
    cinema.setNumber(number);
    cinema.setCity(city);

    return cinema;
    
} 

@Override
public String toString(){
    return "Cinema{" + 
            "id='" + id + '\'' +
            ", name='" + name + '\'' + 
            ", email='" + email + '\'' +
            ", street='" + street + '\'' +
            ", number='" + Integer.toString(number) + '\'' +
            ", city='" + city + '}';
}

}
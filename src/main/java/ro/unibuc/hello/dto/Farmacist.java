package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class Farmacist {
    private static long counter = 1;
    private @Id long id;
    private String name;
    private String email;

    public Farmacist() {
    }

    public Farmacist( String name, String email) {
        this.id=counter;
        this.name = name;
        this.email = email;
        counter++;
    }



    public static long getCounter() {
        return counter;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static void setCounter(long counter) {
        Farmacist.counter = counter;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Farmacist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

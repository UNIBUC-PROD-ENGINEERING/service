package ro.unibuc.hello.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    LocalDateTime created = LocalDateTime.now();
    List<String> favorites = List.of("Liguistics");

    Address address = new Address(
            "Romania",
            "Timisoara",
            "10076"
    );
    Student student = new Student(
            "Ana",
            "ana354@gmail.com",
            Gender.FEMALE,
            address,
            favorites,
            created
    );

    @Test
    void getName() {
        Assertions.assertSame("Ana", student.getName());
    }

    @Test
    void getEmail() {
        Assertions.assertSame("ana354@gmail.com", student.getEmail());
    }

    @Test
    void getGender() {
        Assertions.assertSame(Gender.FEMALE, student.getGender());

    }

    @Test
    void getAddress() {
        Assertions.assertSame(address, student.getAddress());

    }

    @Test
    void getFavoriteSubjects() {
        Assertions.assertSame(favorites, student.getFavoriteSubjects());

    }

    @Test
    void getCreated() {
        Assertions.assertSame(created, student.getCreated());

    }

    @Test
    void setName() {
        student.setName("Maria");
        Assertions.assertSame("Maria", student.getName());

    }

    @Test
    void setEmail() {
        student.setEmail("maria536534@gmail.com");
        Assertions.assertSame("maria536534@gmail.com", student.getEmail());
    }

    @Test
    void setGender() {
        student.setGender(Gender.MALE);
        Assertions.assertSame(Gender.MALE, student.getGender());
    }

    @Test
    void setAddress() {
        Address address = new Address(
                "UK",
                "London",
                "30076"
        );
        student.setAddress(address);
        Assertions.assertSame(address, student.getAddress());
    }

    @Test
    void setFavoriteSubjects() {
        List<String> favorites = List.of("Math");
        student.setFavoriteSubjects(favorites);
        Assertions.assertSame(favorites, student.getFavoriteSubjects());
    }

    @Test
    void setCreated() {
        LocalDateTime created = LocalDateTime.now();
        student.setCreated(created);
        Assertions.assertSame(created, student.getCreated());
    }
}
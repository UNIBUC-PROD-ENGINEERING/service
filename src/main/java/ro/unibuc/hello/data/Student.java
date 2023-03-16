package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class Student {
    @Id
    private String id;

    private String name;
    private String email;
    private int age;

    public Student() {}

    public Student(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int age) {
        this.age = age;
    }

}
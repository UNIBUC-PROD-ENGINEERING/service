package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.TicketEntity;

public class Customer {

    private String name;

    private Integer age;

    public Customer() {};

    public Customer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

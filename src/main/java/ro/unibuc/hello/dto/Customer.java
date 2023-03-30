package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class Customer {
    private static long counter = 1;
    private @Id long customer_id;
    private String name;
    private int age;
    private String email;

    public Customer(){}

    public Customer(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.customer_id=counter;
        counter += 1;
    }

    public static long getCounter() {
        return counter;
    }

    public static void setCounter(long counter) {
        Customer.counter = counter;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return '{' +
                "\"customer_id\":" + customer_id +
                ",\"name\":\"" + name + "\"" +
                ",\"age\":" + age +
                ",\"email\":\"" + email + "\"" +
                '}';
    }

}

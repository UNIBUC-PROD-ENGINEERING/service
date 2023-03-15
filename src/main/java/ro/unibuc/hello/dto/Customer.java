package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.TicketEntity;

public class Customer {

    private String name;

    private Integer age;

    private Ticket ticket;

    public Customer(String name, Integer age, TicketEntity ticketEntity) {
        Ticket ticket1 = new Ticket(ticketEntity.movie, ticketEntity.dateTime);
        ticket = ticket1;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}

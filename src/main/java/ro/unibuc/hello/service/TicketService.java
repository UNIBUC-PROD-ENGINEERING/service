package ro.unibuc.hello.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.TicketRespository;
import ro.unibuc.hello.dto.Movie;
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Optional;

@Component
public class TicketService {

    @Autowired
    private TicketRespository ticketRespository;

    public Ticket getTicketById(String Id) throws EntityNotFoundException {
        Optional<TicketEntity> entity = ticketRespository.findById(Id);
        if(entity == null){
            throw new EntityNotFoundException(Id);
        }
        return new Ticket(entity.get().movie, entity.get().dateTime);
    }


}

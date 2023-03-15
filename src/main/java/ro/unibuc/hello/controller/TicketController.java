package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.MovieRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Movie;
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;
import ro.unibuc.hello.service.MovieService;
import ro.unibuc.hello.service.TicketService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/getTicketById")
    @ResponseBody
    public Ticket getTicketById(@RequestParam(name="id", required=true) String id) throws EntityNotFoundException{
        return ticketService.getTicketById(id);
    }


}

package ro.unibuc.hello.controller;

import java.util.List;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ClientDTO;
import ro.unibuc.hello.service.ClientService;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/restaurant/getAll")
    @ResponseBody
    public List<ClientDTO> getRestaurants() {
        return clientService.getClients();
    }

    @GetMapping("/restaurant/get")
    @ResponseBody
    public ClientDTO getClient(@RequestParam(name="id") String id)
    {
        return clientService.getClient(id);
    }
}

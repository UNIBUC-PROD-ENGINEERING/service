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

    @GetMapping("/client/getAll")
    @ResponseBody
    public List<ClientDTO> getRestaurants() {
        return clientService.getClients();
    }

    @GetMapping("/client/get")
    @ResponseBody
    public ClientDTO getClient(@RequestParam(name="id") String id)
    {
        return clientService.getClient(id);
    }


    @PostMapping("/client/insert")
    @ResponseBody
    public ClientDTO insertClient(@RequestParam(name = "name") String name,
                                  @RequestParam(name = "address") String address,
                                  @RequestParam(name = "email") String email,
                                  @RequestParam(name = "ordersId") List<String> ordersID) {
        return clientService.insertClient(name, address, email, ordersID);
    }
        @PutMapping("/client/update")
        @ResponseBody
        public ClientDTO updateClient(@RequestParam(name = "id") String id,
                @RequestParam(name = "name") String name,
                @RequestParam(name = "address") String address,
                @RequestParam(name = "email") String email,
                @RequestParam(name = "ordersId") List<String> ordersID){
            return clientService.updateClient(id,name,address,email,ordersID);
        }
        @DeleteMapping("/client/delete")
        @ResponseBody
        public String deleteClient(@RequestParam(name = "id") String id){
        return clientService.deleteClient(id);
        }

}

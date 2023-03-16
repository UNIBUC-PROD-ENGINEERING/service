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
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/get-all")
    @ResponseBody
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/get")
    @ResponseBody
    public ClientDTO getClient(@RequestParam(name="id") String id)
    {
        return clientService.getClient(id);
    }


    @PostMapping("/insert")
    @ResponseBody
    public ClientDTO insertClient(@RequestParam(name = "name") String name,
                                  @RequestParam(name = "address") String address,
                                  @RequestParam(name = "email") String email,
                                  @RequestParam(name = "ordersId") List<String> ordersID) {
        return clientService.insertClient(name, address, email, ordersID);
    }
        @PutMapping("/update")
        @ResponseBody
        public ClientDTO updateClient(@RequestParam(name = "id") String id,
                @RequestParam(name = "name") String name,
                @RequestParam(name = "address") String address,
                @RequestParam(name = "email") String email,
                @RequestParam(name = "ordersId") List<String> ordersID){
            return clientService.updateClient(id,name,address,email,ordersID);
        }
        @DeleteMapping("/delete")
        @ResponseBody
        public String deleteClient(@RequestParam(name = "id") String id){
        return clientService.deleteClient(id);
        }

}

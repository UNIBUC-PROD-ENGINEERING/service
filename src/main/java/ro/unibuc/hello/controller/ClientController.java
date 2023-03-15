package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.dto.ClientDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ClientService;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clienti")
    @ResponseBody
    public List<ClientDTO> getAllClients() {
        return clientService.getAll();
    }

    @PostMapping(value = "/clienti")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientEntity client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/clienti/{id}")
    @ResponseBody
    public ResponseEntity<ClientEntity> getClientById(@PathVariable(value = "id") String id) throws EntityNotFoundException {
        return clientService.getClientById(id);
    }

    @PutMapping("/clienti/{id}")
    @ResponseBody
    public ResponseEntity<ClientEntity> updateClientById(@PathVariable(value = "id") String id, @RequestBody ClientEntity updatedClient) throws EntityNotFoundException {
        return clientService.updateClientById(id, updatedClient);
    }

    @DeleteMapping("/clienti/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteClientById(@PathVariable(value = "id") String id) throws EntityNotFoundException {
        return clientService.deleteClientById(id);
    }
}

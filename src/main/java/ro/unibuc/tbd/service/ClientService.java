package ro.unibuc.tbd.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository repository;

    @Autowired
    ClientService(ClientRepository clientRepository) {
        this.repository = clientRepository;
    }

    public Client getClientById(String clientId) {
        Optional<Client> client = repository.findById(clientId);
        if (client.isPresent()) {
            return client.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client createClient(Client client) {
        return repository.save(client);
    }

    public Client updateClient(String clientId, Client request) {
        Optional<Client> optionalClient = repository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
        }

        Client client = optionalClient.get();
        client.updateClient(request);

        return repository.save(client);
    }

    public void deleteClientById(String clientId) {
        repository.deleteById(clientId);
    }
}

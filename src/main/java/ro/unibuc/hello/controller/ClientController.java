package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import ro.unibuc.hello.data.ClientRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.ClientEntity;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ClientEntity createClient(@RequestBody ClientEntity client) {
        return clientRepository.save(client);
    }

    @GetMapping
    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ClientEntity getClientById(@PathVariable String id) {
        return clientRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable String id) {
        clientRepository.deleteById(id);
    }

    @PostMapping("/{clientId}/books/{bookId}")
    public ClientEntity addBookToClient(@PathVariable String clientId, @PathVariable String bookId) {
        ClientEntity client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found with id " + clientId));
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id " + bookId));

        if (client.getBooks() == null) {
            client.setBooks(new ArrayList<>());
        }
        client.getBooks().add(book);
        return clientRepository.save(client);
    }
}
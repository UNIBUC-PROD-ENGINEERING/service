package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ro.unibuc.hello.data.ClientRepository;
import ro.unibuc.hello.data.LoanEntity;
import ro.unibuc.hello.data.LoanRepository;
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

    @Autowired
    private LoanRepository loanRepository;

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
        ClientEntity currClient = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
        List <LoanEntity> loanList = loanRepository.findByClient(currClient);
        if (loanList.size() > 0){
            throw new RuntimeException("Client has loans and cannot be deleted.");
        }
        clientRepository.deleteById(id);
    }

    @PostMapping("/{clientId}/books/{bookId}")
    public LoanEntity addBookToClientAndCreateLoan(@PathVariable String clientId, @PathVariable String bookId) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + clientId));
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + bookId));

        if (client.getBooks() == null) {
            client.setBooks(new ArrayList<>());
        }
        client.getBooks().add(book);

        // Create and save the loan
        LoanEntity loan = new LoanEntity();
        loan.setClient(client);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusWeeks(2));
        loanRepository.save(loan);

        return loanRepository.save(loan);
        
    }

    @PutMapping("/{id}")
    public void editclient(@PathVariable String id, ClientEntity clientEntity){
        Optional<ClientEntity> optionalClient = clientRepository.findById(id);

        if(optionalClient.isPresent()){
            ClientEntity client = optionalClient.get();
            client.setFullName(clientEntity.getFullName());
            client.setFavouriteBook(clientEntity.getFavouriteBook());
            client.setBooks(clientEntity.getBooks());
            clientRepository.save(client);
        }
        
    }
}
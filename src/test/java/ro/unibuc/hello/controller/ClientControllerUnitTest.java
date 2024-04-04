package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.LoanEntity;

import java.util.List;

class ClientControllerUnitTest {
    ClientController clientController = new ClientController();
    BookController bookController = new BookController();
    @Test
    void createClient() {
        //Arrange
        ClientEntity client = new ClientEntity("Alin Barosanu", "2 ani de vacanta");

        //Act
        ClientEntity savedClient = clientController.createClient(client);

        //Assert
        Assertions.assertNotNull(savedClient);
        Assertions.assertTrue(Integer.parseInt(savedClient.getId()) > 0);
    }

    @Test
    void getClientById() {
        //Arrange
        List<ClientEntity> clientList = clientController.getAllClients();
        ClientEntity client = clientList.get(0);

        //Act
        ClientEntity readClient = clientController.getClientById(client.getId());

        //Assert
        Assertions.assertNotNull(readClient);
    }

    @Test
    void deleteClientSuccessfully() {
        // Arrange
        ClientEntity client = new ClientEntity("Gigel Frone", "Siddhartha");
        ClientEntity savedClient = clientController.createClient(client);

        // Act
        clientController.deleteClient(savedClient.getId());

        // Assert
        Assertions.assertThrows(InvalidConfigurationPropertyValueException.class, () -> {
            clientController.getClientById(savedClient.getId());
        });
    }

    @Test
    void addBookToClientAndCreateLoanSuccessfully() {
        //Arrange
        List<ClientEntity> clientList = clientController.getAllClients();
        ClientEntity client = clientList.get(0);
        List <BookEntity> bookList = bookController.getAllBooks();
        BookEntity book = bookList.get(0);

        //Act
        LoanEntity newLoan = clientController.addBookToClientAndCreateLoan(client.getId(), book.getId());
        Assertions.assertNotNull(newLoan);
        Assertions.assertTrue(Integer.parseInt(newLoan.getId()) > 0);
    }

    @Test
    void editClientSuccessfully() {
        // Arrange
        // Create a new book
        ClientEntity client = new ClientEntity("Roberto Grasu", "Mein Kampf");
        ClientEntity savedClient = clientController.createClient(client);

        // Update details for the book
        String updatedName = "Alberto Grasu";
        String updatedFavBook = "The Communist Manifesto";
        savedClient.setFullName(updatedName);
        savedClient.setFavouriteBook(updatedFavBook);

        // Act
        // Call the updateBook method to edit the book details
        clientController.editclient(savedClient.getId(), savedClient);
        ClientEntity editedClient = clientController.getClientById(savedClient.getId());

        // Assert
        // Check if the book details have been updated correctly
        Assertions.assertNotNull(editedClient);
        Assertions.assertEquals(savedClient.getId(), editedClient.getId());
        Assertions.assertEquals(updatedName, editedClient.getFullName());
        Assertions.assertEquals(updatedFavBook, editedClient.getFavouriteBook());
    }
}
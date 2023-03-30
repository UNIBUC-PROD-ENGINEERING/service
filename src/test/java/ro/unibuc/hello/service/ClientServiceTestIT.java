package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.*;


import java.util.Arrays;
import java.util.List;
@SpringBootTest
@Tag("IT")
public class ClientServiceTestIT {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    ClientService clientService;

    private List<OrderEntity> orders;

    @Test
    public void testGetClients(){
        clientRepository.deleteAll();
        List<ClientDTO> clients = clientService.getClients();
        Assertions.assertNotNull(clients);
        Assertions.assertEquals(0, clients.size());
    }

    @Test
    public void testInsertClient() {
        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        ClientDTO cLientDTO = clientService.insertClient("Giani", "giani@gmail.com", "Republica 23", orderIds);
        ClientDTO clientInsertedDTO = clientService.getClient(cLientDTO.getId());

        Assertions.assertNotNull(cLientDTO);
        Assertions.assertEquals("Giani", cLientDTO.getName());
        Assertions.assertEquals("giani@gmail.com", cLientDTO.getEmail());
        Assertions.assertEquals("Republica 23", cLientDTO.getAddress());

        Assertions.assertEquals("Giani", clientInsertedDTO.getName());
        Assertions.assertEquals("giani@gmail.com", clientInsertedDTO.getEmail());
        Assertions.assertEquals("Republica 23", clientInsertedDTO.getAddress());
    }

    @Test
    public void  testUpdateCLient(){
        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        ClientDTO  clientDTO =  clientService.insertClient("Florin", "florin@gmail.com", "Republica 24", orderIds);

        this.orders = Arrays.asList(orderRepository.save(new OrderEntity()), orderRepository.save(new OrderEntity()));
        orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        ClientDTO updatedClientDTO = clientService.updateClient(clientDTO.getId().toString(), clientDTO.getName().toString(),clientDTO.getEmail().toString(), clientDTO.getAddress().toString(), orderIds);

        ClientDTO clientUPdatedDTO = clientService.getClient(updatedClientDTO.getId());

        Assertions.assertEquals("Florin", updatedClientDTO.getName());
        Assertions.assertEquals("florin@gmail.com", updatedClientDTO.getEmail());
        Assertions.assertEquals("Republica 24", updatedClientDTO.getAddress());

        Assertions.assertEquals("Florin", clientUPdatedDTO.getName());
        Assertions.assertEquals("florin@gmail.com", clientUPdatedDTO.getEmail());
        Assertions.assertEquals("Republica 24", clientUPdatedDTO.getAddress());
    }

    @Test()
    public void testDeleteClient(){
        clientRepository.deleteAll();

        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        ClientDTO clientDTO = clientService.insertClient("Florin", "florin@gmail.com", "Republica 24", orderIds);

        String deleteMessage = clientService.deleteClient(clientDTO.getId().toString());

        Assertions.assertEquals("Client with id:" + clientDTO.getId().toString() + "was deleted", deleteMessage);

        Assertions.assertNull(clientService.getClient(clientDTO.getId()));
    }

    public void createObjects(){
       OrderEntity order1 = new OrderEntity();
       OrderEntity order2 = new OrderEntity();

       this.orders =  Arrays.asList(orderRepository.save(order1), orderRepository.save(order2));
    }
}

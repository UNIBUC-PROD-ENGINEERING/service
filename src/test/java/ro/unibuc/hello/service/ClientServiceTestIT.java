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
        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        ClientDTO cLientDTO = clientService.insertClient("Giani", "giani@gmail.com", "Republica 23", orderIds);
        ClientDTO clientInsertedDTO = clientService.getClient(cLientDTO.getId());
        Assertions.assertEquals(orders.size(), cLientDTO.getOrders().size());
        Assertions.assertEquals(orders.get(0).getId(), cLientDTO.getOrders().get(0).getId());
        Assertions.assertEquals(orders.get(1).getId(), cLientDTO.getOrders().get(1).getId());

        Assertions.assertEquals(orders.size(), clientInsertedDTO.getOrders().size());
        Assertions.assertEquals(orders.get(0).getId(), clientInsertedDTO.getOrders().get(0).getId());
        Assertions.assertEquals(orders.get(1).getId(), clientInsertedDTO.getOrders().get(1).getId());


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

        Assertions.assertEquals(orders.size(), updatedClientDTO.getOrders().size());
        Assertions.assertEquals(orders.get(0).getId(), updatedClientDTO.getOrders().get(0).getId());
        Assertions.assertEquals(orders.get(1).getId(), updatedClientDTO.getOrders().get(1).getId());

        Assertions.assertEquals(orders.size(), clientUPdatedDTO .getOrders().size());
        Assertions.assertEquals(orders.get(0).getId(), clientUPdatedDTO .getOrders().get(0).getId());
        Assertions.assertEquals(orders.get(1).getId(), clientUPdatedDTO .getOrders().get(1).getId());


    }

    @Test()
    public void testDeleteClient(){
        clientRepository.deleteAll();

        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        ClientDTO clientDTO = clientService.insertClient("Florin", "florin@gmail.com", "Republica 24", orderIds);

        String deleteMessage = clientService.deleteClient(clientDTO.getId().toString());

        Assertions.assertEquals("CLientwith id:" + clientDTO.getId().toString() + "was deleted", deleteMessage);

        Assertions.assertNull(clientService.getClient(clientDTO.getId()));




    }

    public void createObjects(){
       OrderEntity order1 = new OrderEntity();
       OrderEntity order2 = new OrderEntity();

       this.orders =  Arrays.asList(orderRepository.save(order1), orderRepository.save(order2));

    }
}

package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.*;


import ro.unibuc.hello.dto.ClientDTO;
import ro.unibuc.hello.dto.MenuDTO;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<ClientDTO> getClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }


    public ClientDTO getClient(String id) {
        ClientEntity client = clientRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if (client != null) {
            return new ClientDTO(client);
        } else {
            return null;
        }
    }


    public ClientDTO insertClient(String name, String email, String address, List<String> orderIds) {
        ClientEntity client = new ClientEntity(name, email, address);
        ArrayList<OrderEntity> orderEntities = new ArrayList<>();
        if(orderIds != null && !orderIds.isEmpty())
            orderIds.forEach(id -> orderEntities.add(orderRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            client.setOrders(null);
        if(!orderEntities.isEmpty() && orderEntities !=null)
            client.setOrders(orderEntities);
        return new ClientDTO(client);
    }

    public ClientDTO updateClient(String clientId,String name, String email, String address, List<String> orderIds){
        ClientEntity client = clientRepository.findById(String.valueOf(new ObjectId(clientId))).orElse(null);
        if(client != null){
            if(name != null)
                client.setName(name);
            if(address != null)
                client.setAddress(address);
            if(email != null)
                client.setEmail(email);
            if(orderIds != null && !orderIds.isEmpty()){
                ArrayList<OrderEntity> orderEntities = new ArrayList<>();
                orderIds.forEach(id -> orderEntities.add(orderRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
                if(!orderEntities.isEmpty() && orderEntities !=null)
                    client.setOrders(orderEntities);
            }
            else
                client.setOrders(null);
        return new ClientDTO(client);
        }
        else
            return null;
    }

    public  String deleteClient(String id){
        clientRepository.deleteById(String.valueOf(new ObjectId(id)));
        return "Client with id:" + id + "was deleted";
    }
}
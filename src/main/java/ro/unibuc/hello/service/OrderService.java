package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepository;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.ProdusRepository;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.dto.ProdusDTO;
import ro.unibuc.hello.dto.UserDTO;

@Component
public class OrderService {
    @Autowired
    OrderRepository repo;
    @Autowired
    UserRepository repoUser;
    @Autowired
    ProdusRepository repoProduse;

    public OrderEntity toEntity(OrderDTO orderDTO) {
        OrderEntity order = new OrderEntity(orderDTO.getId(),
                repoUser.findById(orderDTO.getUserID()).get(), StreamSupport.stream(repoProduse.findAllById(orderDTO.getListaProduse()).spliterator(),false).collect(Collectors.toList()));
        return order;
    }

    public OrderDTO entityToDTO(OrderEntity order) {
        return new OrderDTO(order.getId(), order.getUser().getId(), order.getListaProduse().stream().map(produs -> produs.getId()).collect(Collectors.toList()));
    }

    public OrderDTO getOrder(String id) {
        return entityToDTO(repo.findById(id).get());
    }

    public void createOrder(OrderDTO order) {
        repo.save(toEntity(order));
    }

    public List<OrderDTO> getAll() {
        return repo.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public boolean updateOrder(OrderDTO orderDTO) {
        OrderEntity found = repo.findById(orderDTO.getId()).orElse(null);
        if (found != null) {
            found.setListaProduse(
                    StreamSupport.stream(repoProduse.findAllById(orderDTO.getListaProduse()).spliterator(), false).collect(Collectors.toList()));
            found.setUser(repoUser.findById(orderDTO.getUserID()).get());
            repo.save(found);
            return true;
        }
        return false;
    }

    public boolean deleteOrder(String id) {
        OrderEntity found = repo.findById(id).orElse(null);
        if (found != null) {
            repo.delete(found);
            return true;
        }
        return false;
    }

    public Produs toEntity(ProdusDTO produsDTO) {
        Produs produs = new Produs(produsDTO.getId(), produsDTO.getNume(), produsDTO.getPret());
        return produs;
    }

}

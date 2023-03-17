package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/get-all")
    @ResponseBody
    public List<OrderDTO> getClients() {
        return orderService.getOrders();
    }

    @GetMapping("/get")
    @ResponseBody
    public OrderDTO getOrder(@RequestParam(name="id") String id)
    {
        return orderService.getOrder(id);
    }


    @PostMapping("/insert")
    @ResponseBody
    public OrderDTO insertOrder(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "restaurantId") String restaurantID,
                                  @RequestParam(name = "ordersId") List<String> dishesID) {
        return orderService.insertOrder(id, restaurantID, dishesID);
    }
    @PutMapping("/update")
    @ResponseBody
    public OrderDTO updateClient(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "restaurantID") String restaurnatID,
                                  @RequestParam(name = "client") String clientId,
                                  @RequestParam(name = "dishesId") List<String> dishesID){
        return orderService.updateOrder(id,restaurnatID,clientId,dishesID);
    }
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteOrder(@RequestParam(name = "id") String id){
        return orderService.deleteOrder(id);
    }

}

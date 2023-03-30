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
    public OrderDTO insertOrder(@RequestParam(name = "restaurantId") String restaurantId,
                                @RequestParam(name = "clientId") String clientId,
                                @RequestParam(name = "dishesIds") List<String> dishesIds) {
        return orderService.insertOrder(restaurantId, clientId, dishesIds);
    }

    @PutMapping("/update")
    @ResponseBody
    public OrderDTO updateClient(@RequestParam(name = "orderId") String orderId,
                                  @RequestParam(name = "restaurantId") String restaurantId,
                                  @RequestParam(name = "clientId") String clientId,
                                  @RequestParam(name = "dishesId") List<String> dishesIds){
        return orderService.updateOrder(orderId, restaurantId, clientId, dishesIds);
    }
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteOrder(@RequestParam(name = "orderId") String id){
        return orderService.deleteOrder(id);
    }

}

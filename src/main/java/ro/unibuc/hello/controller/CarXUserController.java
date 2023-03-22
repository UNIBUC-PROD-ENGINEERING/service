package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.BuyCarDTO;
import ro.unibuc.hello.dto.GetUserCarsDTO;
import ro.unibuc.hello.service.CarXUserService;

import java.util.List;

@RestController
public class CarXUserController {
    @Autowired
    private CarXUserService _service;

    @GetMapping("/user/getCars")
    @ResponseBody
    public List<GetUserCarsDTO> GetCarsByUser(@RequestParam(name="userId") String userId){
        return _service.GetCarsByUser(userId);
    }

    @PostMapping("/user/buyCar")
    @ResponseBody
    public String BuyCar(@RequestBody BuyCarDTO model){
       return _service.buyCar(model);
    }
}

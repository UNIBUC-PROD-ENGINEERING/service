package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.CustomerService;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getCustomerByName")
    @ResponseBody
    public Customer getCustomerById(@RequestParam(name="name", required = true) String Name) throws EntityNotFoundException {
        return customerService.getCustomerByName(Name);
    }
}

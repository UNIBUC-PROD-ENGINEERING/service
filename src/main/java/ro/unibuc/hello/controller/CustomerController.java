package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Medicament;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.CustomerService;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    @ResponseBody
    public String getCustomers(@RequestParam(name="title", required=false, defaultValue="Customers") String title)  throws EntityNotFoundException {
        return customerService.getCustomers(title);
    }

    @GetMapping("/customer/{customer_id}")
    @ResponseBody
    public String getCustomer(@PathVariable("customer_id")long customer_id, @RequestParam(name="title", required=false, defaultValue="Customers") String title)  throws EntityNotFoundException {
        return customerService.getCustomer(title,customer_id).toString();
    }

    @PostMapping(value = "/addcustomer", produces = "application/json")
    @ResponseBody
    public String addCustomers(@RequestBody Customer c) throws EntityNotFoundException {
        return customerService.addCustomers("Customers",c.getName(),c.getAge(), c.getEmail());
    }

    @DeleteMapping("/delcustomer/{id}")
    @ResponseBody
    public String delCustomer(@PathVariable("customer_id")long customer_id, @RequestParam(name="title", required=false, defaultValue="Customers") String title)  throws EntityNotFoundException {
        customerService.delCustomer(customer_id,title);
        return "Deleted";
    }
}

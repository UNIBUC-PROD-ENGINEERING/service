package ro.unibuc.hello.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.service.CustomerService;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_getCustomers() throws Exception{
        Customer customer = new Customer("John Doe", 25, "jdoe@gmail.com");
        when(customerService.getCustomers(any())).thenReturn(customer.toString());

        MvcResult result = mockMvc.perform(get("/customers")
                .content(objectMapper.writeValueAsString(customer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(customer));

    }

    @Test
    void test_getCustomer() throws Exception{
        Customer customer = new Customer("John Doe", 25, "jdoe@gmail.com");
        when(customerService.getCustomer(anyString(),anyLong())).thenReturn(customer);
        MvcResult result = mockMvc.perform(get(String.format("/customer/%s", customer.getCustomer_id()))
                .content(objectMapper.writeValueAsString(customer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), customer.toString());
    }


}

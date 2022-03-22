package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.hello.dto.Greeting;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HelloWorldControllerTest {

//    private int port = 8080;
//    @Mock
//    HelloWorldController controller;

    @Test
    void sayHello() {
        HelloWorldController controller = new HelloWorldController();
        Greeting responseGreeting = controller.sayHello("Florian");
        assertEquals("Hello, Florian!", responseGreeting.getContent());
    }

    @Test
    void listAll() {
        HelloWorldController controller = new HelloWorldController();
        Greeting responseGreeting = controller.listAll("Overview");
        assertEquals("Overview : This is an example of using a data storage engine running separately from our applications server", responseGreeting.getContent());
    }
}
package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;

@SpringBootTest
class HelloWorldServiceTestIT {

    @Autowired
    InformationRepository informationRepository;

    @Autowired
    HelloWorldService helloWorldService;

    @Test
    void test_buildGreetingFromInfo_returnsGreetingWithInformation() {
        // Arrange
        String title = "Overview";

        // Act
        Greeting greeting = helloWorldService.buildGreetingFromInfo(title);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Overview : This is an example of using a data storage engine running separately from our applications server!", greeting.getContent());
    }
}
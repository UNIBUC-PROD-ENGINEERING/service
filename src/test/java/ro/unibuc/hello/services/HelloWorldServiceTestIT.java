package ro.unibuc.hello.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.repositories.InformationRepository;
import ro.unibuc.hello.dtos.GreetingDTO;

@SpringBootTest
@Tag("IT")
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
        GreetingDTO greetingDTO = helloWorldService.buildGreetingFromInfo(title);

        // Assert
        Assertions.assertEquals(1, greetingDTO.getId());
        Assertions.assertEquals("Overview : This is an example of using a data storage engine running separately from our applications server!", greetingDTO.getContent());
    }
}
package ro.unibuc.slots.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.slots.dto.Greeting;
import ro.unibuc.slots.repo.InformationRepository;

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
        final String title = "Overview";

        // Act
        final Greeting greeting = helloWorldService.buildGreetingFromInfo(title);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Overview : This is an example of using a data storage engine running separately from our applications server!", greeting.getContent());
    }
}

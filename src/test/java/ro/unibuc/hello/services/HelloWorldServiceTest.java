package ro.unibuc.hello.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.entities.InformationEntity;
import ro.unibuc.hello.repositories.InformationRepository;
import ro.unibuc.hello.dtos.GreetingDTO;
import ro.unibuc.hello.exceptions.EntityNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HelloWorldServiceTest {

    @Mock
    InformationRepository mockInformationRepository;

    @InjectMocks
    HelloWorldService helloWorldService = new HelloWorldService();

    @Test
    void test_hello_returnsGreeting(){
        // Arrange
        String name = "John";

        // Act
        GreetingDTO greetingDTO = helloWorldService.hello(name);

        // Assert
        Assertions.assertEquals(1, greetingDTO.getId());
        Assertions.assertEquals("Hello, John!", greetingDTO.getContent());
    }

    @Test
    void test_hello_returnsGreeting_whenNameNull(){
        // Arrange

        // Act
        GreetingDTO greetingDTO = helloWorldService.hello(null);

        // Assert
        Assertions.assertEquals(1, greetingDTO.getId());
        Assertions.assertEquals("Hello, null!", greetingDTO.getContent());
    }

    @Test
    void test_buildGreetingFromInfo_returnsGreetingWithInformation() {
        // Arrange
        String title = "someTitle";
        InformationEntity informationEntity = new InformationEntity(title, "someDescription");

        when(mockInformationRepository.findByTitle(title)).thenReturn(informationEntity);

        // Act
        GreetingDTO greetingDTO = helloWorldService.buildGreetingFromInfo(title);

        // Assert
        Assertions.assertEquals(1, greetingDTO.getId());
        Assertions.assertEquals("someTitle : someDescription!", greetingDTO.getContent());
    }

    @Test
    void test_buildGreetingFromInfo_throwsEntityNotFoundException_whenInformationNull() {
        // Arrange
        String title = "someTitle";

        when(mockInformationRepository.findByTitle(title)).thenReturn(null);

        try {
            // Act
            GreetingDTO greetingDTO = helloWorldService.buildGreetingFromInfo(title);
        } catch (Exception ex) {
            // Assert
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Entity: someTitle was not found");
        }
    }

}
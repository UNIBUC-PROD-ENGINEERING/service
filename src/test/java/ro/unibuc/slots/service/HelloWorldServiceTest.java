package ro.unibuc.slots.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.slots.data.InformationEntity;
import ro.unibuc.slots.data.InformationRepository;
import ro.unibuc.slots.dto.Greeting;
import ro.unibuc.slots.exception.EntityNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HelloWorldServiceTest {
    @Mock
    InformationRepository mockInformationRepository;

    @InjectMocks
    HelloWorldService helloWorldService;

    @Before
    public void init() {
        try (final var mocks = MockitoAnnotations.openMocks(this)) {
            System.out.println(mocks);
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_hello_returnsGreeting() {
        // Arrange
        final String name = "John";

        // Act
        final Greeting greeting = helloWorldService.hello(name);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Hello, John!", greeting.getContent());
    }

    @Test
    void test_hello_returnsGreeting_whenNameNull() {
        // Arrange

        // Act
        final Greeting greeting = helloWorldService.hello(null);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("Hello, null!", greeting.getContent());
    }

    @Test
    void test_buildGreetingFromInfo_returnsGreetingWithInformation() {
        // Arrange
        final String title = "someTitle";
        final InformationEntity informationEntity = new InformationEntity(title, "someDescription");

        when(mockInformationRepository.findByTitle(title)).thenReturn(informationEntity);

        // Act
        final Greeting greeting = helloWorldService.buildGreetingFromInfo(title);

        // Assert
        Assertions.assertEquals(1, greeting.getId());
        Assertions.assertEquals("someTitle : someDescription!", greeting.getContent());
    }

    @Test
    void test_buildGreetingFromInfo_throwsEntityNotFoundException_whenInformationNull() {
        // Arrange
        final String title = "someTitle";

        when(mockInformationRepository.findByTitle(title)).thenReturn(null);

        try {
            // Act
            helloWorldService.buildGreetingFromInfo(title);
        } catch (final Exception ex) {
            // Assert
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Entity: someTitle was not found");
        }
    }
}

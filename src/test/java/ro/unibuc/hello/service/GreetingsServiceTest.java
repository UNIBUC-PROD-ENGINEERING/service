package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GreetingsServiceTest {

    @Mock
    private InformationRepository informationRepository;

    @InjectMocks
    private GreetingsService greetingsService = new GreetingsService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHello() {
        // Arrange
        String name = "John";

        // Act
        Greeting greeting = greetingsService.hello(name);

        // Assert
        assertNotNull(greeting);
        assertEquals("Hello, John!", greeting.getContent());
    }

    @Test
    void testBuildGreetingFromInfo_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String title = "Title";
        String description = "Description";
        InformationEntity entity = new InformationEntity(title, description);
        when(informationRepository.findByTitle(title)).thenReturn(entity);

        // Act
        Greeting greeting = greetingsService.buildGreetingFromInfo(title);

        // Assert
        assertNotNull(greeting);
        assertEquals("Title : Description!", greeting.getContent());
    }

    @Test
    void testBuildGreetingFromInfo_NonExistingEntity() {
        // Arrange
        String title = "NonExistingTitle";
        when(informationRepository.findByTitle(title)).thenReturn(null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> greetingsService.buildGreetingFromInfo(title));
    }

    @Test
    void testSaveGreeting() {
        // Arrange
        Greeting greeting = new Greeting("1", "Hello");

        // Act
        when(informationRepository.save(any(InformationEntity.class))).thenReturn(new InformationEntity("1", "Hello", null));
        Greeting savedGreeting = greetingsService.saveGreeting(greeting);

        // Assert
        assertNotNull(savedGreeting);
        assertEquals("1", savedGreeting.getId());
        assertEquals("Hello", savedGreeting.getContent());
    }

    @Test
    void testUpdateGreeting_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        Greeting greeting = new Greeting(id, "Updated Greeting");
        InformationEntity entity = new InformationEntity(id, "Old Greeting", "Description");
        when(informationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(informationRepository.save(any(InformationEntity.class))).thenReturn(new InformationEntity(id, "Updated Greeting", "Description"));

        // Act
        Greeting updatedGreeting = greetingsService.updateGreeting(id, greeting);

        // Assert
        assertNotNull(updatedGreeting);
        assertEquals(id, updatedGreeting.getId());
        assertEquals("Updated Greeting", updatedGreeting.getContent());
    }

    @Test
    void testUpdateGreeting_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        Greeting greeting = new Greeting(id, "Updated Greeting");
        when(informationRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> greetingsService.updateGreeting(id, greeting));
    }

    @Test
    void testDeleteGreeting_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        InformationEntity entity = new InformationEntity(id, "Greeting to delete", "Description");
        when(informationRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        greetingsService.deleteGreeting(id);

        // Assert
        verify(informationRepository, times(1)).delete(entity);
    }

    @Test
    void testDeleteGreeting_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        when(informationRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> greetingsService.deleteGreeting(id));
    }

    @Test
    void testGetAllGreetings() {
        // Arrange
        List<InformationEntity> entities = Arrays.asList(
                new InformationEntity("1", "Greeting 1", "Description 1"),
                new InformationEntity("2", "Greeting 2", "Description 2")
        );
        when(informationRepository.findAll()).thenReturn(entities);

        // Act
        List<Greeting> greetings = greetingsService.getAllGreetings();

        // Assert
        assertEquals(2, greetings.size());
        assertEquals("1", greetings.get(0).getId());
        assertEquals("Greeting 1", greetings.get(0).getContent());
        assertEquals("2", greetings.get(1).getId());
        assertEquals("Greeting 2", greetings.get(1).getContent());
    }
}

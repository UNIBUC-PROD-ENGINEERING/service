package ro.unibuc.hello.dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.ClientEntity;

public class ClientDTOTest {

    @Test
    public void testConstructor() {
        ClientEntity entity = Mockito.mock(ClientEntity.class);
        Mockito.when(entity.getId()).thenReturn("1");
        Mockito.when(entity.getName()).thenReturn("Dumitru Ion");
        Mockito.when(entity.getEmail()).thenReturn("dummy.ion@gmail.com");
        Mockito.when(entity.getAddress()).thenReturn("Aleea Plopilor 22");

        ClientDTO dto = new ClientDTO(entity);

        Assertions.assertEquals(dto.getId(), "1");
        Assertions.assertEquals(dto.getName(), "Dumitru Ion");
        Assertions.assertEquals(dto.getEmail(), "dummy.ion@gmail.com");
        Assertions.assertEquals(dto.getAddress(), "Aleea Plopilor 22");
    }

    @Test
    public void testGettersAndSetters() {
        ClientDTO dto = new ClientDTO();

        dto.setId("1");
        dto.setName("Dumitru Ion");
        dto.setEmail("dummy.ion@gmail.com");
        dto.setAddress("Aleea Plopilor 22");

        Assertions.assertEquals(dto.getId(), "1");
        Assertions.assertEquals(dto.getName(), "Dumitru Ion");
        Assertions.assertEquals(dto.getEmail(), "dummy.ion@gmail.com");
        Assertions.assertEquals(dto.getAddress(), "Aleea Plopilor 22");
    }

    @Test
    public void testToString() {
        ClientDTO dto = new ClientDTO();
        dto.setName("Dumitru Ion");
        dto.setEmail("dummy.ion@gmail.com");
        dto.setAddress("Aleea Plopilor 22");

        String expected = "Client[name='Dumitru Ion', email='dummy.ion@gmail.com', address='Aleea Plopilor 22']";
        Assertions.assertEquals(dto.toString(), expected);
    }
}
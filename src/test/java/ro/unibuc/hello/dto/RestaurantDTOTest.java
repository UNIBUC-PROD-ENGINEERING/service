package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.RestaurantEntity;


public class RestaurantDTOTest{

    @Test
    void constructor_withEntity_shouldSetFields() {
        // Arrange
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId("123");
        entity.setName("Restaurant Name");
        entity.setEmail("restaurant@example.com");
        entity.setAddress("123 Main St.");

        // Act
        RestaurantDTO dto = new RestaurantDTO(entity);

        // Assert
        Assertions.assertEquals("123", dto.getId());
        Assertions.assertEquals("Restaurant Name", dto.getName());
        Assertions.assertEquals("restaurant@example.com", dto.getEmail());
        Assertions.assertEquals("123 Main St.", dto.getAddress());
    }

    @Test
    void constructor_withNoArgs_shouldCreateEmptyDTO() {
        // Arrange & Act
        RestaurantDTO dto = new RestaurantDTO();

        // Assert
        Assertions.assertNull(dto.getId());
        Assertions.assertNull(dto.getName());
        Assertions.assertNull(dto.getEmail());
        Assertions.assertNull(dto.getAddress());
    }

    @Test
    void setters_and_getters_shouldWorkAsExpected() {
        // Arrange
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId("123");
        dto.setName("Restaurant Name");
        dto.setEmail("restaurant@example.com");
        dto.setAddress("123 Main St.");

        // Act & Assert
        Assertions.assertEquals("123", dto.getId());
        Assertions.assertEquals("Restaurant Name", dto.getName());
        Assertions.assertEquals("restaurant@example.com", dto.getEmail());
        Assertions.assertEquals("123 Main St.", dto.getAddress());
    }

    @Test
    void toString_shouldReturnExpectedValue() {
        // Arrange
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName("Restaurant Name");
        dto.setEmail("restaurant@example.com");
        dto.setAddress("123 Main St.");

        // Act
        String result = dto.toString();

        // Assert
        Assertions.assertEquals("Restaurant[name='Restaurant Name', email='restaurant@example.com', address='123 Main St.']", result);
    }
}

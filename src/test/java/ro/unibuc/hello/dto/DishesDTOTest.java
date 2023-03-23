package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.DishesEntity;

public class DishesDTOTest {

    @Test
    public void testDishesDTOConstructor() {
        // Arrange
        DishesEntity entity = new DishesEntity();
        entity.setId("1");
        entity.setName("Pizza");
        entity.setQuantity(500);
        entity.setPrice(10.5f);

        // Act
        DishesDTO dto = new DishesDTO(entity);

        // Assert
        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getName(), dto.getName());
        Assertions.assertEquals(entity.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(entity.getPrice(), dto.getPrice());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        DishesDTO dto = new DishesDTO(new DishesEntity());

        // Act
        dto.setId("2");
        dto.setName("Pasta");
        dto.setQuantity(300);
        dto.setPrice(8.0f);

        // Assert
        Assertions.assertEquals("2", dto.getId());
        Assertions.assertEquals("Pasta", dto.getName());
        Assertions.assertEquals(300, dto.getQuantity());
        Assertions.assertEquals(8.0f, dto.getPrice());
    }

    @Test
    public void testToString() {
        // Arrange
        DishesDTO dto = new DishesDTO(new DishesEntity());
        dto.setName("Burger");
        dto.setQuantity(200);
        dto.setPrice(5.5f);

        // Act
        String result = dto.toString();

        // Assert
        Assertions.assertEquals("Dish[name='Burger', quantity(grams)='200', price(EUR)='5.500000']", result);
    }
}

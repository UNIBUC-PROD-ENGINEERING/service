package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class OrderDTOTest {

    String id = "1";
    String userId = "123";
    List<String> productList = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
        }
    };

    @Test
    void testConstructor() {
        OrderDTO orderDto = new OrderDTO(id, userId, productList);

        assertEquals(id, orderDto.getId());
        assertEquals(userId, orderDto.getUserID());
        assertEquals(productList, orderDto.getListaProduse());
    }

    @Test
    void testSetters() {
        OrderDTO orderDto = new OrderDTO();

        assertEquals(null, orderDto.getId());
        assertEquals(null, orderDto.getUserID());
        assertEquals(null, orderDto.getListaProduse());

        orderDto.setId(id);
        orderDto.setUserID(userId);
        orderDto.setListaProduse(productList);

        assertEquals(id, orderDto.getId());
        assertEquals(userId, orderDto.getUserID());
        assertEquals(productList, orderDto.getListaProduse());
    }
}

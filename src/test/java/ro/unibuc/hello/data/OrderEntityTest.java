package ro.unibuc.hello.data;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class OrderEntityTest {
    UserEntity user = new UserEntity("10","adresa10@gmail.com", "parola10");
    Produs produs1 = new Produs("abc", "roata", "250.00");
    Produs produs2 = new Produs("efg", "scaun", "89.90");
    List <Produs> listaProduse = new ArrayList<Produs>() {
        {
            add(produs1);
            add(produs2);
        }
    };

    @Test
    void testConstructor() {
        OrderEntity OrderEntity = new OrderEntity("10", user, listaProduse);

        assertEquals("10", OrderEntity.getId());
        assertEquals(user, OrderEntity.getUser());
        assertEquals(listaProduse, OrderEntity.getListaProduse());
    }

    @Test
    void testSetters() {
        OrderEntity OrderEntity = new OrderEntity();

        assertEquals(null, OrderEntity.getId());
        assertEquals(null, OrderEntity.getUser());
        assertEquals(null, OrderEntity.getListaProduse());

        OrderEntity.setId("10");
        OrderEntity.setUser(user);
        OrderEntity.setListaProduse(listaProduse);

        assertEquals("10", OrderEntity.getId());
        assertEquals(user, OrderEntity.getUser());
        assertEquals(listaProduse, OrderEntity.getListaProduse());
    }
}
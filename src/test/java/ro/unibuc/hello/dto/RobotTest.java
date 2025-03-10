package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RobotDTOTest {

    RobotDTO robot = new RobotDTO("1", "idle", "order1", 5, "none");

    @Test
    void test_getId() {
        Assertions.assertEquals("1", robot.getId());
    }

    @Test
    void test_setId() {
        robot.setId("2");
        Assertions.assertEquals("2", robot.getId());
    }

    @Test
    void test_getStatus() {
        Assertions.assertEquals("idle", robot.getStatus());
    }

    @Test
    void test_setStatus() {
        robot.setStatus("active");
        Assertions.assertEquals("active", robot.getStatus());
    }

    @Test
    void test_getCurrentOrderId() {
        Assertions.assertEquals("order1", robot.getCurrentOrderId());
    }

    @Test
    void test_setCurrentOrderId() {
        robot.setCurrentOrderId("order2");
        Assertions.assertEquals("order2", robot.getCurrentOrderId());
    }

    @Test
    void test_getCompletedOrders() {
        Assertions.assertEquals(5, robot.getCompletedOrders());
    }

    @Test
    void test_setCompletedOrders() {
        robot.setCompletedOrders(10);
        Assertions.assertEquals(10, robot.getCompletedOrders());
    }

    @Test
    void test_getErrors() {
        Assertions.assertEquals("none", robot.getErrors());
    }

    @Test
    void test_setErrors() {
        robot.setErrors("error");
        Assertions.assertEquals("error", robot.getErrors());
    }
}
package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GetUserCarsDTOTest {
    GetUserCarsDTO userCars = new GetUserCarsDTO("641b7a7db0792b40cb8f3c96", "641b7a7db0792b40cb8f3c90", "Volvo",2022, 75000 );

    @Test
    public void test_userId(){
        Assertions.assertSame("641b7a7db0792b40cb8f3c96", userCars.getUserId());
    }

    @Test
    public void test_carId(){
        Assertions.assertSame("641b7a7db0792b40cb8f3c90", userCars.getCarId());
    }

    @Test
    public void test_carMaker(){
        Assertions.assertSame("Volvo", userCars.getCarMaker());
    }

    @Test
    public void test_carYear(){
        Assertions.assertEquals(2022, userCars.getCarYear());
    }

    @Test
    public void test_carPrice(){
        Assertions.assertEquals(75000, userCars.getCarPrice());
    }
}

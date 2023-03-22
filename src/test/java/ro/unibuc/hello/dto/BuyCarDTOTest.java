package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BuyCarDTOTest {
    BuyCarDTO buyCarDTO = new BuyCarDTO("641b7a7db0792b40cb8f3c96", "641b7a7db0792b40cb8f3c90");

    @Test
    public void test_userId(){
        Assertions.assertSame("641b7a7db0792b40cb8f3c96", buyCarDTO.getUserId());
    }

    @Test
    public void test_carId(){
        Assertions.assertSame("641b7a7db0792b40cb8f3c90", buyCarDTO.getCarId());
    }
}

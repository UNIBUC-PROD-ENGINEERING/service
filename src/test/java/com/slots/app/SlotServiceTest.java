package com.slots.app;

import com.slots.app.controllers.GameController;
import com.slots.app.service.SlotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SlotServiceTest {

    @Autowired
    private ApplicationContext applicationContext;
    
    
    @Test
    public void test_numberOfWinningCombinations(){
        SlotService slotService = applicationContext.getBean(SlotService.class);
        assertEquals(6, slotService.getWinningCombinations().size());
    }
}

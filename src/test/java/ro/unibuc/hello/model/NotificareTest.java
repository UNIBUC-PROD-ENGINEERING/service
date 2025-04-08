package ro.unibuc.hello.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.model.Notificare;

public class NotificareTest {
    
    Notificare notificare = new Notificare("1", "2", "3", "tip", true);

    @Test
    void test_notificareId(){
        Assertions.assertEquals("1", notificare.getNotificareId());
    }
    @Test
    void test_eventId(){
        Assertions.assertEquals("2", notificare.getEventId());
    }
    @Test
    void test_userId(){
        Assertions.assertEquals("3", notificare.getUserId());
    }
    @Test
    void test_tipVerificare(){
        Assertions.assertEquals("tip", notificare.getTipVerificare());
    }
    @Test
    void test_verificare(){
        Assertions.assertTrue(notificare.getVerificare());
    }

    Notificare notificare2 = new Notificare();

    @Test
    void test_notificareId2(){
        notificare2.setNotificareId("4");
        Assertions.assertEquals("4", notificare2.getNotificareId());
    }
    @Test
    void test_eventId2(){
        notificare2.setEventId("5");
        Assertions.assertEquals("5", notificare2.getEventId());
    }
    @Test
    void test_userId2(){
        notificare2.setUserId("6");
        Assertions.assertEquals("6", notificare2.getUserId());
    }
    @Test
    void test_tipVerificare2(){
        notificare2.setTipVerificare("tip2");
        Assertions.assertEquals("tip2", notificare2.getTipVerificare());
    }
    @Test
    void test_verificare2(){
        notificare2.setVerificare(false);
        Assertions.assertFalse(notificare2.getVerificare());
    }    

}
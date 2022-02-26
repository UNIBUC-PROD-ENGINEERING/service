package ro.unibuc.link;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.unibuc.link.data.UrlRepository;

@SpringBootTest
class LinkApplicationTests {

    @MockBean
    UrlRepository mockRepository2;

    @Test
    void contextLoads() {
    }

}

package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.ProdusRepository;
import ro.unibuc.hello.dto.ProdusDTO;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class ProdusServiceTest {

    @Mock
    ProdusRepository produsRepository;
    @InjectMocks
    ProdusService produsService = new ProdusService();


    @Test
    void test_buildProdusFromInfo_returnsProdus() {
        // Arrange
        String id = "3";
        Produs produs = new Produs(id, "lanterna", "20");
        when(produsRepository.findById(id)).thenReturn(java.util.Optional.of(produs));

        // Act
        ProdusDTO produsDTO = produsService.getProdus(id);

        // Assert
        Assertions.assertEquals(produsDTO.getId(), produs.getId());
    }

}

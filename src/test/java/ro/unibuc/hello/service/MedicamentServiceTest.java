package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.MedicamentRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Medicament;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)

public class MedicamentServiceTest {

    @Mock
    MedicamentRepository mockMedicamentRepository;

    @InjectMocks
    MedicamentService medicamentService = new MedicamentService();

    @Test
    void test_medicamente_not_in_repo(){
        // Arrange
        //String [] str = new String[]{"aa","bb"};
        String name = "Medicamente";

        //Medicament medicamentTest = new Medicament("Nurofen",str);
        // Act
        when(mockMedicamentRepository.findByName(name)).thenReturn(null);
        // Assert

        try {
//            // Act
            Medicament medicament = medicamentService.getMedicament(name,0);
        } catch (Exception ex) {
//            // Assert
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Entity: Medicamente was not found");
        }
    }



}

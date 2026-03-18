package ro.unibuc.prodeng.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.model.Masina;
import ro.unibuc.prodeng.model.MasinaStatus;
import ro.unibuc.prodeng.repository.MasinaRepository;
import ro.unibuc.prodeng.request.MasinaRequest;
import ro.unibuc.prodeng.response.MasinaResponse;

@ExtendWith(MockitoExtension.class)
public class MasinaServiceTest {

    @Mock
    private MasinaRepository masinaRepository;

    @InjectMocks
    private MasinaService masinaService;

    @Test
    void testCreateMasina() {
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", 2020, 10000.0, 100, "Benzina", 90, "a@b.ro");
        Masina saved = new Masina("1", "Dacia", "Logan", 2020, 10000.0, 100, "Benzina", 90, MasinaStatus.DISPONIBIL, "a@b.ro");
        
        when(masinaRepository.save(any(Masina.class))).thenReturn(saved);

        MasinaResponse res = masinaService.createMasina(req);

        assertNotNull(res);
        assertEquals("1", res.getId());
        assertEquals("Dacia", res.getMarca());
        assertEquals(10000.0, res.getPret());
        
        verify(masinaRepository, times(1)).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaNegativePrice() {
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", 2020, -100, 100, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("Pretul trebuie sa fie strict mai mare decat 0", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testAplicaDiscount() throws EntityNotFoundException {
        Masina masina = new Masina("1", "VW", "Golf", 2019, 20000.0, 100, "Benzina", 90, MasinaStatus.DISPONIBIL, "a@b.ro");
        
        when(masinaRepository.findById("1")).thenReturn(Optional.of(masina));
        when(masinaRepository.save(any(Masina.class))).thenAnswer(i -> i.getArguments()[0]);

        MasinaResponse res = masinaService.aplicaDiscount("1");

        assertEquals(18000.0, res.getPret()); // 10% discount from 20000
    }

    @Test
    void testDeleteMasina() throws EntityNotFoundException {
        Masina masina = new Masina("1", "VW", "Golf", 2019, 20000.0, 100, "Benzina", 90, MasinaStatus.DISPONIBIL, "a@b.ro");
        when(masinaRepository.findById("1")).thenReturn(Optional.of(masina));
        
        masinaService.deleteMasina("1");
        
        verify(masinaRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteMasinaNotFound() {
        when(masinaRepository.findById("2")).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> {
            masinaService.deleteMasina("2");
        });
    }

    @Test
    void testCreateMasinaZeroPrice() {
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", 2020, 0, 100, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("Pretul trebuie sa fie strict mai mare decat 0", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaInvalidYearTooOld() {
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", 1899, 10000.0, 100, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("Anul fabricatiei este invalid", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaInvalidYearFuture() {
        int futureYear = java.time.Year.now().getValue() + 2;
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", futureYear, 10000.0, 100, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("Anul fabricatiei este invalid", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaBlankMarca() {
        MasinaRequest req = new MasinaRequest("   ", "Logan", 2020, 10000.0, 100, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("Marca nu poate fi goala", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaBlankModel() {
        MasinaRequest req = new MasinaRequest("Dacia", "", 2020, 10000.0, 100, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("Modelul nu poate fi gol", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaDuplicate() {
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", 2020, 10000.0, 100, "Benzina", 90, "a@b.ro");
        when(masinaRepository.existsByMarcaAndModelAndAn("Dacia", "Logan", 2020)).thenReturn(true);
        
        ro.unibuc.prodeng.exception.DuplicateMasinaException ex = assertThrows(ro.unibuc.prodeng.exception.DuplicateMasinaException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("O masina cu aceeasi marca, model si an exista deja", ex.getMessage());
        verify(masinaRepository, never()).save(any(Masina.class));
    }

    @Test
    void testCreateMasinaCurrentYearHighMileage() {
        int currentYear = java.time.Year.now().getValue();
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", currentYear, 10000.0, 50000, "Benzina", 90, "a@b.ro");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.createMasina(req);
        });
        
        assertEquals("O masina din anul curent sau viitor nu poate avea peste 1000 km", ex.getMessage());
    }

    @Test
    void testUpdateMasinaStatusVandut() {
        MasinaRequest req = new MasinaRequest("Dacia", "Logan", 2020, 10000.0, 50000, "Benzina", 90, "a@b.ro");
        Masina masina = new Masina("1", "Dacia", "Logan", 2020, 10000.0, 50000, "Benzina", 90, MasinaStatus.VANDUT, "a@b.ro");
        
        when(masinaRepository.findById("1")).thenReturn(Optional.of(masina));
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.updateMasina("1", req);
        });
        
        assertEquals("O masina vanduta nu mai poate fi modificata.", ex.getMessage());
    }

    @Test
    void testDeleteMasinaStatusRezervat() {
        Masina masina = new Masina("1", "Dacia", "Logan", 2020, 10000.0, 50000, "Benzina", 90, MasinaStatus.RESERVAT, "a@b.ro");
        
        when(masinaRepository.findById("1")).thenReturn(Optional.of(masina));
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            masinaService.deleteMasina("1");
        });
        
        assertEquals("Nu puteti sterge o masina care este rezervata", ex.getMessage());
    }
}

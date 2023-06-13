package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ProdusDTOTest {
    String id = "1";
    String nume = "produs1";
    String pret = "99";

    @Test
    void testConstructor() {
        ProdusDTO produsDTO = new ProdusDTO(id, nume, pret);

        assertEquals(id, produsDTO.getId());
        assertEquals(nume, produsDTO.getNume());
        assertEquals(pret, produsDTO.getPret());
    }

    @Test
    void testSetters() {
        ProdusDTO produsDTO = new ProdusDTO();

        assertEquals(null, produsDTO.getId());
        assertEquals(null, produsDTO.getNume());
        assertEquals(null, produsDTO.getPret());

        produsDTO.setId(id);
        produsDTO.setNume(nume);
        produsDTO.setPret(pret);

        assertEquals(id, produsDTO.getId());
        assertEquals(nume, produsDTO.getNume());
        assertEquals(pret, produsDTO.getPret());
    }
}

package ro.unibuc.hello.data;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ProdusTest {
    String id = "1";
    String nume = "produs1";
    String pret = "99";

    @Test
    void testConstructor() {
        Produs Produs = new Produs(id, nume, pret);

        assertEquals(id, Produs.getId());
        assertEquals(nume, Produs.getNume());
        assertEquals(pret, Produs.getPret());
    }

    @Test
    void testSetters() {
        Produs Produs = new Produs();

        assertEquals(null, Produs.getId());
        assertEquals(null, Produs.getNume());
        assertEquals(null, Produs.getPret());

        Produs.setId(id);
        Produs.setNume(nume);
        Produs.setPret(pret);

        assertEquals(id, Produs.getId());
        assertEquals(nume, Produs.getNume());
        assertEquals(pret, Produs.getPret());
    }
}
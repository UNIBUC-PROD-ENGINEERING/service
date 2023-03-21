package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MedicamentTest {

    String [] str = new String[]{"aa","bb"};

    Medicament medicamentTest = new Medicament("Nurofen",str);

    @Test
    void test_ingredients(){
        Assertions.assertSame(str, medicamentTest.getIngredients());
    }
    @Test
    void test_nume(){
        Assertions.assertSame("Nurofen", medicamentTest.getName());
    }
}

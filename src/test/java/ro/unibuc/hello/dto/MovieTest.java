package ro.unibuc.hello.dto;

import io.cucumber.java.bs.A;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.shaded.org.yaml.snakeyaml.error.MarkedYAMLException;

public class MovieTest {

    Movie myMovie = new Movie("Avatar",
            "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Na'vi race to protect their home.",
            192);

    @Test
    public void test_getTitle() {
        Assertions.assertSame("Avatar", myMovie.getTitle());
    }


    @Test
    public void test_getDescription() {
        Assertions.assertSame("Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Na'vi race to protect their home.",
                myMovie.getDescription());
    }

    @Test
    public void test_getRuntime() {
        Integer expected = 192;
        Integer result = myMovie.getRuntime();
        Assertions.assertEquals(expected, result);
    }


    @Test
    public void test_setTitle() {
        myMovie.setTitle("Ford v Ferrari");
        Assertions.assertSame("Ford v Ferrari", myMovie.getTitle());
    }

    @Test
    public void test_setDescription() {
        myMovie.setDescription("American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at the 24 Hours of Le Mans in 1966");
        Assertions.assertSame("American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at the 24 Hours of Le Mans in 1966", myMovie.getDescription());
    }

    @Test
    public void test_setRuntime() {
        myMovie.setRuntime(150);
        Integer expected = 150, result = myMovie.getRuntime();
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void test_ConstructorWithoutParams() {
        Movie movie = new Movie();
        Assertions.assertSame(null, movie.getTitle());
    }
}

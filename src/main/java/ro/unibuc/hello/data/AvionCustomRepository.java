package ro.unibuc.hello.data;
import java.util.*;

public interface AvionCustomRepository {
    public List<Avion> findAvionByProperties(String from, String to);
}

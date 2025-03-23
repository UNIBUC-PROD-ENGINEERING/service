import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ro.unibuc.hello.dto.PartySummaryDTO;

public class PartySummaryDTOTest {

    @Test
    public void testPartySummaryDTO_Creation() {
        PartySummaryDTO dto = new PartySummaryDTO("123", "Birthday Bash", "2025-04-01");

        assertEquals("123", dto.getId());
        assertEquals("Birthday Bash", dto.getName());
        assertEquals("2025-04-01", dto.getDate());
    }

    @Test
    public void testPartySummaryDTO_NullValues() {
        PartySummaryDTO dto = new PartySummaryDTO(null, null, null);

        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDate());
    }

    @Test
    public void testPartySummaryDTO_EmptyValues() {
        PartySummaryDTO dto = new PartySummaryDTO("", "", "");

        assertEquals("", dto.getId());
        assertEquals("", dto.getName());
        assertEquals("", dto.getDate());
    }
}

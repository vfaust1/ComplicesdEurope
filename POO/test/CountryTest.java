import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CountryTest {

    @Test
    public void testGetPays() {
        assertEquals("France", Country.FR.getPays());
        assertEquals("Espagne", Country.ES.getPays());
        assertEquals("Italie", Country.IT.getPays());
        assertEquals("Allemagne", Country.GE.getPays());
    }
}
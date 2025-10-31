import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CritereTest {

    @Test
    public void testGetType() {
        assertEquals('B', Critere.GUEST_ANIMAL_ALLERGY.getType());
        assertEquals("T".charAt(0), Critere.HOBBIES.getType());
    }

    @Test
    public void testIsBoolean() {
        assertTrue(Critere.GUEST_ANIMAL_ALLERGY.isBoolean());
        assertFalse(Critere.HOBBIES.isBoolean());
    }
}
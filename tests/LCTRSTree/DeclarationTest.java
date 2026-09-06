package LCTRSTree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeclarationTest {

    @Test
    void isPrivateTrueTest() {
        Declaration testDeclaration = new Declaration(true, "Test", 2);
        assertTrue(testDeclaration.isPrivate());
    }

    @Test
    void isPrivateFalseTest() {
        Declaration testDeclaration = new Declaration(false, "Test", 2);
        assertFalse(testDeclaration.isPrivate());
    }

    @Test
    void getName() {
        Declaration testDeclaration = new Declaration(false, "Test", 2);
        assertEquals("Test", testDeclaration.getName());
    }

    @Test
    void getSorts() {
        Declaration testDeclaration = new Declaration(false, "Test", 2);
        assertEquals(2, testDeclaration.getSorts());
    }
}
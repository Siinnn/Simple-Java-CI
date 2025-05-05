import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    @Test
    public void testAddition() {
        assertEquals(5, Calculator.add(3, 2));
    }

    @Test
    public void testReduction() {
        assertEquals(1, Calculator.reduce(3, 2));
    }
}

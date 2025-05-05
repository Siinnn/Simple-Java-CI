import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    @Test
    public void testAddition() {
        assertEquals(5, Calculator.add(3, 2));
    }
}

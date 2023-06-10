import polynomial.Polynomial;
import polynomial.PolynomialImpl;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PolynomialImplTest {
    Polynomial polynomial1 = new PolynomialImpl("4x^3 +3x^1 -5");
    Polynomial polynomial2 = new PolynomialImpl("-2x^5 -3x^4 +11x^1 -5");
    Polynomial polynomial3 = new PolynomialImpl("-2x^5 -3x^4 4x^3 14x^1 -10");
    Polynomial polynomial4 = new PolynomialImpl("5x^4 4x^3 +3x^1 -5");
    Polynomial polynomial5 = new PolynomialImpl("12x^4 +3x^1");
    Polynomial polynomial6 = new PolynomialImpl("3");
    Polynomial polynomial7 = new PolynomialImpl("");
    Polynomial polynomial8 = new PolynomialImpl();
    Polynomial polynomial9 = new PolynomialImpl("4x^3 25x^1 -15");
    Polynomial polynomial10 = new PolynomialImpl("4x^3 2x^2 25x^1 -15");
    Polynomial polynomial11 = new PolynomialImpl("12x^4 +0x^3 +0x^2 +3x^1 +3");
    Polynomial polynomial12 = new PolynomialImpl("12x^4 +4x^3 +0x^2 +6x^1 -5");

    @Test
    void ConstructorTest() {
        assertEquals(polynomial8.toString(),new PolynomialImpl().toString());
        assertEquals(polynomial2.toString(),new PolynomialImpl("-2x^5 -3x^4 +11x^1 -5").toString());
        assertEquals(polynomial5.toString(),new PolynomialImpl("5x^4 7x^4 +3x^1").toString());
        assertEquals(polynomial6.toString(),new PolynomialImpl("3").toString());
        assertEquals(polynomial7.toString(),new PolynomialImpl("").toString());
        assertThrows(IllegalArgumentException.class, () -> new PolynomialImpl("4x^3,+3x^1, -5"));
    }
    @Test
    public void toStringTest(){
        assertEquals("-2x^5 -3x^4 +11x^1 -5",polynomial2.toString());
        assertEquals("0",polynomial8.toString());
    }
    @Test
    public void addTest() {
        assertEquals(polynomial11.toString(),(polynomial5.add(polynomial6)).toString());
        assertEquals(polynomial12.toString(), (polynomial5.add(polynomial1)).toString());
    }
    @Test
    public void addTermTest(){
        assertThrows(IllegalArgumentException.class, () -> polynomial11.addTerm(5,-2));
        polynomial1.addTerm(5,4);
        assertTrue(polynomial4.isSame(polynomial1));
        polynomial9.addTerm(2,2);
        assertTrue(polynomial10.isSame(polynomial9));
    }
    @Test
    public void isSameTest(){
        assertEquals(true,polynomial2.isSame(polynomial2));
        assertEquals(false,polynomial2.isSame(polynomial3));
    }
    @Test
    public void evaluateTest(){
        assertEquals(1,polynomial2.evaluate(1));
        assertEquals(-17,polynomial2.evaluate(-1));
    }
    @Test
    public void getCoefficientTest() {
        assertEquals(-3,polynomial2.getCoefficient(4));
        assertEquals(0,polynomial2.getCoefficient(3));
    }
    @Test
    public void getDegreeTest() {
        assertEquals(5,polynomial3.getDegree());
        assertEquals(5,polynomial2.getDegree());
        assertEquals(0,polynomial6.getDegree());
    }
}
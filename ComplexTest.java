

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * The test class ComplexTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ComplexTest
{
    private final double DOUBLE_PRECISION = Math.pow(10, -10);
    
    private Random r = new Random();
    
    @Test
    public void testEquals(){
        double range = 10;
        double min = -5;
        
        double aReal = (r.nextDouble() * range) + min;
        double aImaginary = (r.nextDouble() * range) + min;
        
        double bReal = (r.nextDouble() * range) + min;
        double bImaginary = (r.nextDouble() * range) + min;
        
        Complex a = new Complex(aReal, aImaginary);
        Complex b = new Complex(bReal, bImaginary);
        
        // assert using .equals(), as well as testing components individually
        assertEquals(a,a);
        assertEquals(a, new Complex(aReal, aImaginary));
        assertEquals(a.getReal(), aReal, DOUBLE_PRECISION);
        assertEquals(a.getImaginary(), aImaginary, DOUBLE_PRECISION);
        
        assertEquals(b,b);
        assertEquals(b, new Complex(bReal, bImaginary));
        assertEquals(b.getReal(), bReal, DOUBLE_PRECISION);
        assertEquals(b.getImaginary(), bImaginary, DOUBLE_PRECISION);
        
        // test for inequality
        assertNotEquals(a,b);
        assertNotEquals(a, new Complex(bReal, bImaginary));
        assertNotEquals(b, new Complex(aReal, aImaginary));
        assertNotEquals(a.getReal(), b.getReal(), DOUBLE_PRECISION);
        assertNotEquals(a.getImaginary(), b.getImaginary(), DOUBLE_PRECISION);
    }
    
    @Test
    public void testAdd(){
        double range = 10;
        double min = -5;
        
        double aReal = (r.nextDouble() * range) + min;
        double aImaginary = (r.nextDouble() * range) + min;
        
        double bReal = (r.nextDouble() * range) + min;
        double bImaginary = (r.nextDouble() * range) + min;
        
        Complex a = new Complex(aReal, aImaginary);
        Complex b = new Complex(bReal, bImaginary);
        
        Complex result = a.add(new Complex(bReal, bImaginary));
        
        assertEquals(a, new Complex(aReal, aImaginary));
        assertNotEquals(a, b);
        
    }
    
    @Test
    public void testAddMultiple(){
        double range = 10;
        double min = -5;
        
        int count = r.nextInt(12) + 3;
        
        double [] reals = new double [count];
        double [] imaginaries = new double[count];
        
        Complex iterationResult = new Complex(0,0);
        double realResult = 0;
        double imaginaryResult = 0;
        
        Complex [] imaginaryNumbers = new Complex[count];
        for(int i = 0; i < count; i++){
            imaginaryNumbers[i] = new Complex(reals[i], imaginaries[i]);
        }
        
        // calculate proper result of adding the array of Complex numbers
        for(int i = 0; i < count; i++){
            iterationResult = iterationResult.add(imaginaryNumbers[i]);
            
            realResult += reals[i];
            assertEquals(realResult, iterationResult.getReal(), DOUBLE_PRECISION);
            
            imaginaryResult += imaginaries[i];
            assertEquals(imaginaryResult, iterationResult.getImaginary(), DOUBLE_PRECISION);
        }
        
        
        // get total sum using add(array) method
        Complex arrayResult = new Complex(0,0).add(imaginaryNumbers);
        
        // iterationResult loop contains assertions that guarentee it's value is proper        
        assertEquals(iterationResult, arrayResult);
    }
    
    @Test
    public void testRealMultiple(){
        double range = 10;
        double min = -5;
        
        double coefficient = (r.nextDouble() * range) + min;
        
        double realComponent = (r.nextDouble() * range) + min;
        double imaginaryComponent = (r.nextDouble() * range) + min;
        
        Complex a = new Complex (realComponent, imaginaryComponent);
        
        Complex result = a.multiply(coefficient);
        
        assertEquals(realComponent * coefficient, result.getReal(), DOUBLE_PRECISION);
        assertEquals(imaginaryComponent * coefficient, result.getImaginary(), DOUBLE_PRECISION);
    }
    
    @Test
    public void testComplexMultipleFixed(){
        double [][] aValues = {{1,1}, {1,2}, {1,3},
                               {2,1}, {2,2}, {2,3},
                               {3,1}, {3,2}, {3,3}};
                                        
        double [][] bValues = {{3,-3}, {3,-2}, {3,-1},
                               {2,-3}, {2,-2}, {2,-1},
                               {1,-3}, {1,-2}, {1,1}};
                               
        double [][] answerValues = {{6, 0}, {7, 4}, {6, 8},
                                    {7, -4}, {8, 0}, {7, 4},
                                    {6, -8}, {7, -4}, {0, 6}};
                                    
        Complex a;
        Complex b;
        Complex expectedResult;
        Complex computedResult;
        for(int pair = 0; pair < aValues.length; pair++){
            a = new Complex(aValues[pair][0], aValues[pair][1]);
            b = new Complex(bValues[pair][0], bValues[pair][1]);
            expectedResult = new Complex(answerValues[pair][0], answerValues[pair][1]);
            
            computedResult = a.multiply(b);
            
            assertEquals(expectedResult, computedResult);
        }
           
    }
}

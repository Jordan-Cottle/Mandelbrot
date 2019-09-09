/**
 * Write a description of class Complex here.
 *
 * @author Jordan Cottle
 * @version 1.0
 */
public class Complex
{
    // instance variables - replace the example below with your own
    private double realComponent;
    private double complexComponent;

    /**
     * Constructor for objects of class Complex
     * 
     * @param real the real comopnent for the complex number
     * @param imaginary the imaginary component for the complex number
     */
    public Complex(double real, double imaginary)
    {
        realComponent = real;
        complexComponent = imaginary;
    }

    /**
     * Method to add two complex numbers together;
     *
     * @param  other  The other complex number to add to this one
     * @return    A new Complex number that represents the results of adding the two complex numbers together
     */
    public Complex add(Complex other)
    {
        double real = this.realComponent + other.getReal();
        double imaginary = this.complexComponent + other.getImaginary();
        return new Complex(real, imaginary); 
    }
    
    
    /**
     * Method to add multiple complex numbers together;
     *
     * @param  others  The other complex numbers to add to this one
     * @return    A new Complex number that represents the results of adding all of the supplied complex numbers together
     */
    public Complex add(Complex... others){
        Complex result = this;
        for(Complex other: others){
            result = result.add(other);
        }
        return result;
    }
    
    /**
     * Method to multiply two complex numbers
     * 
     * @param other The other complex number to multiply by
     * 
     * @return A new Complex number that represents the results of multiplying the two Complex numbers together
     */
    public Complex multiply(Complex other){
        Complex result = this;
        
        double real = (realComponent * other.getReal()) - (complexComponent * other.getImaginary()); // i^2 == -1
        double complex = (realComponent * other.getImaginary()) + (complexComponent * other.getReal());
        
        return new Complex(real, complex);
    }
    
    /**
     * Method to multiply a complex number by a rational number
     * 
     * @param other The rational number to multiply by
     * 
     * @return A new Complex number that represents the results of multiplying the Complex and rational number together
     */
    public Complex multiply(double real){
        return new Complex(realComponent * real, complexComponent * real);
    }

    /**
     * Computes this Complex number raised to an integer power and returns the result
     * 
     * @param power The exponent to raise this Complex number to 
     * 
     * @return This Complex number raised to the specified power
     */
    public Complex exponent(int power){
        Complex c = this;
        for(int i = 1; i < power; i++){
            c = c.multiply(this);
        }

        return c;
    }

    /**
     * Computes the distance from the origin to this Complex number in the complex plane
     * 
     * @return The distance from the origin of the complex plane to this Complex number
     */
    public double distance(){
        return Math.sqrt(Math.pow(realComponent, 2) + Math.pow(complexComponent, 2));
    }

    /**
     * Determines if this Complex number C is in the mandelbrot set by computing Z = Z^pow + C recursively with Z starting at the origin. 
     * If the distance from the origin to Z is greater than 2 at any point in the process, the function returns early.
     * 
     * This function returns the number of iterations it took to determine that a number is not in the set.
     * If this Complex number is determined to be in the set (by reaching the maximum number of iterations without reaching the exit case), then -1 is returned
     * 
     * @param iterations The maximum number of iterations to run before determining that this Complex is included in the mandelbrot set
     * @param pow The exponent to use for the z^pow portion of the equation
     * 
     * @return The number of iterations it took to determine that this Complex is not part of the mandelbrot set, or -1 if the number is included in the set
     */
    public int mandelbrot(int iterations, int power){
        Complex c = this;

        int count = 0;
        Complex z = new Complex(0,0);

        while(count < iterations){
            // Z = Z^pow + C
            z = z.exponent(power).add(c);
            count++;

            if(z.distance() > 2.0){
                return count;
            }
        }

        return -1;
    }
    
    /**
     * Getter method for the real component of a complex number
     * 
     * @return the real component of the complex number
     */
    public double getReal(){
        return realComponent;
    }
    
    /**
     * Getter method for the imaginary component of a complex number
     * 
     * @return the imaginary component of the complex number
     */
    public double getImaginary(){
        return complexComponent;
    }
    
    /**
     * Method for comparing two Complex numbers for equality
     * 
     * @param obj object to compare to
     * 
     * @return whether or not the other obj was a Complex number with equal real and imaginary components
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        else if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        else {
            Complex other = (Complex) obj;
            return realComponent == other.getReal() && complexComponent == other.getImaginary();
        }
    } // end equals
}

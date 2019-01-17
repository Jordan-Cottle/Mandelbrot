import java.awt.Color;
import java.util.Random;

public class ColorPallete {
  private Color [] colors;
  private final Color [] DEFAULT_GRADIENT = {Color.red, Color.green, Color.blue};
  
  // default constructor
  public ColorPallete(int colorCount){
    this.colors = createGradient(DEFAULT_GRADIENT, colorCount);
  }  // end constructor
  
  /** 
   * Constructor to generate a pallete of colors from specified colors. It creates a gradient from each color in the
   * array to the next and returns the result as an array of individual colors.
   * @param colorKeys array of colors to generate a gradient with
   * @param colorCount total number of colors to have in the final pallete
   * @return a Color array that represents a gradient of colors
   */
  public ColorPallete(Color[] colorKeys, int colorCount){
    // number of colors in each two color section of the total gradient = total # of colors / # of sections
    int gradientResolution = colorCount / (colorKeys.length - 1);
    this.colors = createGradient(colorKeys, gradientResolution);
  }
  
  private static Color [] createGradient(Color start, Color end, int steps){
    Color [] colors = new Color [steps];
    
    int red = start.getRed();
    int green = start.getGreen();
    int blue = start.getBlue();
    
    double redDelta = (end.getRed() - red) / (double) steps;
    double greenDelta = (end.getGreen() - green) / (double) steps;
    double blueDelta = (end.getBlue() - blue) / (double) steps;
    
    for (int i = 0; i < steps; i++){
      colors[i] = new Color(red + (int) (i * redDelta), green + (int) (i * greenDelta), blue + (int) (i * blueDelta));
    }
    
    return colors;
  }
  
  private static Color[] createGradient(Color [] colors, int steps){ // steps = numer of steps between each color
    int totalSteps = (colors.length-1) * steps;
    
    Color [] gradient = new Color [totalSteps];
    int gradientIndex = 0;
    for (int i = 0; i < colors.length - 1; i++){ // loop through colors in supplied color array (needs index to locate adjacent colors)
      for (Color c: createGradient(colors[i], colors[i+1], steps)){  // loop through each color 'step' in gradient formed from each color pair
         gradient[gradientIndex++] = c;         
      } // end gradient loop
    } // end colors indexing loop 
    
    return gradient;
  }
  
  public static void test(Color [] colors){
   Picture pic = new Picture(colors.length * 2, 600);
   pic.show();
   int x = 0; // each color in the gradient gets 2 pixels (for more clarity)
    for(Color c: colors){
      for(int i = 0; i < 2; i++, x++){
        for(int y = 0; y < pic.getHeight(); y++){
          pic.getPixel(x, y).setColor(c);
        } 
        pic.repaint();
      } // end i,x loop
    } // end loop through color array
  } // end test
  
  public static void main (String [] args){
 Color [] gradientKeys = {new Color(110, 168, 127),  
new Color(54, 231, 160),  
new Color(167, 188, 66),  
new Color(170, 245, 40) 
};
    test(createGradient(gradientKeys, 125));
    /*
    Random r = new Random();
    
    for (int i = 2; i < 8; i++){
      Color [] testing = new Color[i];
      for(int j = 0; j < testing.length; j++){
       testing[j] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
      }
      
      colors = createGradient(testing, 75);
      test(colors);
    }
    */
  }
  
  public Color [] getColors () {
    return colors;
  }
  
  public Color getColor(int color){
    return colors[color]; 
  }
}  // end class
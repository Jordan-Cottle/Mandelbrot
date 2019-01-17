import java.awt.Color;
import java.util.Locale;
import java.util.Scanner;
import java.util.Random;
import java.nio.file.*;
import java.io.IOException;

public class Mandelbrot {
  private double leftBound;
  private double topBound;
  
  private double xRange;
  private double yRange;
  private double deltaX;
  private double deltaY;
  
  static final int GRADIENT_RESOLUTION = 50; // number of colors in each gradient
  
  static final int width = 640;
  static final int height = 640;  
  static final String DIRECTORY_PATH = "D:/Misc/Mandelbrot snips/";
  
  public SimplePicture graph;
  
  public static void main (String [] args) throws IOException{
    double x =   0.432539867562512;
    double y =   0.226118675951818;
    double radius = 7.85 * Math.pow(10, -7);
    int exponent = 2;
    int iMax = 2500;
    
    boolean zoomIn = false;
    boolean iterationTest = true;
    boolean exponentTest = false;
    
    boolean display = true;
    boolean randomizeColors = false;
    boolean savePic = true;
    
    
    String folderName = "test";
    if (savePic){
      folderName = "1_7_19 ColorIn A";
      Files.createDirectories(Paths.get(DIRECTORY_PATH + folderName));
    }
    
    Color [] colorKey = new Color[5];
    
    int colorResolution = colorKey.length * GRADIENT_RESOLUTION;  
    if (randomizeColors){
      Random r = new Random();
      System.out.print("{");
      for(int i = 0; i < colorKey.length; i++){
        colorKey[i] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)); 
        System.out.println("new Color(" + colorKey[i].getRed() + ", " + colorKey[i].getGreen() + ", " + colorKey[i].getBlue() + "), ");
      }
      System.out.println("}");
    }
    else{
      colorKey =  new Color [] {new Color(110, 168, 127),  
new Color(54, 231, 160),  
new Color(167, 188, 66),  
new Color(170, 245, 40) 
};
    }
    
    Color [] keyGradient = new ColorPallete(colorKey, colorResolution).getColors();
    
    Mandelbrot test = new Mandelbrot(exponent, iMax, x, y, radius, keyGradient);
    SimplePicture graph = test.graph;
    graph.show();
    
    if (zoomIn){
      int i = 0;
      for (double zoomRadius = 2; zoomRadius > radius; zoomRadius *= 0.85){

        test = new Mandelbrot(exponent, iMax, x, y, zoomRadius, keyGradient);
        graph = test.graph;
        
        if (display){
          graph.repaint();
        }
        
        if (savePic){
          String fileName = String.format(Locale.US, "%s\\%03d %3.4E", folderName, i++, zoomRadius).replace(".", ",");
          saveImage(graph, fileName);
        }
      } 
    } // end zoom
    else if (iterationTest) {  // test iteration resolution
      for (int iterations = 250; iterations <= iMax; iterations += 5){
        test = new Mandelbrot(exponent, iterations, x, y, radius, keyGradient);
        graph = test.graph;
        
        if (display){
          graph.repaint();
        }
        
        System.out.println(iterations);
        if (savePic){
          String fileName = String.format(Locale.US, "%s\\%d %3.4E", folderName, iterations, radius).replace(".", ",");
          saveImage(graph, fileName);
        }
      }
    }
    else if (exponentTest){
      for (int i = 3; i <= 10; i++){
        test = new Mandelbrot(i, iMax, x, y, radius, keyGradient);
        graph = test.graph;
        
        if (display){
          graph.repaint();
        }
        
        if (savePic){
          String fileName = String.format(Locale.US, "%s\\%d %3.4E", folderName, i, radius).replace(".", ",");
          saveImage(graph, fileName);
        }
      }
    }
  } // end main
  
  public Mandelbrot(int exponent, int maxIterations, double leftBound, double rightBound, double topBound, double botBound, Color [] colorKey){
    
    this.leftBound = leftBound;
    this.topBound = topBound;
    
    this.xRange = rightBound - leftBound;  // end - start
    this.yRange = botBound - topBound;  // end - start
    this.deltaX = xRange / (double) width;    
    this.deltaY = yRange / (double) height;
    
    graph = new SimplePicture(width, height);
    
    Complex number;
    //loop through pixels and test coords for inclusion in the mandelbrot
    for (int x = 0; x < width; x++){
      for(int y = 0; y < height; y++){
        double xCoord = pixelToCoord(x, deltaX, leftBound);
        double yCoord = pixelToCoord(y, deltaY, topBound);
        
        number = new Complex(xCoord, yCoord);
        
        int iterations = number.mandelbrot(maxIterations, exponent);
        if (iterations == -1)
          graph.getPixel(x,y).setColor(Color.black);
        else if (iterations == -2)
          graph.getPixel(x,y).setColor(Color.black);
        else{
          graph.getPixel(x,y).setColor(colorKey[iterations%colorKey.length]);
        }
      }
    }
    
    drawGridlines(graph);
  }
  
  public Mandelbrot(int exponent, int maxIterations, double x, double y, double radius, Color [] colorKey){
    this(exponent, maxIterations, x - radius, x + radius, y + radius, y - radius, colorKey);
  }
  
  private static double pixelToCoord(int pixelCoord, double delta, double start){
    return start + (pixelCoord * delta);
  }
  
  private static void saveImage(SimplePicture pic, String name){
    pic.write(DIRECTORY_PATH + name + "." + pic.getExtension());
  }
  
  private void drawGridlines(SimplePicture pic){
    double xSensitivity = deltaX / 2;
    double ySensitivity = Math.abs(deltaY / 2);
    // loop through x values
    for (int x = 0; x < width; x++){ 
      // locate x=0 (or y axis)
      if (Math.abs(pixelToCoord(x, deltaX, leftBound)) <= xSensitivity){
        // loop through all y pixels to draw y axis
        for (int y = 0; y < height; y++){ 
          pic.getPixel(x,y).setColor(Color.black);
          // if y=0 is found, draw x axis
          if (Math.abs(pixelToCoord(y, deltaY, topBound)) <= ySensitivity){
            for (int xx = 0; xx < width; xx++)
              pic.getPixel(xx,y).setColor(Color.black);
          } // end x axis drawing
        } // end y axis drawing
        // there is only one y axis, so end loop after finished with it
        break;
      } // end if x=0 is found
    } // end x=0 loop
  } // end draw gridlines
}
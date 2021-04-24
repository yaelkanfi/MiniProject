/**
 * 
 */
package unittests;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * @author Yael and Chagit
 *
 */
public class ImageWriterTest
{
	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
    @Test
    public void writeImageTest()
    {
    	 ImageWriter imageWriter = new ImageWriter("Image1", 800, 500); //create an image
         int Nx = imageWriter.getNx(); //
         int Ny = imageWriter.getNy();
         Color red=new Color(244, 0, 0); //rgb component for red color to lines
         Color pink=new Color(244, 194, 194); // rgb component for pink color to background 
         for (int col = 0; col < Ny; col++)
         {
             for (int row = 0; row < Nx; row++)
             {
                 if (col % 50 == 0 || row % 50 == 0)//to create the lines between the squares 
                     imageWriter.writePixel(row, col, red); //drawing black strips in regular intervals 
                 else
                     imageWriter.writePixel(row, col, pink);
             }
         }
         imageWriter.writeToImage(); //finally make this image
    }
}
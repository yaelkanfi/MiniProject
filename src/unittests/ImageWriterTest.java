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
         int Nx = imageWriter.getNx(); //rows
         int Ny = imageWriter.getNy();//columns
         Color red=new Color(244, 0, 0); //rgb component for red color to lines
         Color pink=new Color(244, 194, 194); // rgb component for pink color to background 
         for (int col = 0; col < Ny; col++)// move on all columns
         {
             for (int row = 0; row < Nx; row++)// move on all rows
             {
                 if (col % 50 == 0 || row % 50 == 0)//to create the lines between the squares 
                     imageWriter.writePixel(row, col, red); //drawing red strips in regular intervals (draw the lines)
                 else
                     imageWriter.writePixel(row, col, pink);//draw the backgrownd
             }
         }
         imageWriter.writeToImage(); //finally create this image
    }
}
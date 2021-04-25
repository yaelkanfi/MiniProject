/**
 * 
 */
package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * @author Yael and Chagit
 *This class creates from the scene the color matrix of the image
 */
public class Render {
private Scene scene; //the scene of the image
private Camera camera; //the camera
private  ImageWriter imageWriter;// the image that we create
private RayTracerBase rayTracer;// ray tracer


/**
 * @param scene the scene to set
 * @return Render- this object
 */
public Render setScene(Scene scene) {
	this.scene = scene;
	return this;
}
/**
 * @param camera the camera to set
 * @return Render- this object
 */
public Render setCamera(Camera camera) {
	this.camera = camera;
	return this;
}
/**
 * @param imageWriter the imageWriter to set
 * @return Render- this object
 */
public Render setImageWriter(ImageWriter imageWriter) {
	this.imageWriter = imageWriter;
	return this;
}
/**
 * @param rayTracer the rayTracer to set
 * @return Render- this object
 */
public Render setRayTracer(RayTracerBase rayTracer) {
	this.rayTracer = rayTracer;
	return this;
}
/**
 * For each pixel The function build ray and for each ray we will get a color from ray tracer.
 *  we put the color in the appropriate pixel of the image writer
 */
public void renderImage()
{
	if(scene==null)// if there is no scene, throw exception
		throw new MissingResourceException( "Error", ", ", "there is no scene" );
	if(camera==null)// if there is no camera, throw exception
		throw new MissingResourceException( "Error", ", ", "there is no camera" );
	if(imageWriter==null)// if there is no image, throw exception
		throw new MissingResourceException( "Error", ", ", "there is no imageWriter" );
	if(rayTracer==null)// if there is no rayTracer, throw exception
		throw new MissingResourceException( "Error", ", ", "there is no rayTracer" );
	
	int Nx = imageWriter.getNx(); //get number of row from image
    int Ny = imageWriter.getNy();//get number of columns from image
    for (int col = 0; col < Ny; col++) //move on all columns
     {
         for (int row = 0; row < Nx; row++) //move on all row
         {
        	 Ray ray = camera.constructRayThroughPixel(Nx, Ny, col, row); //construct a ray through the pixel
        	 imageWriter.writePixel(row, col, rayTracer.traceRay(ray)); //drawing color strips in regular intervals 
         }
     }
	}
/**
 * print a grid above the image
 * @param interval- where the line should be
 * @param color - the color of lines
 */
public void printGrid( int interval, Color color)
{
	if(imageWriter==null) // if there is no image, throw exception
		throw new MissingResourceException( "Error", ", ", "no image writer" );
	
	int Nx = imageWriter.getNx(); //get number of row from image
    int Ny = imageWriter.getNy();//get number of columns from image
    for (int col = 0; col < Ny; col++) //move on all columns
     {
         for (int row = 0; row < Nx; row++) //move on all row
         {
             if ((col % interval == 0 || row % interval == 0))//to create the lines between the squares 
                 imageWriter.writePixel(row, col, color); //drawing color strips in regular intervals 
             }
     }
 

}
/**
 * //call the function that create the wanted image,  if there is no image this function throw exception.
 */
public void writeToImage()
{
	if(imageWriter==null) // if there is no image, throw exception
		throw new MissingResourceException( "Error", ", ", "no image writer" );
	imageWriter.writeToImage();//call the function that create the wanted image.
}
	
}


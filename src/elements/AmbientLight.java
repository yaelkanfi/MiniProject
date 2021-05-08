/**
 * 
 */
package elements;

import primitives.Color;

/**
 * @author Yael and Chagit
 *
 */
public class AmbientLight extends Light{

/**
 * constructor that calculate the intensity using the super class constructor
 * new ambient light
 * @param IA the color
 * @param KA double parameter
 */
 public  AmbientLight(Color IA, double KA)
{
	
	 super(IA.scale(KA));
}
 /**
  * default constructor the intensity using the super class constructor and send black color.
  */
 public  AmbientLight()
 {
 	
 	 super(Color.BLACK);
 }

	



 
}

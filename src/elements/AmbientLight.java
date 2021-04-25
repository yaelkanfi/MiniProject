/**
 * 
 */
package elements;

import primitives.Color;

/**
 * @author Yael and Chagit
 *
 */
public class AmbientLight {
/**
 * private field intensity 
 */
private Color intensity; 
/**
 * constructor that calculate the intensity.
 * new ambient light
 * @param IA the color
 * @param KA double parameter
 */
 public  AmbientLight(Color IA, double KA)
{
	 intensity= IA.scale(KA);
}
/**
 * @return the intensity (color)
 */
public Color getIntensity() {
	return intensity;
}


 
}

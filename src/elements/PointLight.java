/**
 * 
 */
package elements;

import primitives.Point3D;

/**
 * @author Yael and Chagit
 *
 */
public class PointLight extends Light implements LightSource {
	
	private Point3D position;
	private double kC;
	private double kL;
	private double kQ;

}

/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Yael and Chagit
 *
 */
public interface LightSource {
	
	/**
	 * the function returns the intensity of a point
	 * @param p - Point3D
	 * @return color- intensity of point
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 * the function returns the vector from point
	 * @param p - Point3D
	 * @return vector - directional vector 
	 */
	public Vector getL(Point3D p);


}

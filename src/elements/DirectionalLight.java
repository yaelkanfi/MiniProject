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
public class DirectionalLight extends Light implements LightSource {
	
	private Vector direction;// directional vector

	/**constructor that gets intensity color and direction vector
	 * @param intensity color
	 * @param direction -directional vector
	 */
	public DirectionalLight(Color intensity, Vector direction)
	{
		super(intensity);
		this.direction = direction.normalized();		
	}

	@Override
	public Color getIntensity(Point3D p) {
		// TODO Auto-generated method stub
		return super.getIntensity(); //return the intensity color
	}

	@Override
	public Vector getL(Point3D p) {
		// TODO Auto-generated method stub
		return direction;//return the direction vector of the light
	}
	
	

}

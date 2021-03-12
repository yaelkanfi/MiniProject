/**
 * 
 */
package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Sphere implements Geometry 
{
	Point3D center;
	double radius;
	@Override
	public Vector get_Normal(Point3D p) 
	{
		// TODO Auto-generated method stub
		return p.subtract(this.center).normalize();
	}	
	/**
	 * constructor that get a point and radius and return a sphere
	 * @param center Point3D
	 * @param radius double
	 */
	public Sphere(Point3D center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}
	/**the function return the center point of the circle 
	 * @return the center- point3D
	 */
	public Point3D getCenter() {
		return center;
	}
	/**the function return radius
	 * @return the radius-- double value
	 */
	public double getRadius() {
		return radius;
	}
	@Override
	public String toString() {
		return "Sphere [center=" + center.toString() + ", radius=" + radius + "]";
	}
	
	
	
	
 
}

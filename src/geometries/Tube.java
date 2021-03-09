/**
 * 
 */
package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886 //
 *
 */
public class Tube implements Geometry 
{
    Ray axisRay;
    double radius;
    
	/**
	 * constructor that get ray and radius and return tube
	 * @param axisRay
	 * @param radius
	 */
	public Tube(Ray axisRay, double radius) {
		super();
		this.axisRay = axisRay;
		this.radius = radius;
	}

	@Override
	public Vector get_Normal(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * the function return ray
	 * @return the axisRay--Ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * the function return radius
	 * @return the radius--double
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay.toString() + ", radius=" + radius + "]";
	}

    
    
    
}

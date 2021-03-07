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
public class Plane implements Geometry {
	
	Point3D q0;
	Vector normal;
	/**
	 * constructor that gets 3 point and returns a plane
	 * @param q1 Point3D
	 * @param q2 Point3D
	 * @param q3 Point3D
	 */
	public Plane(Point3D q1, Point3D q2, Point3D q3)
	{
		super();
		this.normal=null;
		this.q0 = q1;
	}
	/**constructor that gets a vector and a point and returns a plane
	 * @param q0 Point3D
	 * @param normal Vector
	 */
	public Plane(Point3D q0, Vector normal) {
		super();
		this.q0 = q0;
		this.normal = normal;
	}
	@Override
	public Vector get_Normal(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}
	/**the function return point
	 * @return the q0- point
	 */
	public Point3D getQ0() {
		return q0;
	}
	/**the function return   normal vector 
	 * @return the normal
	 */
	public Vector getNormal() {
		return normal;
	}
	@Override
	public String toString() {
		return "point: "+q0.toString() +" normal vector: "+normal.toString();
	}

}

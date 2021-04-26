/**
 * 
 */
package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Plane extends Geometry {
	
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
		Vector v1 = q2.subtract(q1);
		Vector v2 = q3.subtract(q1);
		this.normal = (v1.crossProduct(v2)).normalize();
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
		return this.normal;
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
	@Override
	public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		 //get ray point and vector
        Point3D rayPoint = ray.getP0();
        Vector rayVector = ray.getDir();

        // check if the ray is parallel to the plane
        if (isZero(normal.dotProduct(rayVector))) // dotProduct = 0 => parallel
            return null;
        //check if the ray and the plane start at the same point
        if(ray.getP0().equals(q0))
        	return null;
        try {

            double t = alignZero((normal.dotProduct(q0.subtract(rayPoint))) / (normal.dotProduct(rayVector)));
         // check if the ray starts on the plane
            if(isZero(t))
               return null;
          //check if the ray crosses the plane
            else if(t > 0) 
            	return List.of(ray.getPoint(t));
         //no intersection between the ray and the plane
            else 
                return null;

        } catch(Exception ex){
            // p.subtract(rayP) is vector zero, which means the ray point is equal to the plane point (ray start on plane)
        	return null;
        }
	}
}
	
	


/**
 * 
 */
package geometries;
import primitives.Point3D;
import static primitives.Util.*;
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
	public Vector get_Normal(Point3D point) {
		Point3D o =axisRay.getP0();
	    Vector v=axisRay.getDir();
	    double t=point.subtract(o).dotProduct(v);
	    if(!isZero(t))
	    {
	       o=o.add(v.scale(t));
	       return point.subtract(o).normalize();
	    }
	  //if the point is on the same level, return normal
        return point.subtract(axisRay.getP0()).normalized();
	}
		
	  ;

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

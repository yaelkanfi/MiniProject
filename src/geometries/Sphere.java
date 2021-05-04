/**
 * 
 */
package geometries;
import static primitives.Util.*;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Sphere extends Geometry 
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
	
	/*@Override
	/**
	 * @param Ray ray - the ray that intersect the plane
	 * @return List<Point3D> - the list of intersection Point3D
	 */
	/*public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		//point and vector of ray
		Point3D p0 = ray.getP0();//ray point
        Vector v = ray.getDir();//ray vector
       //check if ray point is the center
        if(p0.equals(center))       //
        	return List.of(ray.getPoint(radius));
        Vector u=center.subtract(p0);// the vector between center and ray
		double tm=v.dotProduct(u); //the scale for the ray in order to get parallel to the sphere center
		double d=Math.sqrt(u.lengthSquared()-tm*tm);//get the distance between the ray and the sphere center
		//check if d is bigger then radius-the ray doesn't cross the sphere
		if (d>radius)
			return null;
		double th=Math.sqrt(radius*radius-d*d);//according pitagoras
		double t1=tm+th;
		double t2=tm-th;
		if(t1>0&&t2>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if orthogonal -> no intersection 
			return List.of(ray.getPoint(t1),ray.getPoint(t2));
		else if(t1>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))) //if only t1 is not orthogonal and positive
			return List.of(ray.getPoint(t1));
		else if(t2>0&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if only t2 is not orthogonal and positive
			return List.of(ray.getPoint(t2));
		else
			return null;
	}*/
	
	@Override
	public String toString() {
		return "Sphere [center=" + center.toString() + ", radius=" + radius + "]";
	}
	
	@Override
	/**
	 * @param Ray ray - the ray that intersect the plane
	 * @return List<GeoPoint> - the list of intersection GeoPoints
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		// TODO Auto-generated method stub
		//point and vector of ray
		Point3D p0 = ray.getP0();//ray point
        Vector v = ray.getDir();//ray vector
       //check if ray point is the center
        if(p0.equals(center))       //
        	return List.of(new GeoPoint(this, ray.getPoint(radius)));
        Vector u=center.subtract(p0);// the vector between center and ray
		double tm=v.dotProduct(u); //the scale for the ray in order to get parallel to the sphere center
		double d=Math.sqrt(u.lengthSquared()-tm*tm);//get the distance between the ray and the sphere center
		//check if d is bigger then radius-the ray doesn't cross the sphere
		if (d>radius)
			return null;
		double th=Math.sqrt(radius*radius-d*d);//according pitagoras
		double t1=tm+th;
		double t2=tm-th;
		if(t1>0&&t2>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v)) && !isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if orthogonal -> no intersection 
			return List.of(new GeoPoint(this,ray.getPoint(t1)) , new GeoPoint(this, ray.getPoint(t2))); //convert from list of Point3D to GeoPoint
		else if(t1>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))) //if only t1 is not orthogonal and positive
			return List.of(new GeoPoint(this,ray.getPoint(t1))); //convert from list of Point3D to GeoPoint
		else if(t2>0&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if only t2 is not orthogonal and positive
			return List.of(new GeoPoint(this,ray.getPoint(t2))); //convert from list of Point3D to GeoPoint
		else
			return null;
	}
	
	
	
	
 
}
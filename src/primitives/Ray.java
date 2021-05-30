/**
 * 
 */
package primitives;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;
/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Ray 
{
Point3D p0;
Vector dir;
private static final double DELTA = 0.1; //Size of first moving rays
/** constructor who gets clean finds a direction vector and returns Ray
 * @param p0 starting point
 * @param dir direction vector
 */
public Ray(Point3D p0, Vector dir)
{
	super();
	this.p0 = p0;
	this.dir = dir.normalized();
}
/**
 * 
 * @param pointGeo point
 * @param dir- directional vector 
 * @param n- vector normal
 * p0=point + normal.scale(±DELTA)
 */
public Ray(Point3D pointGeo, Vector direction, Vector n)
{
	dir=direction.normalized();
	double vN = n.dotProduct(direction); //dot product to the directional vector
	Vector delta = n.scale(vN >= 0 ? DELTA : -DELTA); //check sign
	p0= pointGeo.add(delta);//moving the point according delta
	
}

/** the function return starting point
 * @return the p0- point
 */
public Point3D getP0() {
	return p0;
}

/** the function return direction vector
 * @return the dir -vector
 */
public Vector getDir() {
	return dir;
}

@Override
public int hashCode() {
	return Objects.hash(dir, p0);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (!(obj instanceof Ray))
		return false;
	Ray other = (Ray) obj;
	return dir.equals(other.dir) && p0.equals( other.p0);
}
@Override
public String toString() {
	return p0.toString() + " + t" + dir.toString();
}

/**
 * 
 * @param t double
 * @return If point is 0 the function returns p0 otherwise returns a new point which is P0 + vector direction * scalar 
 */
public Point3D getPoint(double t)
{
	 Point3D p1 = p0.add(dir.scale(t));
	return isZero(t) ? p0 : new Point3D(p1.x, p1.y, p1.z);
}
/**
 * In the points list - find the point with minimal distance from the
ray head point and return it
 * @param pointList list of intersection point
 * @return the closet point to ray point (p0)
 */
public Point3D findClosestPoint(List<Point3D> pointList) 
{
	if (pointList==null)//if the list is empty
		return null;

	Point3D closestPoint=pointList.get(0);	//begin with the first point
	double min=p0.distance(pointList.get(0));	// find the distance between the point

	for(int i=0; i<pointList.size(); i++) 	//run on the list
	{
		if (p0.distance(pointList.get(i))<min) //if there is a closer point update
		{
			min=p0.distance(pointList.get(i));
			closestPoint=pointList.get(i);		    
		}
	}
	return closestPoint;
}

/**
 * the function returns the closest GeoPoint from list by using distance of the point
 * @param lst - list of GeoPoints
 * @return GeoPoint - the closest GeoPoint in the list
 */
public GeoPoint findClosestGeoPoint(List<GeoPoint> lst)
{
	if (lst == null)//if the list is empty
		return null;

	GeoPoint closestPoint=lst.get(0);	//begin with the first point
	double min = p0.distance(lst.get(0).point);	// find the distance between the point

	for(int i=0; i<lst.size(); i++) 	//run on the list
	{
		if (p0.distance(lst.get(i).point)<min) //if there is a closer point update
		{
			min=p0.distance(lst.get(i).point);
			closestPoint=lst.get(i);// of type GeoPoint		    
		}
	}
	return closestPoint;
}


public List<Ray> splitRay(int number, double distance, double radius) {
	if (number < 1) {
		return new ArrayList<Ray>();
	}
	
	List<Ray> retRays = new ArrayList<Ray>();
	retRays.add(this);
	
	Vector normal = this.dir.getNormal();
	Vector normal2 = this.dir.crossProduct(normal).normalize();
	for (int i = 0; i < number - 1; i++) {
		double up = random(-radius, radius);
		double maxRight = Math.sqrt(radius*radius - up*up);
		double right = random(-maxRight, maxRight);
		Point3D destination = this.getPoint(distance);
		if (up != 0) {
			destination = destination.add(normal.scale(up));
		}
		if (right != 0) {
			destination = destination.add(normal2.scale(right));
		}
		Vector dir = destination.subtract(p0);
		retRays.add(new Ray(p0, dir));
	}
	return retRays;
}
}

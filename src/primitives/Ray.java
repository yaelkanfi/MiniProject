/**
 * 
 */
package primitives;

import java.util.List;
import java.util.Objects;
import static primitives.Util.*;
/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Ray 
{
Point3D p0;
Vector dir;
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
}

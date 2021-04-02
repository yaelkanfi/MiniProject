/**
 * 
 */
package primitives;

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
/**
 * 
 * @param t
 * @return If point is 0 the function returns p0 otherwise returns a new point which is P0 + vector direction * scalar 
 */
public Point3D getPoint(double t)
{
	 Point3D p1=p0.add(dir.scale(t));
	return isZero(t) ? p0 : new Point3D(p1.x, p1.y, p1.z);
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

}

/**
 * 
 */
package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886 
 *
 */
public class Cylinder extends Tube 
{
double height;

/**constructor that get ray, radius and height and return Cylinder
 * @param axisRay ray
 * @param radius double
 * @param height double
 */
public Cylinder(Ray axisRay, double radius, double height) {
	super(axisRay, radius);
	this.height = height;
}

@Override
public Vector get_Normal(Point3D p) {
	// TODO Auto-generated method stub
	if(p.subtract(axisRay.getP0().add(axisRay.getDir().scale(height))).length()<radius)
        return axisRay.getDir().normalize();
	if(p.subtract(axisRay.getP0()).length()<radius)
		return axisRay.getDir().scale(-1).normalize();
	if(p.subtract(axisRay.getP0()).length()==radius)
		return p.subtract(axisRay.getP0()).normalize();
	else
	   return super.get_Normal(p);
}

@Override
public Ray getAxisRay() {
	// TODO Auto-generated method stub
	return super.getAxisRay();
}

@Override
public double getRadius() {
	// TODO Auto-generated method stub
	return super.getRadius();
}

/**
 * @return the height
 */
public double getHeight() {
	return height;
}

@Override
public String toString() {
	return "Cylinder [height=" + height + ", axisRay=" + axisRay.toString() + ", radius=" + radius + "]";
}

}

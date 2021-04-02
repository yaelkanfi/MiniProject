/**
 * 
 */
package geometries;

import primitives.Vector;

import primitives.Point3D;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public interface Geometry extends Intersectable
{
	/**
	 *
	 * @param p point3D 
	 * @return  the normal of point
	 */
	public Vector get_Normal(Point3D p);
}

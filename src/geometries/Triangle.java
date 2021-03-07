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
public class Triangle extends Polygon 
{

	/**
	 * constructor that get 3 points and return a triangle
	 * @param p1 Point3D Vertex
	 * @param p2 Point3D Vertex
	 * @param p3 Point3D Vertex
	 * @throws IllegalArgumentException
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) throws IllegalArgumentException {
		super(p1, p2, p3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector get_Normal(Point3D point) {
		// TODO Auto-generated method stub
		return super.get_Normal(point);
	}

	@Override
	public String toString() {
		return "Triangle [vertices: " + super.vertices.toString() + "]";
	}
}

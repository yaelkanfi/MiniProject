/**
 * 
 */
package unittests;////

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

/** 
 * Unit tests for geometries.Geometries class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 * -
 */

public class GeometriesTests {
	
	/**
	 * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersectionPoint()
	{
		Geometries empty = new Geometries(); //empty list
		Geometries geometries = new Geometries();
		//add geometries to list
		geometries.add(new Sphere(new Point3D(4,0,0), 1d), //new sphere
				new Triangle(new Point3D(2,0,1),new Point3D(2,0,-1),new Point3D(2,1,0)), //new triangle
				new Plane(new Point3D(0,0,0), new Vector(1,0,0))); //new plane
		
		// ============ Equivalence Partitions Tests ==============
		
		//TC01: some geometries are intersected but not all of them
		Ray ray = new Ray(new Point3D(-1,2,0), new Vector(1, 0, 0));
		List<Point3D> result = geometries.findIntersections(ray);
		assertEquals("Wrong number of points", 1, result.size());
		
		// =============== Boundary Values Tests ==================
		
		//TC11: empty collection
	    ray = new Ray(new Point3D(1,1,0), new Vector(1, 0, 0));
		assertNull("Geometries is empty", empty.findIntersections(ray));
		
		//TC12: no geometry is intersected
		ray = new Ray(new Point3D(-1,0,0), new Vector(0,1, 0));
		assertNull("Geometries intersected", geometries.findIntersections(ray));
		
		//TC13: one geometry is intersected
		ray = new Ray(new Point3D(2.5,0,0), new Vector(4,0,0));
	    result = geometries.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		
		//TC14: all geometries are intersected
		ray = new Ray(new Point3D(-1,0,0), new Vector(1,0,0));
		result = geometries.findIntersections(ray);
		assertEquals("Wrong number of points", 3, result.size());
	}
}

/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
/**
 * Unit tests for geometries.Triangle class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class TriangleTests {

	/**
	 * Test method for {@link geometries.Triangle#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testNormal() {
		Triangle tri = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(0,0,0));
        assertEquals(new Vector(0,0,1),tri.get_Normal(null));
	}

	   /**
     * Check findIntersections
     */
    @Test
    public void testFindIntersectionPoint() 
    {
        Triangle tri = new Triangle(new Point3D(0, 3, -3),new Point3D(3, 0, -3),new Point3D(-3, 0, -3));
        Ray ray;

        // EP:

        // the ray goes through the triangle
        ray = new Ray(new Point3D(1, 1, -2), new Vector(-2, 0.5, -1));
        assertEquals("the ray goes through the triangle", List.of(new Point3D(-1, 1.5, -3)), tri.findIntersections(ray));
        // the ray is outside the triangle between 2 far sides
        ray = new Ray(new Point3D(4, 4, -2), new Vector(1, 1, -4));
        assertEquals("the ray is outside the triangle between 2 far sides", null, tri.findIntersections(ray));
        // the ray is outside the triangle between 2 close sides
        ray = new Ray(new Point3D(-4, -1, -2), new Vector(-1, -1, -1));
        assertEquals("the ray is outside the triangle between 2 close sides", null, tri.findIntersections(ray));


        // BVA:

        
        // ray goes through the continuation of side 1
        ray = new Ray(new Point3D(-1, 4, -2), new Vector(0, 0, -1));
        assertEquals("ray goes through the continuation of side 1", null, tri.findIntersections(ray));
       

        // ray through edge
        ray = new Ray(new Point3D(-2, 1, -1), new Vector(0, 0, -1));
        assertEquals("ray through edge", null, tri.findIntersections(ray));
     

        // ray through vertex 
        ray = new Ray(new Point3D(0, 3, -2), new Vector(0, 0, -1));
        assertEquals("ray through vertex", null, tri.findIntersections(ray));
      
    }
}
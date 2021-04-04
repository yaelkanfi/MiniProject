/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import java.util.List;
import static primitives.Util.isZero;

import org.junit.Test;

import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Plane class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class PlaneTests {


	/**
	 * Test method for {@link geometries.Plane#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGet_Normal() {
		
	        // ============ Equivalence Partitions Tests ==============
			Point3D p1 = new Point3D(1,2,0);
			Point3D p2 = new Point3D(-4,7,0);
			Point3D p3 = new Point3D(1,0,5);
			Plane p = new Plane(p1,p2, p3); //plane from the points
			Vector v1 = new Vector(p1.subtract(p2).getHead());
			Vector v2 = new Vector(p2.subtract(p3).getHead());
			Vector v3 = new Vector(p3.subtract(p1).getHead());
			Vector n = p.get_Normal(p1); //n is the normal of p in p0
			assertTrue("ERROR: Bad normal to plane", isZero(v1.dotProduct(n)));
			assertTrue("ERROR: Bad normal to plane", isZero(v2.dotProduct(n)));
			assertTrue("ERROR: Bad normal to plane", isZero(v3.dotProduct(n)));
			 try {
		        	new Plane(new Point3D(1,2,3), new Point3D(2,4,6), new Point3D(4,8,12)).get_Normal(p1);
		            fail("GetNormal() should throw an exception, but it failed");
		        } catch (Exception e) {}
	}
	
    /**
     * Check findIntersections
     */
    @Test
    public void findIntersections() {
        Plane p = new Plane(new Point3D(0, 0, -3), new Vector(0, 0, -1));
        Ray ray;

        // EP: The Ray must be neither orthogonal nor parallel to the plane
        // the ray intersects the plane
        ray = new Ray(new Point3D(1, 1, 0), new Vector(2, 1, -1));
        List<Point3D> result = p.findIntersections(ray);
        assertEquals("the ray intersects the plane", List.of(new Point3D(7, 4, -3)), result);
        
        // the ray does not intersect the plane
        ray = new Ray(new Point3D(1, 1, 0), new Vector(2, 1, 1));
        result=p.findIntersections(ray);
        assertEquals("the ray does not intersect the plane", null, result);


        // BVA: Ray is parallel to the plane
        // the ray is included in the plane
        ray = new Ray(new Point3D(1, 2, -3), new Vector(2, 1, 0));
        assertEquals("the ray is parallel and included in the plane", null, p.findIntersections(ray));
        // the ray is not included in the plane
        ray = new Ray(new Point3D(1, 2, -2), new Vector(2, 1, 0));
        assertEquals("the ray is parallel and not included in the plane", null, p.findIntersections(ray));


        // BVA: Ray is orthogonal to the plane
        // Ray starts before the plane
        ray = new Ray(new Point3D(1, 1, 0), new Vector(0, 0, -1));
        assertEquals("the ray is orthogonal and starts before the plane",List.of(new Point3D(1, 1, -3)), p.findIntersections(ray));
        // Ray starts in the plane
        ray = new Ray(new Point3D(1, 1, -3), new Vector(0, 0, -1));
        assertEquals("the ray is orthogonal and starts in the plane", null, p.findIntersections(ray));
        // Ray starts after the plane
        ray = new Ray(new Point3D(1, 1, -4), new Vector(0, 0, -1));
        assertEquals("the ray is orthogonal and starts after the plane", null, p.findIntersections(ray));


        // BVA: Starting point on the plane, but the rest of the ray is not
        ray = new Ray(new Point3D(1, 1, -3), new Vector(2, 1, -1));
        assertEquals("Starting point is on the plane, but the rest of the ray is not", null, p.findIntersections(ray));


        // BVA: Starting point is equal to the plane point
        ray = new Ray(new Point3D(0, 0, -3), new Vector(2, 1, -1));
        assertEquals("Starting point is equal to the plane point", null, p.findIntersections(ray));
    }
	}


/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Sphere;
import primitives.Point3D;
import primitives.Vector;

/**
 * Unit tests for geometries.Sphere class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGet_Normal() {
		Point3D p = new Point3D(1, 1, 6);
		Point3D center = new Point3D(1,1,1);
		Sphere s = new Sphere(center, 5);
		Vector v = center.subtract(p).normalize();
		// ============ Equivalence Partitions Tests ==============
		assertEquals("Bad normal to sphere", v, s.get_Normal(p));
		// =============== Boundary Values Tests ==================
        // 
        try {
        	new Sphere(center, 0).get_Normal(p);
            fail("GetNormal() should throw an exception, but it failed");
        } catch (Exception e) {}
	}
}
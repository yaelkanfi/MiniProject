/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Cylinder;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Cylinder class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testNormal() {
		// ============ Equivalence Partitions Tests =================
			 Cylinder c = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 3, 10);
			 Vector v = new Vector(0, 0, -1);
			 assertEquals("Bad normal to cylinder", v, c.get_Normal(new Point3D(2, 2, 0)));
			 v = new Vector(0, 0, 1);
			 assertEquals("Bad normal to cylinder", v, c.get_Normal(new Point3D(1, 2, 10)));
			 v = new Vector(1, 0, 0);
	        assertEquals("Bad normal to cylinder", v, c.get_Normal(new Point3D(3, 0, 6)));
	     // ============ Boundary Values Tests =========================
	        v = new Vector(1, 0, 0);
	        assertEquals("Bad normal to cylinder", v, c.get_Normal(new Point3D(3, 0, 0)));
	        v = new Vector(1, 0, 0);
	        assertEquals("Bad normal to cylinder", v, c.get_Normal(new Point3D(3, 0, 10)));
	}

}

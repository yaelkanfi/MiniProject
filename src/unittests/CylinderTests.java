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
	public void testGet_Normal() {
		// ============ Equivalence Partitions Tests ==============
			  Ray r = new Ray(new Point3D(1,0,0), new Vector(0,1,0));
			  Cylinder cyl = new Cylinder(r, 1, 1);
			  assertEquals("Wrong normal to Cylinder", new Vector(1, 0, 0), cyl.get_Normal(new Point3D(2, 0, 0)));
	}

}

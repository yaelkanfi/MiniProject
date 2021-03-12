/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;

import geometries.Plane;
import primitives.Point3D;
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
			Plane p = new Plane(p1,p2, p3);
			Vector v1 = new Vector(p1.subtract(p2).getHead());
			Vector v2 = new Vector(p2.subtract(p3).getHead());
			Vector v3 = new Vector(p3.subtract(p1).getHead());
			Vector n = p.get_Normal(p1);
			assertTrue("ERROR: Bad normal to plane", isZero(v1.dotProduct(n)));
			assertTrue("ERROR: Bad normal to plane", isZero(v2.dotProduct(n)));
			assertTrue("ERROR: Bad normal to plane", isZero(v3.dotProduct(n)));
			 try {
		        	new Plane(new Point3D(1,2,3), new Point3D(2,4,6), new Point3D(4,8,12)).get_Normal(p1);
		            fail("GetNormal() should throw an exception, but it failed");
		        } catch (Exception e) {}
	}
	}



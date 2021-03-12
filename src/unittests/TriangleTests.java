/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;

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
	public void testGet_Normal() {
		Triangle tri = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(0,0,0));
        assertEquals(new Vector(0,0,1),tri.get_Normal(null));
	}

}
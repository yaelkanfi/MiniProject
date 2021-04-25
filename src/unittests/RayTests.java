/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector ;

/**
 * @author Yael and Chagit
 *
 */
public class RayTests {
	/**
	 * Test method for {@link primitives.Point3D#findClosestPoint()}.
	 */
	@Test
	public void findClosestPointTest() 
	{
	Ray ray=new Ray(Point3D.ZERO, new Vector(1,0,0));
	// ============ Equivalence Partitions Tests ==============
	//TC01: the closest point is in the middle of the list
	List<Point3D> lst1 = List.of(new Point3D(5,0,0),new Point3D(4,0,0),new Point3D(2,0,0) , new Point3D(3,0,0),new Point3D(7,0,0));
	assertEquals("wrong closest point", new Point3D(2,0,0), ray.findClosestPoint(lst1));
		// =============== Boundary Values Tests ==================
	//TC11:Empty list of points
	assertEquals("no closest point", null, ray.findClosestPoint(null));
	//TC12:the closest point is in the first index of the list
	lst1 = List.of(new Point3D(2,0,0),new Point3D(4,0,0),new Point3D(5,0,0) , new Point3D(3,0,0),new Point3D(7,0,0));
	assertEquals("wrong closest point", new Point3D(2,0,0), ray.findClosestPoint(lst1));
	//TC13:the closest point is in the last index of the list
	lst1 = List.of(new Point3D(8,0,0),new Point3D(4,0,0),new Point3D(5,0,0) , new Point3D(3,0,0),new Point3D(2,0,0));
	assertEquals("wrong closest point", new Point3D(2,0,0), ray.findClosestPoint(lst1));
	
	
	
	}

}

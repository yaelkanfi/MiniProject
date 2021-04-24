/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for elements.Camera class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class IntegrationTests {

	/**
	 * the function calculates how many intersections view plan has with Intersectable
	 * @param Nx 
	 * @param Ny 
	 * @param cam
	 * @param geo
	 * @return amount of intersections with geo
	 */
	private int countIntersections(int Nx, int Ny, Camera cam, Intersectable geo) {
		List<Point3D> intersections; //list of intersections points of geo
		int count = 0; //counter of intersection points
		for (int i = 0; i < Nx; ++i) { //width
			for (int j = 0; j < Ny; ++j) { //height
				Ray ray = cam.constructRayThroughPixel(3, 3, j, i);  //calculate ray
				intersections = geo.findIntersections(ray); //calculate intersections for ray
				if (intersections != null) //if there are intersections
					count += intersections.size(); //sum the amount of intersections
			}
		}
		return count;
	}
	
	Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
	Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
	
	//Test cases for Sphere
	@Test
	public void cameraSphereIntersections() {

		// ***** 2 intersection points*****
		Sphere sph = new Sphere(new Point3D(0, 0, -3), 1);
		int Nx = 3;// pixels in row
		int Ny = 3;// pixels in column		
		cam1.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
		assertEquals("Wrong number of points", 2, countIntersections(Nx,Ny,cam1, sph));

		// ***** 18 intersection points*****
		sph = new Sphere(new Point3D(0, 0, -2.5), 2.5);
		cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
		assertEquals("Wrong number of points", 18, countIntersections(Nx,Ny,cam2,sph));

		// ***** 10 intersection points*****
		sph = new Sphere(new Point3D(0, 0, -2), 2);
		cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
		assertEquals("Wrong number of points", 10, countIntersections(Nx,Ny,cam2,sph));

		// ***** 9 intersection points*****
		sph = new Sphere(new Point3D(0, 0, 1), 4);
		cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
		assertEquals("Wrong number of points", 9, countIntersections(Nx,Ny,cam2,sph));

		// ***** 0 intersection points*****
		sph = new Sphere(new Point3D(0, 0, 1), 0.5);
		cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
		assertEquals("Wrong number of points", 0, countIntersections(Nx,Ny,cam2,sph));
	}
	
	//Test cases for Plane
	@Test
	public void cameraPlaneIntersections() {
		
		// ***** 9 intersection points***** 
	    Plane pln = new Plane(new Point3D(0,0,-4),new Vector(0, 0, 1));
	    int Nx = 3;// pixels in row
		int Ny = 3;// pixels in column	
	    cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
	    assertEquals("Wrong number of points", 9, countIntersections(Nx,Ny,cam2,pln));
	    
	 // ***** 9 intersection points***** 
	    pln = new Plane(new Point3D(0, 0, -3), new Vector(0, -1, 4));
	    cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
	    assertEquals("Wrong number of points", 9, countIntersections(Nx,Ny,cam2,pln));
	    
	 // ***** 6 intersection points***** 
	    pln = new Plane(new Point3D(0, 0, -3.5), new Vector(0, -3, -0.5));
	    cam2.setDistance(1).setViewPlaneSize(3, 3); //set view plane and distance
	    assertEquals("Wrong number of points", 6, countIntersections(Nx,Ny,cam2,pln));
	}

	//Test cases for Triangle
	@Test
	public void cameraTriangleIntersections() {
		
		// ***** 1 intersection point*****
		Triangle tri = new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 1, -2));
		int Nx = 3;// pixels in row
		int Ny = 3;// pixels in column
		cam2.setDistance(1).setViewPlaneSize(3, 3);
		assertEquals("Wrong number of points", 1, countIntersections(Nx,Ny,cam2,tri));

		// ***** 2 intersection point*****
		tri = new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 20, -2));
		cam2.setDistance(1).setViewPlaneSize(3, 3);
		assertEquals("Wrong number of points", 2, countIntersections(Nx,Ny,cam2,tri));
	}
}

/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886 
 *
 */
public class Geometries implements Intersectable {

	/**
	 * A container for Geometries (Intersectables)
	 **/
	private List<Intersectable> geometries = new ArrayList<>();
	
	/* ********* Constructors *********** */
	/**
	 * @param geometries
	 * return a list of geometries
	 **/
	public Geometries(Intersectable... geometries) {
		this.geometries.addAll(Arrays.asList(geometries)); //add to the end of list
	}
	
	
	/**
	 * a default constructor
	 * @param none
	 */
	public Geometries() {
		this.geometries = new ArrayList<>();
	}

	public void add(Intersectable... geometries) {
		this.geometries.addAll(Arrays.asList(geometries)); //add the new geometries to list
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		List<Point3D> intersections = null;

        for (Intersectable geo : geometries) { //run on list of geometries
            List<Point3D> otherIntersections = geo.findIntersections(ray); //find intersections of each geometry
            if (otherIntersections != null) {
                if (intersections == null) //if no intersections were inserted yet
                    intersections = new ArrayList<>(); //create a new ArrayList
                intersections.addAll(otherIntersections); //insert all intersections
            }
        }
        return intersections; //return the list of intersections
	}

}

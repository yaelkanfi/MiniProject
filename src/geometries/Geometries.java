/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
//import static geometries.Intersectable.GeoPoint;


/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886 
 *
 */
public class Geometries implements Intersectable {

	/**
	 * A container for Geometries (Intersectables)
	 **/
	private List<Intersectable> geometries = new LinkedList<>();
	protected Point3D leftUpperBackcorner = null;
	protected Point3D rightLowerFrontCorner = null;
	
	/* ********* Constructors *********** */
	/**
	 * @param geometries
	 * return a list of geometries
	 **/
	public Geometries(Intersectable... geometries) {
		setBox(geometries);
		this.geometries.addAll(Arrays.asList(geometries)); //add to the end of list
	}
	
	/**
	 * @return the leftUpperBackcorner
	 */
	public Point3D getLeftUpperBackcorner() {
		return leftUpperBackcorner;
	}

	/**
	 * @return the rightLowerFrontCorner
	 */
	public Point3D getRightLowerFrontCorner() {
		return rightLowerFrontCorner;
	}
	
	/**
	 * a default constructor
	 * @param none
	 */
	public Geometries() {
		this.geometries = new LinkedList<>();
		this.leftUpperBackcorner = null;
		this.rightLowerFrontCorner = null;
	}

	public void add(Intersectable... geometries) {
		setBox(geometries);
		this.geometries.addAll(Arrays.asList(geometries)); //add the new geometries to list
	}
	
	private void setBox(Intersectable[] geometries) {
		if (geometries.length <= 0) {
			return;
		}
		if(leftUpperBackcorner == null) {
			this.leftUpperBackcorner = new Point3D(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE);			
		}
		if (rightLowerFrontCorner == null) {
			this.rightLowerFrontCorner = new Point3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
		}
		double mostLeftCoordinate = this.getLeftUpperBackcorner().getX();
		double mostRightCoordinate =  this.getRightLowerFrontCorner().getX();
		double mostUpCoordinate = this.getLeftUpperBackcorner().getY();
		double mostDownCoordinate = this.getRightLowerFrontCorner().getY();
		double mostBackCoordinate = this.getLeftUpperBackcorner().getZ();
		double mostFrontCoordinate = this.getRightLowerFrontCorner().getZ();
		for (var geometry : geometries) {
			if (geometry.getLeftUpperBackcorner().getX() < mostLeftCoordinate) {
				mostLeftCoordinate = geometry.getLeftUpperBackcorner().getX();
			}
			if (geometry.getLeftUpperBackcorner().getY() > mostUpCoordinate) {
				mostUpCoordinate = geometry.getLeftUpperBackcorner().getY();
			}
			if (geometry.getLeftUpperBackcorner().getZ() < mostBackCoordinate) {
				mostBackCoordinate = geometry.getLeftUpperBackcorner().getZ();
			}
			if (geometry.getRightLowerFrontCorner().getX() > mostRightCoordinate) {
				mostRightCoordinate = geometry.getRightLowerFrontCorner().getX();
			}
			if (geometry.getRightLowerFrontCorner().getY() < mostDownCoordinate) {
				mostDownCoordinate = geometry.getRightLowerFrontCorner().getY();
			}
			if (geometry.getRightLowerFrontCorner().getZ() > mostFrontCoordinate) {
				mostFrontCoordinate = geometry.getRightLowerFrontCorner().getZ();
			}
		}
		if (mostLeftCoordinate < leftUpperBackcorner.getX() || mostUpCoordinate > leftUpperBackcorner.getY() ||
				mostBackCoordinate < leftUpperBackcorner.getZ()) {
			this.leftUpperBackcorner = new Point3D(mostLeftCoordinate, mostUpCoordinate, mostBackCoordinate);
		}
		if (mostRightCoordinate > rightLowerFrontCorner.getX() || mostDownCoordinate < rightLowerFrontCorner.getY() ||
				mostFrontCoordinate > rightLowerFrontCorner.getZ()) {
			this.rightLowerFrontCorner = new Point3D(mostRightCoordinate, mostDownCoordinate, mostFrontCoordinate);	
		}
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
    /**
     * @param Ray ray - the ray that intersect the geometries
     * @return List<GeoPoint> - list of intersections geoPoints
     */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		if (!rayPassesInBox(ray)) { //check if ray passes the box
			return null; //if not -return null
		}
		List<GeoPoint> intersections = null; //list of GeoIntersection
		for (Intersectable geometry : geometries) { // Goes over the list of geometries
			if (geometry instanceof Geometry && !((Geometry)geometry).rayPassesInBox(ray)) { //if  Geometry is not passes the box
				continue;
			}
			if (geometry instanceof Geometries && !((Geometries)geometry).rayPassesInBox(ray)) { //if Geometries is not passes the box
				continue;
			}
		    var geoIntersections = geometry.findGeoIntersections(ray);//find geo intersection
			if(geoIntersections != null) //if the list is not empty
			  {
				if (intersections == null) //if no intersections were inserted yet
	                intersections = new ArrayList<>(); //create a new empty ArrayList
				intersections.addAll(geoIntersections); //insert all intersections
			  }
		}
		return intersections;// list of intersection geo point
	}
	/**The function checks if the ray is intersection within the box areas. 
	 * Our method is to extend the ray until it is intersection
	 *  and then check if the intersection is in the area of ​​the box.
	 * At first we checked edge cases where there is no intersections at all
	 * 
	 * @param ray the ray to check
	 * @return boolean value-if the ray passes the box return true
	 */
	public boolean rayPassesInBox(Ray ray) {
		//If the ray starts to the right of the box and turns to the right - then there is no intersection with the box
		if (ray.getP0().getX() > getRightLowerFrontCorner().getX() && ray.getDir().getHead().getX() >= 0) {
			return false;
		}
		//If the ray starts to the left of the box and turns to the left - then there is no intersection with the box
		if (ray.getP0().getX() < getLeftUpperBackcorner().getX() && ray.getDir().getHead().getX() <= 0) {
			return false;
		}
		//If the ray starts higher than the box and faces up - then there is no intersection with the box
		if (ray.getP0().getY() > getLeftUpperBackcorner().getY() && ray.getDir().getHead().getY() >= 0) {
			return false;
		}
		//If the ray starts lower than the box and faces down - then there is no intersection with the box
		if (ray.getP0().getY() < getRightLowerFrontCorner().getY() && ray.getDir().getHead().getY() <= 0) {
			return false;
		}
		//If the ray starts further forward from the box and turns forward - then there is no intersection with the box
		if (ray.getP0().getZ() > getRightLowerFrontCorner().getZ() && ray.getDir().getHead().getZ() >= 0) {
			return false;
		}
		//If the ray starts behind the box and turns backwards - then there is no intersection with the box
		if (ray.getP0().getZ() < getLeftUpperBackcorner().getZ() && ray.getDir().getHead().getZ() <= 0) {
			return false;
		}
		// left wall
		//Check if X is different from 0, otherwise X cannot be divided
		if (ray.getDir().getHead().getX() != 0) {
			//X0+X.dir*length=X1 ==> length= (X1-X0)/X.dir
			double length = (getLeftUpperBackcorner().getX() - ray.getP0().getX()) / ray.getDir().getHead().getX();
			Point3D p = ray.getPoint(length); //p is point that should go in the box
			//check if p  is located in the box dimensions
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // right wall
		//Check if X is different from 0, otherwise X cannot be divided
		if (ray.getDir().getHead().getX() != 0) {
			//X0+X.dir*length=X1 ==> length= (X1-X0)/X.dir
			double length = (getRightLowerFrontCorner().getX() - ray.getP0().getX()) / ray.getDir().getHead().getX();
			Point3D p = ray.getPoint(length);//p is point that should go in the box
			//check if p  is located in the box dimensions
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // lower wall
		//Check if Y is different from 0, otherwise Y cannot be divided
		if (ray.getDir().getHead().getY() != 0) {
			//Y0+Y.dir*length=Y1 ==> length= (Y1-Y0)/Y.dir
			double length = (getRightLowerFrontCorner().getY() - ray.getP0().getY()) / ray.getDir().getHead().getY();
			Point3D p = ray.getPoint(length);//p is point that should go in the box
			//check if p  is located in the box dimensions			
			if (p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // upper wall
		//Check if Y is different from 0, otherwise Y cannot be divided
		if (ray.getDir().getHead().getY() != 0) {
			//Y0+Y.dir*length=Y1 ==> length= (Y1-Y0)/Y.dir
			double length = (getLeftUpperBackcorner().getY() - ray.getP0().getY()) / ray.getDir().getHead().getY();
			Point3D p = ray.getPoint(length);//p is point that should go in the box
			//check if p  is located in the box dimensions	
			if (p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // back wall
		//Check if Z is different from 0, otherwise Z cannot be divided
		if (ray.getDir().getHead().getZ() != 0) {
			//Z0+Z.dir*length=Z1 ==> length= (Z1-Z0)/Z.dir
			double length = (getLeftUpperBackcorner().getZ() - ray.getP0().getZ()) / ray.getDir().getHead().getZ();
			Point3D p = ray.getPoint(length);//p is point that should go in the box
			//check if p  is located in the box dimensions
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX()) {
				return true;
			}
		}
		//front wall
		//Check if Z is different from 0, otherwise Z cannot be divided
		if (ray.getDir().getHead().getZ() != 0) {
			//Z0+Z.dir*length=Z1 ==> length= (Z1-Z0)/Z.dir
			double length = (getRightLowerFrontCorner().getZ() - ray.getP0().getZ()) / ray.getDir().getHead().getZ();
			Point3D p = ray.getPoint(length);//p is point that should go in the box
			//check if p  is located in the box dimensions
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX()) {
				return true;
			}
		}
		return false;
	}
}

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
import primitives.Vector;


/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886 
 *
 */
public class Geometries implements Intersectable {

	/**
	 * A container for Geometries (Intersectables)
	 **/
	private List<Intersectable> geometries = new LinkedList<>();
	protected Point3D leftUpperBackcorner = null; //left upper back corner of box, null is default
	protected Point3D rightLowerFrontCorner = null; //right lower front corner of box, null is default
	
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
	 * getter for leftUpperBackcorner
	 * @return the leftUpperBackcorner
	 */
	public Point3D getLeftUpperBackCorner() {
		return leftUpperBackcorner;
	}

	/**
	 * getter for rightLowerFrontCorner
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
		setBox(geometries); //set a new box for the new geometries
		this.geometries.addAll(Arrays.asList(geometries)); //add the new geometries to list
	}
	
	/**
	 * the function calculate the new box values, comparing to the old one
	 * @param geometries - array of geometries
	 */
	private void setBox(Intersectable[] geometries) {
		if (geometries.length <= 0) { 
			return; //no need of a new box because no geometries
		}
		if(leftUpperBackcorner == null) { //if there is no box yet, initialize with maximum values
			this.leftUpperBackcorner = new Point3D(Double.MAX_VALUE, -Double.MAX_VALUE, Double.MAX_VALUE);			
		}
		if (rightLowerFrontCorner == null) {//if there is no box yet, initialize with minimum values
			this.rightLowerFrontCorner = new Point3D(-Double.MAX_VALUE, Double.MAX_VALUE, -Double.MAX_VALUE);
		}
		// the maximum and minimum edges values of box according to the new geometries
		double mostLeftCoordinate = this.getLeftUpperBackCorner().getX(); //most left
		double mostRightCoordinate =  this.getRightLowerFrontCorner().getX(); //most right
		double mostUpCoordinate = this.getLeftUpperBackCorner().getY(); //most up
		double mostDownCoordinate = this.getRightLowerFrontCorner().getY(); //most down
		double mostBackCoordinate = this.getLeftUpperBackCorner().getZ(); //most back
		double mostFrontCoordinate = this.getRightLowerFrontCorner().getZ(); //most front
		
		for (var geometry : geometries) { //run over the geometries
			if (geometry.getLeftUpperBackCorner().getX() < mostLeftCoordinate) { //find the mostLeftCoordinate
				mostLeftCoordinate = geometry.getLeftUpperBackCorner().getX(); //update
			}
			if (geometry.getLeftUpperBackCorner().getY() > mostUpCoordinate) {//find the mostUpCoordinate
				mostUpCoordinate = geometry.getLeftUpperBackCorner().getY(); //update
			}
			if (geometry.getLeftUpperBackCorner().getZ() < mostBackCoordinate) {//find the mostBackCoordinate
				mostBackCoordinate = geometry.getLeftUpperBackCorner().getZ(); //update
			}
			if (geometry.getRightLowerFrontCorner().getX() > mostRightCoordinate) {//find the mostRightCoordinate
				mostRightCoordinate = geometry.getRightLowerFrontCorner().getX(); //update
			}
			if (geometry.getRightLowerFrontCorner().getY() < mostDownCoordinate) {//find the mostDownCoordinate
				mostDownCoordinate = geometry.getRightLowerFrontCorner().getY(); //update
			}
			if (geometry.getRightLowerFrontCorner().getZ() > mostFrontCoordinate) {//find the mostFrontCoordinate
				mostFrontCoordinate = geometry.getRightLowerFrontCorner().getZ(); //update
			}
		}
		
		//initialize the new box
		if (mostLeftCoordinate < leftUpperBackcorner.getX() || mostUpCoordinate > leftUpperBackcorner.getY() ||
				mostBackCoordinate < leftUpperBackcorner.getZ()) {
			this.leftUpperBackcorner = new Point3D(mostLeftCoordinate, mostUpCoordinate, mostBackCoordinate); //update
		}
		if (mostRightCoordinate > rightLowerFrontCorner.getX() || mostDownCoordinate < rightLowerFrontCorner.getY() ||
				mostFrontCoordinate > rightLowerFrontCorner.getZ()) {
			this.rightLowerFrontCorner = new Point3D(mostRightCoordinate, mostDownCoordinate, mostFrontCoordinate);	//update
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
	 * @param ray the ray to check
	 * @return boolean value-if the ray passes the box return true
	 */
	public boolean rayPassesInBox(Ray ray) {
		//If the ray starts to the right of the box and turns to the right - then there is no intersection with the box
		if (ray.getP0().getX() > getRightLowerFrontCorner().getX() && ray.getDir().getHead().getX() >= 0) {
			return false;
		}
		//If the ray starts to the left of the box and turns to the left - then there is no intersection with the box
		if (ray.getP0().getX() < getLeftUpperBackCorner().getX() && ray.getDir().getHead().getX() <= 0) {
			return false;
		}
		//If the ray starts higher than the box and faces up - then there is no intersection with the box
		if (ray.getP0().getY() > getLeftUpperBackCorner().getY() && ray.getDir().getHead().getY() >= 0) {
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
		if (ray.getP0().getZ() < getLeftUpperBackCorner().getZ() && ray.getDir().getHead().getZ() <= 0) {
			return false;
		}
		// left wall
		//Check if X is different from 0, otherwise X cannot be divided
		if (ray.getDir().getHead().getX() != 0) {
			//X0+X.dir*length=X1 ==> length= (X1-X0)/X.dir
			double length = (getLeftUpperBackCorner().getX() - ray.getP0().getX()) / ray.getDir().getHead().getX();
			Point3D p = ray.getPoint(length); //p is point that should go in the box
			//check if p  is located in the box dimensions
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} 
		// right wall
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
		}
		// lower wall
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
		} 
		// upper wall
		//Check if Y is different from 0, otherwise Y cannot be divided
		if (ray.getDir().getHead().getY() != 0) {
			//Y0+Y.dir*length=Y1 ==> length= (Y1-Y0)/Y.dir
			double length = (getLeftUpperBackCorner().getY() - ray.getP0().getY()) / ray.getDir().getHead().getY();
			Point3D p = ray.getPoint(length);//p is point that should go in the box
			//check if p  is located in the box dimensions	
			if (p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} 
		// back wall
		//Check if Z is different from 0, otherwise Z cannot be divided
		if (ray.getDir().getHead().getZ() != 0) {
			//Z0+Z.dir*length=Z1 ==> length= (Z1-Z0)/Z.dir
			double length = (getLeftUpperBackCorner().getZ() - ray.getP0().getZ()) / ray.getDir().getHead().getZ();
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
	
	/**
	 * This function gets two intersectables and checks if they are close enough, if so returns true
	 * @param first - Intersectable
	 * @param second - Intersectable
	 * @return boolean - return true if the intersectables are close enough
	 */
	private boolean isCloseEnough(Intersectable first, Intersectable second) {
		Vector firstDiagonal = first.getLeftUpperBackCorner().subtract(first.getRightLowerFrontCorner()); //calculate the first diagonal of box (left - right)
		Vector secondDiagonal = second.getLeftUpperBackCorner().subtract(second.getRightLowerFrontCorner()); //calculate the second diagonal of box (left - right)
		double smallLength  = Math.min(firstDiagonal.length(), secondDiagonal.length()); //find the smaller diagonal length
		double distance = first.getRightLowerFrontCorner().add(firstDiagonal.scale(0.5))
				.distance(second.getRightLowerFrontCorner().add(secondDiagonal.scale(0.5))); //the distance between the middle of the boxes
		
		return distance < smallLength * 2; //if the distance is smaller than smallLength * 2 -> return true, the intersectables are close enough
	}
	
	/**
	 * This function goes over the geometries and checks if there are close enough geometries in order to unite them into a box, 
	 * then creates a box for them and updates the geometries
	 */
	public void sortInnerGeometries( ) {
		for (int i = 0; i < geometries.size(); i++) { //run over geometries
			List<Intersectable> closeToI = new ArrayList<>(); //new array list for the close enough geometries to the geometry in the i index
			for (int j = i + 1; j < geometries.size(); j++) { //run over the other geometries
				if(isCloseEnough( geometries.get(i),  geometries.get(j))) { //check is the geometry is close enough, if so add to the list
					closeToI.add(geometries.get(j));
				}
			}
			if (closeToI.size() > 0) {	//if the list is not empty			
				closeToI.add(geometries.get(i)); //add the geometry in index i to the list
				for (var box : closeToI) { //instead of having each geometry, keep a box that contains all of them
					geometries.remove(box); //remove each geometry that in the new box
				}
				//move into array
				Intersectable[] close = new Intersectable[closeToI.size()];
				closeToI.toArray(close);
				
				Geometries newBox = new Geometries(close); //create a new box with close (array) of geometries that are close enough, by using the constructor
				geometries.add(newBox); //add the new box to the geometries list
				i--; //Because we removed some of the geometries,
				//the indexes of the geometries in the list have changed (minus 1) and now we want to go over the geometry in index i
			}			
		}
		
	}

	@Override
	public void setBox() {
		// TODO Auto-generated method stub
		
	}
}

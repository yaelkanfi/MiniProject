/**
 * 
 */
package geometries;

import primitives.Vector;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public abstract class Geometry implements Intersectable
{
	protected Color emmission = Color.BLACK;
	private Material material = new Material();
	protected Point3D leftUpperBackcorner; //left Upper Back corner of box
	protected Point3D rightLowerFrontCorner; //right Lower Front Corner of box
	
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

	/** getter for emmission 
	 * @return the emmission (color)
	 */
	public Color getEmmission() {
		return emmission;
	}

	/**
	 * @param emmission the emmission to set
	 * @return this object- geometry
	 */
	public Geometry setEmmission(Color emmission) {
		this.emmission = emmission;
		return this;
	}

	
	/** getter that return material
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**setter that return Geometry
	 * @param material the material to set
	 * @return Geometry
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * @param p point3D  (point)
	 * @return  the normal of point
	 */
	public abstract Vector get_Normal(Point3D p);
	
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
	
}

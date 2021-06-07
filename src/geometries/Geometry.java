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
	protected Color emmission =Color.BLACK;
	private Material material=new Material();
	protected Point3D leftUpperBackcorner;
	protected Point3D rightLowerFrontCorner;
	
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
	 *
	 * @param p point3D  (point)
	 * @return  the normal of point
	 */
	public abstract Vector get_Normal(Point3D p);
	
	public boolean rayPassesInBox(Ray ray) {
		if (ray.getP0().getX() > getRightLowerFrontCorner().getX() && ray.getDir().getHead().getX() >= 0) {
			return false;
		}
		if (ray.getP0().getX() < getLeftUpperBackcorner().getX() && ray.getDir().getHead().getX() <= 0) {
			return false;
		}
		if (ray.getP0().getY() > getLeftUpperBackcorner().getY() && ray.getDir().getHead().getY() >= 0) {
			return false;
		}
		if (ray.getP0().getY() < getRightLowerFrontCorner().getY() && ray.getDir().getHead().getY() <= 0) {
			return false;
		}
		if (ray.getP0().getZ() > getRightLowerFrontCorner().getZ() && ray.getDir().getHead().getZ() >= 0) {
			return false;
		}
		if (ray.getP0().getZ() < getLeftUpperBackcorner().getZ() && ray.getDir().getHead().getZ() <= 0) {
			return false;
		}
		// left wall
		if (ray.getDir().getHead().getX() != 0) {
			double length = (getLeftUpperBackcorner().getX() - ray.getP0().getX()) / ray.getDir().getHead().getX();
			Point3D p = ray.getPoint(length);
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // right wall
		if (ray.getDir().getHead().getX() != 0) {
			double length = (getRightLowerFrontCorner().getX() - ray.getP0().getX()) / ray.getDir().getHead().getX();
			Point3D p = ray.getPoint(length);
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // lower wall
		if (ray.getDir().getHead().getY() != 0) {
			double length = (getRightLowerFrontCorner().getY() - ray.getP0().getY()) / ray.getDir().getHead().getY();
			Point3D p = ray.getPoint(length);
			if (p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // upper wall
		if (ray.getDir().getHead().getY() != 0) {
			double length = (getLeftUpperBackcorner().getY() - ray.getP0().getY()) / ray.getDir().getHead().getY();
			Point3D p = ray.getPoint(length);
			if (p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX() &&
					p.getZ() > this.leftUpperBackcorner.getZ() && p.getZ() < this.rightLowerFrontCorner.getZ()) {
				return true;
			}
		} // back wall
		if (ray.getDir().getHead().getZ() != 0) {
			double length = (getLeftUpperBackcorner().getZ() - ray.getP0().getZ()) / ray.getDir().getHead().getZ();
			Point3D p = ray.getPoint(length);
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX()) {
				return true;
			}
		}
		if (ray.getDir().getHead().getZ() != 0) {
			double length = (getRightLowerFrontCorner().getZ() - ray.getP0().getZ()) / ray.getDir().getHead().getZ();
			Point3D p = ray.getPoint(length);
			if (p.getY() > this.rightLowerFrontCorner.getY() && p.getY() < this.leftUpperBackcorner.getY() &&
					p.getX() > this.leftUpperBackcorner.getX() && p.getX() < this.rightLowerFrontCorner.getX()) {
				return true;
			}
		}
		return false;
	}
	
}

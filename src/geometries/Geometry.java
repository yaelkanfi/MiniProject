/**
 * 
 */
package geometries;

import primitives.Vector;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public abstract class Geometry implements Intersectable
{
	protected Color emmission =Color.BLACK;
	private Material material=new Material();
	
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
	
}

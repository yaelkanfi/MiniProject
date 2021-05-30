/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Yael and Chagit
 *
 */
public class PointLight extends Light implements LightSource {
	
	private Point3D position;
	
	private double radius = 0;


	private double kC=1; //factor for attenuation with distance
	private double kL=0; //factor for attenuation with distance
	private double kQ=0; //factor for attenuation with distance
	/**
	 * constructor that using field
	 * @param intensity color
	 * @param position point3D
	 * @param kC double factor for attenuation with distance
	 * @param kL double factor for attenuation with distance
	 * @param kQ double factor for attenuation with distance
	 * 
	 */
	public PointLight(Color intensity, Point3D position, double kC , double kL, double kQ)
	{
		super(intensity);
		this.position = position;
		this.kC = kC;
		this.kL = kL;
		this.kQ = kQ;
	}
	

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public PointLight setRadius(double radius) {
		this.radius = radius;
		return this;
	}
	
	/**
	 * @return the position
	 */
	public Point3D getPosition() {
		return position;
	}
	
	/**
	 * @param kC the kC to set
	 * @return PointLight
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}


	/** setter that return PointLight
	 * @param kL the kL to set
	 * @return PointLight
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}


	/** setter that return PointLight
	 * @param kQ the kQ to set
	 * @return PointLight
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	/**
	 * @param p- Point3D 
	 */
	public Color getIntensity(Point3D p) {
		// TODO Auto-generated method stub
		double distanceSquared=p.distanceSquared(position);//d^2
		double distance=p.distance(position);//d
		return getIntensity().scale(1 / (kC + kL * distance + kQ * distanceSquared));
	}
	@Override
	public Vector getL(Point3D p) {
		// TODO Auto-generated method stub
		if (p.equals(position)) {//check if p==position ,because vector zero is bad
			return null;
		}
		return p.subtract(position).normalized();//return the normalized vector of (p-position)
	}


	@Override
	public double getDistance(Point3D point) {
		// TODO Auto-generated method stub
		return position.distance(point);
	}
	

}

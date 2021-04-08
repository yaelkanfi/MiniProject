/**
 * 
 */
package elements;

import primitives.Point3D;
import primitives.Ray;

import static primitives.Util.*;
import primitives.Vector;

/**Camera class
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886 
 *
 */
public class Camera {
	
	//private fields
	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
    private double width;
    private double height;
    private double distance;
	/**
	 * @return the p0
	 */
	public Point3D getP0() {
		return p0;
	}
	/**
	 * @return the vUp
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * @return the vTo
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * @return the vRight
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/** constructor -The constructor gets 2 vectors and point, checks that the vectors are vertical and initializes them and their normal vector-vRight
	 * @param p0 point
	 * @param vUp vector
	 * @param vTo vector
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp)throws IllegalArgumentException  {
		super();
		if (!isZero(vUp.dotProduct(vTo))) {
			throw new IllegalArgumentException("Error, cannot create Camera, vUp and vTo are not vertical");
		}
		this.p0 = p0;
		this.vUp = vUp.normalized();
		this.vTo = vTo.normalized();
		/*this.width = width;
		this.height = height;
		this.distance = distance;
		*/
		this.vRight =(vTo.crossProduct(vUp)).normalize();
	}
	/**
	 * this function initializes view plane 
	 * @param width
	 * @param height
	 * @return Camera
	 */
	public Camera setViewPlaneSize(double width, double height)
	{
		this.width=width;
		this.height=height;
		return this;
	}
	/**
	 * this function initializes Camera distance from view plane 
	 * @param distance
	 * @return Camera
	 */
	public Camera setDistance(double distance)
	{
		this.distance=distance;
		return this;
	}
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i)
	{
		//Image center
		if (isZero(distance)) {
			throw new IllegalArgumentException("distance can not be 0");
		}
		Point3D pc = this.p0.add(this.vTo.scale((this.distance))); //p0 + d*vto
		
		//Ratio (pixel width & height)
		if (isZero(nY)) {
			throw new IllegalArgumentException("can not divide in 0");
		}
		if (isZero(nX)) {
			throw new IllegalArgumentException("can not divide in 0");
		}
		double Ry = this.height/nY;
		double Rx = this.width/nX;
		
		//Pixel[i,j] center
		double yi = -(i - (nY - 1) / 2d) * Ry;
		double xj = (j - (nX - 1) / 2d) * Rx;
		
		Point3D Pij = pc;

		if (!isZero(xj)) {
			Pij = Pij.add(vRight.scale(xj));
		}
		if (!isZero(yi)) {
			Pij = Pij.add(vUp.scale(yi));
		}
		
		Vector Vij = Pij.subtract(this.p0);
		
		return new Ray(this.p0, Vij);
	}
}

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
	private Point3D p0; //start point
	private Vector vUp; //vector from camera
	private Vector vTo;//vector from camera
	private Vector vRight;//vector from camera
    private double width; //width of view plane
    private double height;//height of view plane
    private double distance;//distance between camera and view plane
	/**
	 * @return the p0 //start point
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
	 * @param p0 start point
	 * @param vUp vector
	 * @param vTo vector
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp)throws IllegalArgumentException  {
		super();
		if (!isZero(vUp.dotProduct(vTo))) //if there are no vertical throw exception
		{
			throw new IllegalArgumentException("Error, cannot create Camera, vUp and vTo are not vertical");
		}
		this.p0 = p0;
		this.vUp = vUp.normalized();
		this.vTo = vTo.normalized();
		/*this.width = width;
		this.height = height;
		this.distance = distance;
		*/
		this.vRight =vTo.crossProduct(vUp).normalize(); //the normal vector
	}
	/**
	 * this function initializes view plane 
	 * @param width -width of view plane
	 * @param height -height of of view plane
	 * @return Camera -this object
	 */
	public Camera setViewPlaneSize(double width, double height)
	{
		this.width=width;
		this.height=height;
		return this;
	}
	/**
	 * this function initializes Camera distance from view plane 
	 * @param distance- distance between camera and view plane
	 * @return Camera-this object
	 */
	public Camera setDistance(double distance)
	{
		this.distance=distance;
		return this;
	}
	/**
	 * the function construct ray through pixel
	 * @param nX -amount of column
	 * @param nY -amount of row
	 * @param j- column of pixel
	 * @param i- row of pixel
	 * @return ray- the constructed ray through the pixel
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i)
	{
		//Image center
		if (isZero(distance)) //if distance is zero, throw exception
		{
			throw new IllegalArgumentException("distance can not be 0");
		}
		Point3D pc = this.p0.add(this.vTo.scale((this.distance))); //p0 + distance*vto
		
		//Ratio (pixel width & height)
		if (isZero(nY)) //if columns is zero, throw exception
		{
			throw new IllegalArgumentException("can not divide in 0");
		}
		if (isZero(nX)) //if rows is zero, throw exception
		{
			throw new IllegalArgumentException("can not divide in 0");
		}
		double Ry = this.height/nY; //height of each pixel
		double Rx = this.width/nX;//width of each pixel
		
		//Pixel[i,j] center
		double yi = (i - (nY - 1) / 2d) * Ry;
		double xj = (j - (nX - 1) / 2d) * Rx;
		
		Point3D Pij = pc;

		if (!isZero(xj))// if is not zero,add 
		{
			Pij = Pij.add(vRight.scale(xj));
		}
		if (!isZero(yi))// if is not zero,add 
		{
			Pij = Pij.add(vUp.scale(-yi));
		}
		
		Vector Vij = Pij.subtract(this.p0); //vij=pij-p0
		
		return new Ray(this.p0, Vij); //return ray
	}
}

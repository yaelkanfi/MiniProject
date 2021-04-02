/**
 * 
 */
package primitives;

import java.util.Objects;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Point3D 
{
	
	final Coordinate x; //x
	final Coordinate y; //y
	final Coordinate z; //z
	public static Point3D ZERO = new Point3D(0,0,0); //zero static
	
	/**
	 * constructor that gets three values of coordinates and returns a Point3D with x, y, z coordinates
	 * @param x  x value
	 * @param y  y value
	 * @param z  z value
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z)
	{
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * constructor that gets 3 double values and sets the coordinates  x, y and z values
	 * @param x  x value
	 * @param y  y value
	 * @param z  z value
	 */
	public Point3D(double x, double y, double z) 
	{
		super();
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}
	
	/**
	 * subtract between headPoint to this Point and returns the vector
	 * @param headPoint Point3D
	 * @return Vector
	 */
    public Vector subtract(Point3D headPoint)
    {
    	double newX = x.coord - headPoint.x.coord;
    	double newY = y.coord - headPoint.y.coord;
    	double newZ = z.coord - headPoint.z.coord;
    	return new Vector(newX, newY, newZ);
    }
	
    /**
     * add a vector to this point and returns a Point3D
     * @param vector Vector
     * @return Point3D
     */
	public Point3D add(Vector vector)
	{
		double newX = x.coord + vector.getHead().x.coord;
		double newY = y.coord + vector.getHead().y.coord;
		double newZ = z.coord + vector.getHead().z.coord;
		return new Point3D(newX, newY, newZ);
	}
	
	/**
	 * returns the distance Squared between two points3D
	 * @param point Point3D
	 * @return double distanceSquared
	 */
	public double distanceSquared(Point3D point)
	{
		double newX = x.coord - point.x.coord;
		double newY = y.coord - point.y.coord;
		double newZ = z.coord - point.z.coord;
		return newX*newX + newY*newY + newZ*newZ;
	}

	/**
	 * returns the distance between two points3D
	 * @param point Point3D
	 * @return double distance
	 */
	public double distance(Point3D point)
	{
		return Math.sqrt(this.distanceSquared(point));
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D other = (Point3D) obj;
		return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
	}
	
	@Override
	public String toString() 
	{
		return "[" + (x != null ?  x + ", " : "") + (y != null ? y + ", " : "")
				+ (z != null ?  z : "") + "]";
    }

}


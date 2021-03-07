/**
 * 
 */
package primitives;

import java.util.Objects;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Vector {
	
	Point3D head; //head point

	
	/**
	 * constructor that gets 3 Coordinates and returns Vector
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @param z Coordinate z
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) throws IllegalArgumentException {
		super();
		Point3D point = new Point3D(x, y, z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("ERROR,this vector can't be zero vector");
		head=point;
	}
	
	/**
	 * constructor that gets 3 double values and returns Vector
	 * @param x double value
	 * @param y double value
	 * @param z double value
	 */
	public Vector(double x, double y, double z) throws IllegalArgumentException {
		super();
		Point3D point = new Point3D(x, y, z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("ERROR,this vector can't be zero vector");
		head=point;
	}


	/**
	 * constructor that gets Point3D and returns Vector
	 * @param head Point3D
	 */
	public Vector(Point3D head) {
		super();
		this.head = head;
	}
	
     /**
	 * subtract between Vector to this Vector and returns the new vector
	 * @param vector - other Vector
	 * @return Vector- new vector after subtract
	 */
	public Vector subtract(Vector vector)
	{
		return this.head.subtract(vector.head);
	}
	/**
	 * add a other vector to this vector and returns a new vector
	 * @param vector- other vector
	 * @return new vector after adding
	 */
	public Vector add(Vector vector)
	{
		 return new Vector(this.head.add(vector));
	}
	
	/**
	 * The function receives scalar multiplication the vector in the scalar and returns the new vector
	 * @param scaleValue double value
	 * @return new vector 
	 */
	public Vector scale(double scaleValue)
	{
		return (new Vector((this.head.x.coord)*scaleValue, (this.head.y.coord)*scaleValue,(this.head.z.coord)*scaleValue));
	}
	
	/**
	 *function that receives a vector and returns its scalar product
	 * @param vector other vector
	 * @return scalar
	 */
    public double dotProduct(Vector vector)
    {
	return (this.head.x.coord*vector.head.x.coord+this.head.y.coord*vector.head.y.coord+this.head.z.coord*vector.head.z.coord);
    }
    /**
     * A function that acts on a vector and receives another vector and returns their cross product
     * @param vector other vector
     * @return new vector
     */
    public Vector crossProduct(Vector vector)
    {
    	return new Vector(this.head.y.coord*vector.head.z.coord- this.head.z.coord*vector.head.y.coord,this.head.z.coord*vector.head.x.coord-this.head.x.coord*vector.head.z.coord, this.head.x.coord*vector.head.y.coord-this.head.y.coord*vector.head.x.coord );
    }
   /**
    * The function calculates the length of the vector squared
    * @return length of vector Squared
    */
    public double lengthSquared() 
    {
    	return this.dotProduct(this);
    }
    /**
     * The function calculates the length of the vector
     * @return length of the vector
     */
    public double length()
    {
    	return Math.sqrt(this.lengthSquared());
    }
    /** 
     * The function normalizes the vector and returns it after normalization
     * @return The Normalized Vector
     */
    public Vector normalize()
    {
    	double scalar=1/this.length();
    	this.head =this.scale(scalar).head;
    	return this;
    }
    /**
     * The function normalizes the vector and returns it after normalization with the same direction
     * @return  new Normalized  Vector
     */
    public Vector normalized()
    {
    	return new Vector(this.head).normalize();
    }

	@Override
	public String toString()
	{
		return "Vector " + head.toString();	

	}

	@Override
	public int hashCode() {
		return Objects.hash(head);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return head.equals(other.head);
	}

	/** the function return the vector
	 * @return the head vector
	 */
	public Point3D getHead() {
		// TODO Auto-generated method stub
		return head;
	}
	
    
    
}

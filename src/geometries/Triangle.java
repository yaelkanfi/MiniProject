/**
 * 
 */
package geometries;
import static primitives.Util.*;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Chagit Shaviv 322805482 and Yael Kanfi 212450886
 *
 */
public class Triangle extends Polygon 
{

	/**
	 * constructor that get 3 points and return a triangle
	 * @param p1 Point3D Vertex
	 * @param p2 Point3D Vertex
	 * @param p3 Point3D Vertex
	 * @throws IllegalArgumentException
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) throws IllegalArgumentException {
		super(p1, p2, p3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector get_Normal(Point3D point) {
		// TODO Auto-generated method stub
		return super.get_Normal(point);
	}
/**
 * The function returns intersections between a ray and a triangle. If there are no intersection points the function returns null
 */
	/*@Override
	/*public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		 List<Point3D> intersections = plane.findIntersections(ray);
         if (intersections == null) return null;

         Point3D rayPoint = ray.getP0();
         Vector rayVector = ray.getDir();

         Vector v1 = vertices.get(0).subtract(rayPoint); //vertex v1
         Vector v2 = vertices.get(1).subtract(rayPoint); //vertex v2
         Vector v3 = vertices.get(2).subtract(rayPoint); //vertex v3

         Vector n1 = v1.crossProduct(v2).normalize();        
         Vector n2 = v2.crossProduct(v3).normalize();
         Vector n3 = v3.crossProduct(v1).normalize();
         double vN1=rayVector.dotProduct(n1); //rayVector*n1
         double vN2=rayVector.dotProduct(n2);//rayVector*n2
         double vN3=rayVector.dotProduct(n3);//rayVector*n3
         //check if all vNi are not zero
         if(isZero(vN1)||isZero(vN2)||isZero(vN3))
        	 return null;
        //check if all vNi have a same sign(-/+)	 
         if ((vN1 > 0 && vN2 > 0 && vN3 > 0) || (vN1 < 0 && vN2 < 0 && vN3 < 0)) {           
             return intersections;
         }
         else
         {
         	return null;
         }
		
	}*/

	@Override
	public String toString() {
		return "Triangle [vertices: " + super.vertices.toString() + "]";
	}
	
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		// TODO Auto-generated method stub
		 List<GeoPoint> intersections = plane.findGeoIntersections(ray);
         if (intersections == null) return null;

         Point3D rayPoint = ray.getP0();
         Vector rayVector = ray.getDir();

         Vector v1 = vertices.get(0).subtract(rayPoint); //vertex v1
         Vector v2 = vertices.get(1).subtract(rayPoint); //vertex v2
         Vector v3 = vertices.get(2).subtract(rayPoint); //vertex v3

         Vector n1 = v1.crossProduct(v2).normalize();        
         Vector n2 = v2.crossProduct(v3).normalize();
         Vector n3 = v3.crossProduct(v1).normalize();
         double vN1=rayVector.dotProduct(n1); //rayVector*n1
         double vN2=rayVector.dotProduct(n2);//rayVector*n2
         double vN3=rayVector.dotProduct(n3);//rayVector*n3
         //check if all vNi are not zero
         if(isZero(vN1)||isZero(vN2)||isZero(vN3))
        	 return null;
        //check if all vNi have a same sign(-/+)	 
         if ((vN1 > 0 && vN2 > 0 && vN3 > 0) || (vN1 < 0 && vN2 < 0 && vN3 < 0)) {           
        	 for (GeoPoint geo : intersections) {
 				geo.geometry = this;
 			}
 			return intersections;
         }
         else
         {
         	return null;
         }
		
	}
}
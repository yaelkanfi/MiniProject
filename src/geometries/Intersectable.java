/**
 * 
 */
package geometries;

import primitives.Ray;
import java.util.List;


import primitives.Point3D;

/**
 * @author Yael and Chagit ////
 *
 */
public interface Intersectable {

/**
 * 
 * @param ray
 * @return list of intersections with ray
 */
List<Point3D> findIntsersections(Ray ray);
}
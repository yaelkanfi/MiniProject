/**
 * 
 */
package geometries;

import primitives.Ray;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import primitives.Point3D;

/**
 * @author Yael and Chagit 
 *
 */
public interface Intersectable {


/*List<Point3D> findIntersections(Ray ray);

/**
 * a static class for geo point
 * a class with a point and a geometry
 * @author Yael and Chagit 
 *
 */
public static class GeoPoint {
public Geometry geometry; //the geometry
public Point3D point; //the point

/** constructor for geoPoint- initialize both fields
 * @param geometry - Geometry
 * @param point - Point3D
 */
public GeoPoint(Geometry geometry, Point3D point) {
	super();
	this.geometry = geometry;
	this.point = point;
}


@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	GeoPoint other = (GeoPoint) obj;// convert to GeoPoint
	return geometry == other.geometry && point.equals(other.point); //compare geometry and point
}
}

/**
 * the function finds the intersection points
 * @param ray
 * @return list of intersections with ray
 */
default List<Point3D> findIntersections(Ray ray) {
	List<GeoPoint> geoList = findGeoIntersections(ray);
	return geoList == null ? null
	: geoList .stream()
	.map(gp -> gp.point)
	.collect(Collectors.toList());
	}

/**
 * the function finds the intersection GeoPoints
 * @param ray
 * @return List<GeoPoint> - list of intersections point of type GeoPoint
 */
List<GeoPoint> findGeoIntersections(Ray ray);
Point3D getLeftUpperBackcorner();
Point3D getRightLowerFrontCorner();

}
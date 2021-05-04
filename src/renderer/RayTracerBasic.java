/**
 * 
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * @author Yael and Chagit
 *
 */
public class RayTracerBasic extends RayTracerBase
{
	/**
	 * calculate the scene color, using the scene's ambient light's intensity function
	 * @param point 
	 * @return the scene's ambient light
	 */
	private Color calcColor(GeoPoint intersection) {
		return scene.ambientLight.getIntensity().add(intersection.geometry.getEmmission());
		}
	
	/** an inheritance function from base
	 * this function returns the color of the closest point to the ray.
	 * @param ray -the ray between camera and view plane
	 * @return the color -color of closest point
	 */
	public Color traceRay(Ray ray)
	{
		var intersections = scene.geometries.findGeoIntersections(ray);//find intersection  GeoPoint
		if (intersections == null) // if there are no intersection points return color of background
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);// find closest GeoPoint between ray
		return calcColor(closestPoint); // return the color of closestGeoPoint
	}

	/**constructor that get scene and call to superClass constructor
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
		// TODO Auto-generated constructor stub
	}
	
}


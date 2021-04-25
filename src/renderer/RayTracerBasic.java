/**
 * 
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

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
	private Color calcColor(Point3D point)
	{
	  return scene.ambientLight.getIntensity();		
	}
	
	/** an inheritance function from base
	 * this function returns the color of the closest point to the ray.
	 * @param ray -the ray between camera and view plane
	 * @return the color -color of closest point
	 */
	public Color traceRay(Ray ray)
	{
		List<Point3D> intersections = scene.geometries.findIntersections(ray);//find intersection point
		if (intersections == null) // if there are no intersection points return color of background
			return scene.background;
		Point3D closestPoint = ray.findClosestPoint(intersections);// find closest point between ray
		return calcColor(closestPoint); // return the color of closestPoint
	}

	/**constructor that get scene and call to superClass constructor
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
		// TODO Auto-generated constructor stub
	}
	
}


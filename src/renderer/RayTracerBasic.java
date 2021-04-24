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
	
	/**
	 * this function return the color of the closest point to the ray.
	 * @param ray
	 * @return the color
	 */
	public Color traceRay(Ray ray)
	{
		List<Point3D> intersections = scene.geometries.findIntersections(ray);
		if (intersections == null) return scene.background;
		Point3D closestPoint = ray.findClosestPoint(intersections);
		return calcColor(closestPoint);
	}

	/**constructor that get scene and call to superClass constructor
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
		// TODO Auto-generated constructor stub
	}
	
}


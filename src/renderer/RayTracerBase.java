/**
 * 
 */
package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * @author Yael and Chagit
 *
 */
public abstract class RayTracerBase
{
	protected Scene scene;
	/**
	 * this function return the color of the closest point to the ray.
	 * @param ray
	 * @return the color
	 */
	public abstract Color traceRay(Ray ray);
	
	/**constructor that gets scene 
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		super();
		this.scene = scene;
	}
	
}

/**
 * 
 */
package renderer;

import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import static primitives.Util.*;
import primitives.Vector;
import scene.Scene;

/**
 * @author Yael and Chagit
 *
 */
public class RayTracerBasic extends RayTracerBase
{
	private static final double DELTA = 0.1; //Size of first moving rays
	/**
	 * calculate the scene color, using the scene's ambient light's intensity function
	 * @param point 
	 * @param ray 
	 * @return the scene's ambient light
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		Color color=scene.ambientLight.getIntensity().add(intersection.geometry.getEmmission());
		color = color.add(calcLocalEffects(intersection, ray));
		return color;
	}
	
	/**
	 * calc Local Effects
	 * @param intersection
	 * @param ray
	 * @return
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray)
	{
		Vector v = ray.getDir();
		Vector n = intersection.geometry.get_Normal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				if (unshaded(lightSource, l,n, intersection))
						{
						Color lightIntensity = lightSource.getIntensity(intersection.point);
						color = color.add(calcDiffusive(kd, nl, lightIntensity),
						calcSpecular(ks, l, n, nl,  v, nShininess, lightIntensity));
						}

			}
			}
				
		return color;
	}
	/**
	 * Calculate Specular component of light reflection.
	 *
	 * @param ks         specular component coef
	 * @param l          direction from light to point
	 * @param n          normal to surface at the point
	 * @param nl         dot-product n*l
	 * @param v          direction from point of view to point
	 * @param nShininess shininess level
	 * @param ip         light intensity at the point
	 * @return specular component light effect at the point
	 * @author Dan Zilberstein
	 *         <p>
	 *         Finally, the Phong model has a provision for a highlight, or
	 *         specular, component, which reflects light in a shiny way. This is
	 *         defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection
	 *         direction vector we discussed in class (and also used for ray
	 *         tracing), and where p is a specular power. The higher the value of p,
	 *         the shinier the surface.
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
		double p = nShininess;

		Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -Util.alignZero(R.dotProduct(v));
		if (minusVR <= 0) {
			return Color.BLACK; // view from direction opposite to r vector
		}
		return ip.scale(ks * Math.pow(minusVR, p));
	}
	/**
	 * Calculate Diffusive component of light reflection.
	 *
	 * @param kd diffusive component coef
	 * @param nl dot-product n*l
	 * @param ip light intensity at the point
	 * @return diffusive component of light reflection
	 * @author Dan Zilberstein
	 *         <p>
	 *         The diffuse component is that dot product n•L that we discussed in
	 *         class. It approximates light, originally from light source L,
	 *         reflecting from a surface which is diffuse, or non-glossy. One
	 *         example of a non-glossy surface is paper. In general, you'll also
	 *         want this to have a non-gray color value, so this term would in
	 *         general be a color defined as: [rd,gd,bd](n•L)
	 */
	private Color calcDiffusive(double kd, double nl, Color ip) {
		if (nl < 0)
			nl = -nl;
		return ip.scale(nl * kd);
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
		return calcColor(closestPoint, ray); // return the color of closestGeoPoint
	}

	/**constructor that get scene and call to superClass constructor
	 * @param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
		// TODO Auto-generated constructor stub
	}
	/**
	 * The function checks if there is shading or not between a point and the light source
	 * @param l vector
	 * @param n vector
	 * @param geopoint GeoPoint
	 * @return boolean value- true if there is no shading
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
		Point3D point = geopoint.point.add(delta);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return true;
		double lightDistance = light.getDistance(geopoint.point);
		for (GeoPoint gp : intersections) {
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0)
				return false;
				}
				return true;
				}

		

}


/**
 * 
 */
package renderer;

import java.util.List;

import elements.LightSource;
import elements.PointLight;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
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
	private static final double INITIAL_K = 1.0;
	private static final int MAX_CALC_COLOR_LEVEL = 10;//const variable for stop conditions in the recursion of transparencies / reflections
	private static final double MIN_CALC_COLOR_K = 0.001;//const variable for stop conditions in the recursion of transparencies / reflections

	/**
	 * calculate the scene color, using the scene's ambient light's intensity function
	 * @param geoPoint - geoPoint of intersection
	 * @param ray -ray of a center of pixel
	 * @return the color of the point by phong model + reflacted and refracted
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray)
	{
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}
	/**
	* calculate the color of a point, recursion
	* 
	* @param intersection - a point of intersection
	* @param ray          - ray of a center of pixel
	* @param level        - level of the recursion
	* @param k            - The multiplication factor of kR and kT
	 * @return local effect + global effect
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) 
	{
		Color color = intersection.geometry.getEmmission();
		color = color.add(calcLocalEffects(intersection, ray , k));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
		
	}

	
	/**
	 * calc Local Effects -diffusive and Specular
	 * @param intersection -GeoPoint intersection
	 * @param ray -Ray of a center of pixel
	 * @return calculate the part of phong model :( kd*|l*n| +ks*(-v*r)^nsh)*IL
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray , double k)
	{
		Vector v = ray.getDir();
		Vector n = intersection.geometry.get_Normal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights)
		{
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0)  // sign(nl) == sing(nv)
				{
					double ktr = transparency(lightSource, l, n, intersection);
					if (ktr * k > MIN_CALC_COLOR_K)
					{
						
						Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
						color = color.add(calcDiffusive(kd, nl, lightIntensity),
						calcSpecular(ks, l, n, nl,  v, nShininess, lightIntensity));
					}
						
				}
		}
					
		return color;
	}
	/**
	 * calculate Global Effects - reflected and refracted
     * @param geopoint - geopoint
     * @param ray  - Ray of a center of pixel
     * @param level  - level of the recursion
     * @param k   - The multiplication factor of kR and kT
     * @return color after global effect
     */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		if (level == 1 || k < MIN_CALC_COLOR_K) {
			return Color.BLACK;
		}
		Color color = Color.BLACK;
		Vector n = geopoint.geometry.get_Normal(geopoint.point);
		Material material = geopoint.geometry.getMaterial();
		//calculate kr השתקפות
		double kr = material.kR;
		double kkr = k * kr;
		if (kkr > MIN_CALC_COLOR_K) 
		{
			Ray reflectedRay = constructReflectedRay(geopoint.point, ray, n);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
		}
		//calculate kt שקיפות
		double kt = material.kT;
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) 
		{
			Ray refractedRay = constructRefractedRay(geopoint.point, ray, n);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
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
	 * this function returns the color of the closest intersection point to the ray.
	 * @param ray -the ray between camera and view plane
	 * @return the color -color of closest point
	 */
	public Color traceRay(Ray ray)
	{
		GeoPoint closestPoint = findClosestIntersection(ray);  //find closestPoint from intersections
		if( closestPoint == null)//if there are no closest intersection point- return  Color.BLACK
			return scene.background;
		return calcColor(closestPoint, ray); // else-calculate  color 
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
		Ray lightRay = new Ray(geopoint.point, lightDirection, n); // refactored ray head move
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return true;
		double lightDistance = light.getDistance(geopoint.point);
		for (GeoPoint gp : intersections) {
		if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
		gp.geometry.getMaterial().kT == 0)
		return false;
		}
		return true;
		}

	/**
	 * this function calculate Refracted  Ray
	 * @param pointGeo point 
	 * @param inRay ray
	 * @param n Vector normal
	 * @return Ray- Refracted Ray
	 */
		private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n)
		{
			/*Vector direction=inRay.getDir(); //directional vector
			double vN = n.dotProduct(direction); //dot product to the directional vector
			Vector delta = n.scale(vN >= 0 ? DELTA : -DELTA); //check sign
			Point3D pointDelta= pointGeo.add(delta);//moving the point according delta
			return new Ray(pointDelta, inRay.getDir()); //return new ray
			*/
			return new Ray(pointGeo, inRay.getDir(),  n);
		}
		/**
		 * this function calculate Reflected Ray
		 * @param pointGeo point 
		 * @param inRay 
		 * @param n vector normal
		 * @return Ray - Reflected Ray
		 */
		private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n)
		{
			Vector direction=inRay.getDir(); //directional vector
			double vN=direction.dotProduct(n); //dot product to the directional vector
			if(Util.isZero(vN)) //check if is zero
				return null;
			Vector r=direction.subtract(n.scale(2*vN)); // r =v -2*(v*n)*n
		//	Vector delta=n.scale(n.dotProduct(r)>0? DELTA: -DELTA);
		//	Point3D pointDelta= pointGeo.add(delta);
		//	return new Ray(pointDelta, r);//return new ray
			return new Ray(pointGeo, r,  n);
			
		}
		/** this function find the closet intersection point, if there is no intersection return null
		 * @param Ray
		 * @return GeoPoint- The closest point
		 */
		private GeoPoint findClosestIntersection(Ray ray)
		{
			 if (ray == null) 
			 {
                 return null;
             }

            // GeoPoint closestPoint = null;
            // double closestDistance = Double.MAX_VALUE;
            // Point3D rayP0 = ray.getP0(); //the ray point

             List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray); //find intersection
             /*  if (intersections == null) // if there is no intersection
                 return null;

             for (GeoPoint geoPoint : intersections)
             {
                 double distance = rayP0.distance(geoPoint.point); //distance between rayP0 and point
                 if (distance < closestDistance) //check the closet point
                 {
                     closestDistance = distance;
                     closestPoint = geoPoint;
                 }
             }
             
             return closestPoint;
             */
             return ray.findClosestGeoPoint(intersections);
		}
		
		/**
		 * function that creates Partial shading in case the body or bodies that block the light source from the point have transparency at some level or another
		 * @param light - light source
		 * @param l -the vector from the light to the point 
		 * @param n- normal vector to the point at the geometry
		 * @param geopoint- the point in the geometry
		 * @return double - partial shading
		 */
		private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint)
		{
			Vector lightDirection = l.scale(-1); // from point to light source
			Ray lightRay = new Ray(geopoint.point, lightDirection, n);
			double lightDistance = light.getDistance(geopoint.point);
			double transperancy = 1.0; //default
			
			if(light instanceof PointLight) 
			{
				double lightRadius = ((PointLight)light).getRadius(); //get radius of light circle
				List<Ray> splitRays = lightRay.splitRay(scene.ShadowRays, lightDistance, lightRadius); //call to splitRay to create more rays if requested
				double transparencySum = 0; 
				for (Ray ray : splitRays) {
					transparencySum += rayTransparency(ray, lightDistance); //sum ktr of rays - factor of ray's transparency
				}
				if (splitRays.size() > 0) { // if there are more than 1 rays
					transperancy = transparencySum / splitRays.size(); //calculate average of transparency
				}
			}
			else //not point light (no changes)
			{
				if (scene.ShadowRays > 0) { //if there is shadow
					transperancy = rayTransparency(lightRay, lightDistance);
				}
			}
			return transperancy;
		}
		
		/**
		 * This function calculates transparency coefficient for ray and distance
		 * @param ray - the ray to calculate with
		 * @param lightDistance - the distance from light 
		 * @return ktr - transparency factor
		 */
		private double rayTransparency(Ray ray, double lightDistance) {
			var intersections = scene.geometries.findGeoIntersections(ray);
			if (intersections == null) return 1.0;
				double ktr = 1.0;
			for (GeoPoint gp : intersections) {
				if (alignZero(gp.point.distance(ray.getP0()) -lightDistance) <= 0) {
					ktr *= gp.geometry.getMaterial().kT;
					if (ktr < MIN_CALC_COLOR_K) return 0.0;
				}
			}
			return ktr;
		}
		
		

}


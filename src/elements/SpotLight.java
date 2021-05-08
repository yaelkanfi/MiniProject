/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Yael and Chagit
 *
 */
public class SpotLight extends PointLight {
	
	private Vector direction;

	/**constuctor that using super class constructor 
	 * @param intensity color
	 * @param position point3D
	 * @param kC double  factor for attenuation with distance
	 * @param kL double  factor for attenuation with distance
	 * @param kQ double  factor for attenuation with distance
	 * @param direction -directional vector
	 */
	public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalized();
	}
	@Override
	public Color getIntensity(Point3D p)
	{
		double l =direction.dotProduct(getL(p));//dir*L
    	//max(0,dir*L)
		
        if (l <= 0) //if(l<0) l=0;    
            return Color.BLACK;   // no color for t=zero (intensity*0)/(kC+kL*distance+kQ*distanceSquared)=0
        return super.getIntensity(p).scale(l);  //return  (intensity*dir*l)/(kC+kL*distance+kQ*distanceSquared)
  
	}


}

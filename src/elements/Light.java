/**
 * 
 */
package elements;

import primitives.Color;

/** 
 * @author Yael and Chagit
 * A class for the light
 */
abstract class Light {
	
	private Color intensity;

	/** Getter for intensity color
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

	/** constructor that initialize intensity color
	 * @param intensity - the color of intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}
	
	



	
}

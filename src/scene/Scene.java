/**
 * 
 */
package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * @author Yael and Chagit
 *
 */
public class Scene 
{
public String name; //name of scene
public Color background = Color.BLACK; //background color, the default color is black
public AmbientLight ambientLight=new AmbientLight (Color.BLACK, 0);  //the default color is black with zero scalar
public Geometries geometries; //the geometries collection in the scene
/**
 * constructor that get name and build empty collection of geometries
 * @param name the name of scene
 */
public Scene(String name)
{
	super();
	this.name = name;
	geometries = new Geometries();
}
/**
 * 
 * @param background- the background color to set
 * @return scene- this object
 */
public Scene setBackground(Color background)
{
	this.background = background;
	return this;
}
/**
 * @param ambientLight the ambientLight to set
 * @return scene- this object
 */
public Scene setAmbientLight(AmbientLight ambientLight) 
{
	this.ambientLight = ambientLight;
	return this;
}
/**
 * @param geometries the geometries to set
 * @return scene- this object
 */
public Scene setGeometries(Geometries geometries) 
{
	this.geometries = geometries;
	return this;
}

}

/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

/**
 * @author Home
 *
 */
public class MiniProject2 {

	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000);
	
	
	@Test
	public void test() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		List<Intersectable> ballOfBalls = new ArrayList<Intersectable>();
		for (double x = -50; x <= 50; x+=5) {
			for (double z = -Math.sqrt(2500 - x*x); z <= Math.sqrt(2500 - x*x); z += 5) {
				double y = Math.sqrt(2500 - x*x - z*z);				
				ballOfBalls.add(new Sphere(new Point3D(x, y, z), 3)
						.setEmmission(new Color((int)Math.abs(x), (int)Math.abs(z), (int)Math.abs(y)))
						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
				ballOfBalls.add(new Sphere(new Point3D(x, -y, z), 3)
						.setEmmission(new Color((int)Math.abs(x), (int)Math.abs(z), (int)Math.abs(y)))
						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
			}
		}
		Intersectable[] ballsArray = new Intersectable[ballOfBalls.size()];
		ballOfBalls.toArray(ballsArray);
		
		Geometries balls = new Geometries(ballsArray);
		

		scene.geometries.add( 
				balls,
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -115), new Point3D(150, 0, -150)) //floor
				.setMaterial(new Material().setkS(0.8).setnShininess(60)), 
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-150, -5, -150), new Point3D(150, 0, -150)) //floor
                .setMaterial(new Material().setkS(0.8).setnShininess(60)) 	
		);
				
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(40, 200, 200), 1, 0.00001,  0.000005,  new Vector(-1, -1, -4).normalize()) 
						.setkL(0.00001).setkQ( 0.000005).setRadius(5));
		

		scene.setShadowRays(70);
		Render render = new Render() 
				.setImageWriter(new ImageWriter("BoundingBoxTry", 600, 600)) 
				.setCamera(camera)
				.setRayTracer(new RayTracerBasic(scene))
				.setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
	}

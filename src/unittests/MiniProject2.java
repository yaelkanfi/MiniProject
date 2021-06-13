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
import elements.PointLight;
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
	private Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000);
	
	
	//@Test
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
	
	@Test
	public void test2() {
		
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0));
		
		//blue ball
		for (double x = -50; x <= 50; x+=5) { 
			for (double z = -Math.sqrt(2500 - x*x); z <= Math.sqrt(2500 - x*x); z += 5) {
				double y = Math.sqrt(2500 - x*x - z*z);	
				if(Double.isNaN(y)) {
					y = 0;
				}
				scene.geometries.add(new Sphere(new Point3D(x-15, y, z), 3)
						.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
				scene.geometries.add(new Sphere(new Point3D(x-15, -y, z), 3)
						.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
			}
		}
		
		//right ball
//		for (double x = -50; x <= 50; x+=5) {
//			for (double z = -Math.sqrt(2500 - x*x); z <= Math.sqrt(2500 - x*x); z += 5) {
//				double y = Math.sqrt(2500 - x*x - z*z);	
//				if(Double.isNaN(y)) {
//					y = 0;
//				}
//				scene.geometries.add(new Sphere(new Point3D(x+105, y, z), 3)
//						.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
//						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
//				scene.geometries.add(new Sphere(new Point3D(x+105, -y, z), 3)
//						.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
//						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
//			}
//		}
		

		
		scene.geometries.add(
				//mirrors
				new Sphere(new Point3D(0, -1000, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)),
				new Sphere(new Point3D(0, 1000, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)),
				new Sphere(new Point3D(-1000, 0, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)),
	            //balls
				new Sphere( new Point3D(80, 0 ,120) ,40).setEmmission(new Color(0,0,0))
			    .setMaterial(new Material().setkD(0.05).setkS(1).setnShininess(15).setkT(0).setkR(1)), 
			    new Sphere(new Point3D(60, 0, 0) , 10).setEmmission(new Color(75,0,25))
			    .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(15).setkT(0).setkR(1)) 
			    
		);
		
		scene.geometries.sortInnerGeometries();
				
		scene.lights.add(	
				new PointLight(new Color(200, 200, 510), new Point3D(-40, -50, 200), 1, 0.00001,  0.000005) 
						.setkL(0.00001).setkQ( 0.000005).setRadius(5));
		scene.lights.add(
				new PointLight(new Color(510, 200, 200), new Point3D(40, 50, 200), 1, 0.00001,  0.000005) 
				.setkL(0.00001).setkQ( 0.000005).setRadius(5));
		
		scene.lights.add(
				new SpotLight(new Color(200,00,000), new Point3D(30, 0, -60), 1, 0.00005, 0.00003, new Vector(1, 0, 2))
				);
		
		scene.setShadowRays(70);
		Render render = new Render() 
				.setImageWriter(new ImageWriter("BoundingBoxAutomated", 600, 600)) 
				.setCamera(camera)
				.setRayTracer(new RayTracerBasic(scene))
				.setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Test
//	  /**
//	   * Testing creation of lighted images
//	   */
//	  public void bigimageTest() {    
//	    Renderer renderer = new Renderer();
//	    renderer.setImageName("The great image");
//	    renderer.getScene.setCamera(new Camera(new Point3D(-5000,0,0), new Vector(1,0,0), new Vector(0,1,0); //NOTE: in this test, vTo is NOT the usual 0,0,-1. It is 1,0,0!!
//	    renderer.getScene().setCameraDistance(5300);
//	    renderer.getScene().addLight(new PointLight(new Color(200,200,200), new Point3D(new Coordinate(-30), new Coordinate(50), new Coordinate(60)), 1, 0.00005, 0.00003));
//	    renderer.getScene().addLight(new PointLight(new Color(00,00,200), new Point3D(new Coordinate(80), new Coordinate(80), new Coordinate(120)), 1, 0.00005, 0.00003));
//	    renderer.getScene().addLight(new SpotLight(new Color(200,00,000), new Point3D(new Coordinate(30), new Coordinate(00), new Coordinate(-60)), 1, 0.00005, 0.00003, new Vector(new Point3D(new Coordinate(1), new Coordinate(0), new Coordinate(2)))));
//	    
//	    renderer.getScene().addLight(new DirectionalLight(new Color(10, 100, 10), new Vector(new Point3D(new Coordinate(0), new Coordinate(0), new Coordinate(-0.5)))));
//	    
//	    Sphere s;
//	    
//
//	    // Remove this loop and all it's contents if you wish the rendering to take less than 30 minutes!!
//	    for (double x = -50; x<=50; x+=5)
//	      for (double y = -(50-Math.abs(x)); y<=50-Math.abs(x); y+=5) {
//	        double z = Math.sqrt(2500 - x*x - y*y);
//	        s = new Sphere(5, new Point3D(new Coordinate(x + 60), new Coordinate(y), new Coordinate(z)),new Color((int)Math.abs(x+y+z)%25,(int)Math.abs(x+y+z+10)%25,(int)Math.abs(x+y+z+20)%25));
//	        s.setMaterial(new Material(1,0.1,0,0.6,99));
//	        renderer.getScene().addGeometry(s);
//	        if (z != 0 ) {
//	          s = new Sphere(5, new Point3D(new Coordinate(x + 60), new Coordinate(y), new Coordinate(-z)),new Color((int)Math.abs(x+y+z)%25,(int)Math.abs(x+y+z+10)%25,(int)Math.abs(x+y+z+20)%25));
//	          s.setMaterial(new Material(1,0.1,0,0.6,99));
//	          renderer.getScene().addGeometry(s);
//	        }
//	        
//	      }
//
//
//	    
//	    s = new Sphere(70, new Point3D(new Coordinate(80), new Coordinate(0), new Coordinate(120)),new Color(0,0,0));
//	    s.setMaterial(new Material(0.05,1,1,0,15));
//	    renderer.getScene().addGeometry(s);
//	    
//	    s = new Sphere(30, new Point3D(new Coordinate(60), new Coordinate(0), new Coordinate(0)),new Color(75,0,25));
//	    s.setMaterial(new Material(0.2,1,1,0,15));
//	    renderer.getScene().addGeometry(s);
//	    
//	    s = new Sphere(800, new Point3D(new Coordinate(60), new Coordinate(-900), new Coordinate(0)),new Color(0,0,0));
//	    s.setMaterial(new Material(0.1,1,1,0,15));
//	    renderer.getScene().addGeometry(s);
//	    
//	    s = new Sphere(800, new Point3D(new Coordinate(60), new Coordinate(900), new Coordinate(0)),new Color(0,0,0));
//	    s.setMaterial(new Material(0.1,1,1,0,15));
//	    renderer.getScene().addGeometry(s);
//	    
//
//	    renderer.getScene().addGeometry(new Plain(new Point3D(new Coordinate(250), new Coordinate(-200), new Coordinate(-150)),new Point3D(new Coordinate(250), new Coordinate(200), new Coordinate(-150)),new Point3D(new Coordinate(250), new Coordinate(-200), new Coordinate(200)),new Color(15,15,15), new Material(0.7,1,0.3,0,99)));
//
//	    renderer.getScene().addGeometry(new Triangle(new Point3D(new Coordinate(-5000), new Coordinate(-200), new Coordinate(-70)),new Point3D(new Coordinate(150), new Coordinate(200), new Coordinate(-70)),new Point3D(new Coordinate(150), new Coordinate(-200), new Coordinate(-70)),new Color(7,7,7), new Material(0.1,1,0.5,0.0,99)));
//	    renderer.getScene().addGeometry(new Triangle(new Point3D(new Coordinate(-5000), new Coordinate(200), new Coordinate(-70)),new Point3D(new Coordinate(150), new Coordinate(200), new Coordinate(-70)),new Point3D(new Coordinate(-5000), new Coordinate(-200), new Coordinate(-70)),new Color(7,7,7), new Material(0.1,1,0.5,0.0,99)));
//
//	    renderer.getScene().setAmbientLight(new AmbientLight(new Color(0,0,0), 0));
//	    renderer.renderImage();
//	  }
	
	}

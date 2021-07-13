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
import elements.DirectionalLight;
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
	private Camera camera = new Camera(new Point3D(0, 0, 2000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
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
	
	//@Test //running time:    1067.196s = 18 minutes  (with improvement)   
	public void test2() {
		
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0));
		
		//middle blue sphere- a sphere made up of hundreds of small spheres
		//a loop that create the spheres with x,y,z sequential values  (using pitagoras)
		for (double x = -50; x <= 50; x+=5) {   //x between (-50 , 50)
			for (double z = -Math.sqrt(2500 - x*x); z <= Math.sqrt(2500 - x*x); z += 5) {//z between (-Math.sqrt(2500 - x*x), Math.sqrt(2500 - x*x))
				double y = Math.sqrt(2500 - x*x - z*z);	 //y value
				if(Double.isNaN(y)) {//check if y value is nan(close to zero) and update them to 0
					y = 0;
				}
				scene.geometries.add(new Sphere(new Point3D(x-15, y, z), 3)   //add the small spheres
						.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
				scene.geometries.add(new Sphere(new Point3D(x-15, -y, z), 3)
						.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
						.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
			}
		}
		

		
		scene.geometries.add(
				//mirrors
				new Sphere(new Point3D(0, -1000, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)), //lower mirror
				new Sphere(new Point3D(0, 1000, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)), //upper mirror
			      //spheres
				//right spheres
				new Sphere( new Point3D(80, 0 ,120) ,40).setEmmission(new Color(0,0,0)) // outer right spheres
			    .setMaterial(new Material().setkD(0.05).setkS(1).setnShininess(15).setkT(0).setkR(1)), 
			    new Sphere(new Point3D(60, 0, 0) , 10).setEmmission(new Color(75,0,25))// inner right spheres
			    .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(15).setkT(0).setkR(1)) ,
			    //left spheres
			    new Sphere( new Point3D(-110, 0 ,120) ,40).setEmmission(new Color(0,0,0)) // outer left spheres
			    .setMaterial(new Material().setkD(0.05).setkS(1).setnShininess(15).setkT(0).setkR(1)), 
			    new Sphere(new Point3D(-90, 0, 0) , 10).setEmmission(new Color(75,0,25)) // inner left spheres
			    .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(15).setkT(0).setkR(1)),
			    //middle small triangle
			    new Triangle(new Point3D(-30, 0, 0),new Point3D(-15, 10, 0),new Point3D(0, 0, 0)).setEmmission(new Color(200, 200, 510)).setMaterial(new Material().setkD(0.1).setkS(1).setnShininess(99).setkT(0.5).setkR(0))//0.1,1,0.5,0.0,99)));

			   
			    
			    
		);
		//box automation
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
		scene.lights.add(new DirectionalLight(new Color(10, 100, 10), new Vector(new Point3D(0,0,-0.5) )));
		
		scene.lights.add(	
				new PointLight(new Color(132, 45, 97), new Point3D(-80, -50, 200), 1, 0.00001,  0.000005) 
						.setkL(0.00001).setkQ( 0.000005).setRadius(5));
		//soft shadow improvement
		scene.setShadowRays(70);
		Render render = new Render() 
				.setImageWriter(new ImageWriter("BoundingBoxAutomated", 600, 600)) 
				.setCamera(camera)
				.setRayTracer(new RayTracerBasic(scene))
				.setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
	
	
	
	
	
	
	
	@Test //running time: 8680.965s  =144 minutes=2 hours and 25 minutes   (without improvement)
	public void test1() {
	
	scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0));
	
	//middle blue sphere- a sphere made up of hundreds of small spheres
	//a loop that create the spheres with x,y,z sequential values  (using pitagoras)
	for (double x = -50; x <= 50; x+=5) {   //x between (-50 , 50)
		for (double z = -Math.sqrt(2500 - x*x); z <= Math.sqrt(2500 - x*x); z += 5) {//z between (-Math.sqrt(2500 - x*x), Math.sqrt(2500 - x*x))
			double y = Math.sqrt(2500 - x*x - z*z);	 //y value
			if(Double.isNaN(y)) {//check if y value is nan(close to zero) and update them to 0
				y = 0;
			}
			scene.geometries.add(new Sphere(new Point3D(x-15, y, z), 3)   //add the small spheres
					.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
					.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
			scene.geometries.add(new Sphere(new Point3D(x-15, -y, z), 3)
					.setEmmission(new Color((int)Math.abs(x+y+z)% 256, (int)Math.abs(x+y+z+80) %256, (int)Math.abs(x+y+z+160)%256))
					.setMaterial(new Material().setkD(0.3).setkS(1).setnShininess(99)));
		}
	}
	

	
	scene.geometries.add(
			//mirrors
			new Sphere(new Point3D(0, -1000, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)), //lower mirror
			new Sphere(new Point3D(0, 1000, 0), 940).setEmmission(new Color(20, 20, 20)).setMaterial(new Material().setkR(1).setkS(1).setnShininess(99)), //upper mirror
		      //spheres
			//right spheres
			new Sphere( new Point3D(80, 0 ,120) ,40).setEmmission(new Color(0,0,0)) // outer right spheres
		    .setMaterial(new Material().setkD(0.05).setkS(1).setnShininess(15).setkT(0).setkR(1)), 
		    new Sphere(new Point3D(60, 0, 0) , 10).setEmmission(new Color(75,0,25))// inner right spheres
		    .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(15).setkT(0).setkR(1)) ,
		    //left spheres
		    new Sphere( new Point3D(-110, 0 ,120) ,40).setEmmission(new Color(0,0,0)) // outer left spheres
		    .setMaterial(new Material().setkD(0.05).setkS(1).setnShininess(15).setkT(0).setkR(1)), 
		    new Sphere(new Point3D(-90, 0, 0) , 10).setEmmission(new Color(75,0,25)) // inner left spheres
		    .setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(15).setkT(0).setkR(1)),
		    //middle small triangle
		    new Triangle(new Point3D(-30, 0, 0),new Point3D(-15, 10, 0),new Point3D(0, 0, 0)).setEmmission(new Color(200, 200, 510)).setMaterial(new Material().setkD(0.1).setkS(1).setnShininess(99).setkT(0.5).setkR(0))//0.1,1,0.5,0.0,99)));

		   
		    
		    
	);
	//box automation
	//scene.geometries.sortInnerGeometries();
			
	scene.lights.add(	
			new PointLight(new Color(200, 200, 510), new Point3D(-40, -50, 200), 1, 0.00001,  0.000005) 
					.setkL(0.00001).setkQ( 0.000005).setRadius(5));
	scene.lights.add(
			new PointLight(new Color(510, 200, 200), new Point3D(40, 50, 200), 1, 0.00001,  0.000005) 
			.setkL(0.00001).setkQ( 0.000005).setRadius(5));
	
	scene.lights.add(
			new SpotLight(new Color(200,00,000), new Point3D(30, 0, -60), 1, 0.00005, 0.00003, new Vector(1, 0, 2))
			);
	scene.lights.add(new DirectionalLight(new Color(10, 100, 10), new Vector(new Point3D(0,0,-0.5) )));
	
	scene.lights.add(	
			new PointLight(new Color(132, 45, 97), new Point3D(-80, -50, 200), 1, 0.00001,  0.000005) 
					.setkL(0.00001).setkQ( 0.000005).setRadius(5));
	//soft shadow improvement
	scene.setShadowRays(70);
	Render render = new Render() 
			.setImageWriter(new ImageWriter("BoundingBox2", 600, 600)) 
			.setCamera(camera)
			.setRayTracer(new RayTracerBasic(scene))
			.setMultithreading(3);
	render.renderImage();
	render.writeToImage();
}	
	
	
	

	}

/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * @author Chagit Shaviv and Yael Kanfi
 * unittests for mini project 1
 */
public class MiniProject1 {
	
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000);
	
	
	/**
	 * Produce a picture of spheres with shadow before changes
	 */
	@Test 
	public void ourTest() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( 
				
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -115), new Point3D(150, 0, -150)) //floor
				.setMaterial(new Material().setkS(0.8).setnShininess(60)), 
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-150, -5, -150), new Point3D(150, 0, -150)) //floor
                .setMaterial(new Material().setkS(0.8).setnShininess(60)),
 	
				new Sphere( new Point3D(0, 0, -80), 40).setEmmission(new Color(180,30,100)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0.4).setkR(0)), //biggest
				new Sphere( new Point3D(-40, 0, -115), 30).setEmmission(new Color(100,30,230)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)), //second left
				new Sphere( new Point3D(40, 0, -115), 30).setEmmission(new Color(100,30,230)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)), //second right
				new Sphere( new Point3D(-70, 0, -140), 20).setEmmission(new Color(20,170,80)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)),//third left
				new Sphere( new Point3D(70, 0, -140), 20).setEmmission(new Color(20,170,80)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)), //third right
				new Sphere( new Point3D(-90, 0, -150), 10).setEmmission(new Color(220,120,30)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)),//fourth left
				new Sphere(new Point3D(90, 0, -150), 10).setEmmission(new Color(220,120,30)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0))//fourth right
				
				);
				
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(40, 200, 200), 1, 0.00001,  0.000005,  new Vector(-1, -1, -4).normalize()) 
						.setkL(0.00001).setkQ( 0.000005));
		scene.lights.add(
		new SpotLight(new Color(1020, 400, 400), new Point3D(-40, 200, 200), 1, 0.00001,  0.000005,  new Vector(-4, -1, -1).normalize()) 
		.setkL(0.00001).setkQ( 0.000005));
		
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(-40, 200, 200), 1, 0.00001, 0.000005,  new Vector(-1, -1, -4).normalize()) 
						.setkL(0.00001).setkQ(0.000005));
		
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(-40, 200, 200), 1, 0.00001, 0.000005,  new Vector(-4, -1, -1).normalize()) 
						.setkL(0.00001).setkQ(0.000005));


		Render render = new Render() 
				.setImageWriter(new ImageWriter("noSoftShadowTry", 600, 600)) 
				.setCamera(camera) 
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 *  Produce a picture of spheres with shadow after changes
	 */
	@Test 
	public void ourTestImproved() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( 
				
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -115), new Point3D(150, 0, -150)) //floor
				.setMaterial(new Material().setkS(0.8).setnShininess(60)), 
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-150, -5, -150), new Point3D(150, 0, -150)) //floor
                .setMaterial(new Material().setkS(0.8).setnShininess(60)),
 	
				new Sphere( new Point3D(0, 0, -80), 40).setEmmission(new Color(180,30,100)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0.4).setkR(0)), //biggest
				new Sphere( new Point3D(-40, 0, -115), 30).setEmmission(new Color(100,30,230)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)), //second left
				new Sphere( new Point3D(40, 0, -115), 30).setEmmission(new Color(100,30,230)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)), //second right
				new Sphere( new Point3D(-70, 0, -140), 20).setEmmission(new Color(20,170,80)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)),//third left
				new Sphere( new Point3D(70, 0, -140), 20).setEmmission(new Color(20,170,80)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)), //third right
				new Sphere( new Point3D(-90, 0, -150), 10).setEmmission(new Color(220,120,30)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0)),//fourth left
				new Sphere(new Point3D(90, 0, -150), 10).setEmmission(new Color(220,120,30)).setMaterial(new Material().setkD(0.3).setkS(0.3).setnShininess(200).setkT(0).setkR(0))//fourth right
				
				);
				
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(40, 200, 200), 1, 0.00001,  0.000005,  new Vector(-1, -1, -4).normalize()) 
						.setkL(0.00001).setkQ( 0.000005).setRadius(5));
		scene.lights.add(
		new SpotLight(new Color(1020, 400, 400), new Point3D(40, 200, 200), 1, 0.00001,  0.000005,  new Vector(-4, -1, -1).normalize()) 
		.setkL(0.00001).setkQ( 0.000005).setRadius(5));
		
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(-40, 200, 200), 1, 0.00001, 0.000005,  new Vector(-1, -1, -4).normalize()) 
						.setkL(0.00001).setkQ(0.000005).setRadius(5));
		
		scene.lights.add( 
				new SpotLight(new Color(1020, 400, 400), new Point3D(-40, 200, 200), 1, 0.00001, 0.000005,  new Vector(-4, -1, -1).normalize()) 
						.setkL(0.00001).setkQ(0.000005).setRadius(5));

		scene.setShadowRays(70);
		Render render = new Render() 
				.setImageWriter(new ImageWriter("SoftShadowTry", 600, 600)) 
				.setCamera(camera) 
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	

}

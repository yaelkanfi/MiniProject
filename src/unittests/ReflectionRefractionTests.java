/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( //
				new Sphere( new Point3D(0, 0, -50), 50) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
				new Sphere( new Point3D(0, 0, -50), 25) //
						.setEmmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500),1, 0.0004, 0.0000006,  new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere( new Point3D(-950, -900, -1000), 400) //
						.setEmmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
				new Sphere(new Point3D(-950, -900, -1000), 200) //
						.setEmmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setkR(1)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setkR(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150),1, 0.00001, 0.000005,  new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Sphere(new Point3D(60, 50, -50), 30) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0),1, 4E-5,2E-7,  new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * produce a picture of a teddy bear holding a balloon that has another balloon in it, sitting on a reflected floor
	 */
	@Test// Chagit&Yael in the test we made teddy bear that reflected on floor with a balloon in balloon.
	public void TeddyBearYC() {
		Camera camera = new Camera(new Point3D(0, 0, 1700), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);	

		scene.geometries.add(
				//ears
				new Sphere( new Point3D(-40, 70, -120), 20) // the center sphere-  left ear
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
				new Sphere( new Point3D(40, 70, -120), 20) // the center sphere-right ear
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
	
				new Sphere( new Point3D(-38, 68, -120), 13) // the center sphere-  left ear inner
				.setEmmission(new Color(255,0,128)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
				new Sphere( new Point3D(38, 68, -120), 13) // the center sphere-right ear inner
				.setEmmission(new Color(255,0,128)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
	
				//mouth
				new Sphere( new Point3D(0, 20, -100), 15) // the center sphere-  mouth
				.setEmmission(new Color(255,0,128)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
				
				//eyes
				new Sphere( new Point3D(16, 42,-65),7) //right eye
						.setEmmission(new Color(69,43,29)),
				new Sphere( new Point3D(-16, 42, -65),7) //left eye
						.setEmmission(new Color(69,43,29)),
				new Triangle(new Point3D(-3, 25, 95), new Point3D(3, 25, 115), new Point3D(0,19, 130)).setEmmission(new Color(69,43,29)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //triangle of nose
			
				//head
				new Sphere( new Point3D(0, 40, -100), 40) // the center sphere-head
						.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
			
				//body
				new Sphere( new Point3D(0,-50, -120), 60) // the center body
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
			
				//hands
				new Sphere( new Point3D(-50, -13, -100), 23) // the center sphere-  left hand
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
				new Sphere( new Point3D(50, -13, -100), 23) // the center sphere-right hand
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
				
				//legs		
				new Sphere( new Point3D(-40, -100, -100), 30) // the center sphere-  left leg
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
				new Sphere( new Point3D(40, -100, -100), 30) // the center sphere-right leg
				.setEmmission(new Color(185,124,90)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0.3).setkR(0)),
			
				//balloons
				new Sphere( new Point3D(-95, 70, -120),36) // the center sphere- baloon
				.setEmmission(new Color(30,0,50)) //
				.setMaterial(new Material().setkD(0).setkS(0.2).setnShininess(1000).setkT(0.6).setkR(0.2)),
				new Sphere( new Point3D(-95, 70, -120),20) // the center sphere- baloon
				 .setEmmission(new Color(0,100,100)).setMaterial(new Material().setkD(0.6).setkS(0.9).setnShininess(1000).setkT(0).setkR(1)),
				new Triangle(new Point3D(-70, 42, 95), new Point3D(-67, 42, 115), new Point3D(-50,-5, 130)).setEmmission(new Color(java.awt.Color.RED)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //line of balloons
				
				//heart
				new Sphere( new Point3D(-15, -40, -65), 18) // the center sphere-  left heart
				.setEmmission(new Color(java.awt.Color.RED)),
				new Sphere( new Point3D(15, -40, -65), 18) // the center sphere-right heart
				.setEmmission(new Color(java.awt.Color.RED)),
				new Triangle(new Point3D(-28.7,-44.5, 95), new Point3D(28.6, -44.5, 115), new Point3D(0, -63, 130)).setEmmission(new Color(java.awt.Color.RED)), //bottom of heart	
				
				//floor
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -115), new Point3D(150, 0, -150)) //floor
		        .setEmmission(new Color(125, 158, 209)) 
		        .setMaterial(new Material().setkR(0.2)), 
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-150, -5, -150), new Point3D(150, 0, -150)) //floor
                .setEmmission(new Color(125, 158, 209)) 
                .setMaterial(new Material().setkR(0.2)));
 	
		scene.lights.add( //add spot light
				new SpotLight(new Color(1000, 600, 400), new Point3D(-250, 400, 1500),1 ,0.0004,0.0000006,  new Vector(-1, -1, -2)) 
				.setkL(0.0004).setkQ(0.0000006));
		scene.lights.add(new DirectionalLight(new Color(200,100,0), new Vector(-1, -1, -1))); //add directional light
		
		
		Render render = new Render() 
				.setImageWriter(new ImageWriter("TeddyBearY&C", 500, 500))
				.setCamera(camera) 
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	@Test//c&y test with all new affect
	public void allNewEffect() {
		Camera camera = new Camera(new Point3D(0, 0, 1500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
				.setEmmission(new Color(java.awt.Color.BLACK)) .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(1)), //
		new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
				.setMaterial(new Material().setkD(0.01).setkS(0.5).setnShininess(60).setkR(1)), //
		
				new Sphere( new Point3D(0, 0, -50), 50) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.6)),
				new Sphere( new Point3D(0, 0, -50), 25) //
						.setEmmission(new Color(java.awt.Color.RED)) );//
		scene.lights.add(new DirectionalLight(new Color(100,100,100), new Vector(0, 0, -1)));
		scene.lights.add(new SpotLight(new Color(430,230,230), new Point3D(60, 50, 0),1, 4E-5,2E-7,  new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));
		
		Render render = new Render() //
				.setImageWriter(new ImageWriter("allNewEffect", 600,600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	

	
		
}
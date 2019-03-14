package enginetester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderengine.DisplayManager;
import renderengine.Loader;
import renderengine.MasterRenderer;
import renderengine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1.2f,1.2f,1.2f));
		
		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("visMap1")));

		Camera camera = new Camera(terrain);
		MasterRenderer renderer = new MasterRenderer();
		
		//za prikaz frejmova
		long nextSecond = System.currentTimeMillis() + 1000;
		int frameInLastSecond = 0;
		int framesInCurrentSecond = 0;
		
		while (!Display.isCloseRequested()) {
			
			//za racunanje frejmova***********************
			long currentTime = System.currentTimeMillis();
		    if (currentTime > nextSecond) {
		        nextSecond += 1000;
		        frameInLastSecond = framesInCurrentSecond;
		        framesInCurrentSecond = 0;
		    }
		    framesInCurrentSecond++;
		    //System.out.println(frameInLastSecond);
		    //********************************************
		    if(terrain.getFlag() == 1)
		    	terrain.gen();
		    
			camera.move();

			renderer.processTerrain(terrain);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUP();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		System.exit(0);

	}
	
}

package terrains;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderengine.Loader;
import textures.ModelTexture;

public class Terrain {

	private static final float SIZE = 1500;
	private static int VERTEX_COUNT = 100;
	
	

	public int getVERTEX_COUNT() {
		return VERTEX_COUNT;
	}

	public void setVERTEX_COUNT(int vERTEX_COUNT) {
		VERTEX_COUNT = vERTEX_COUNT;
	}

	public float getSize() {
		return SIZE;
	}

	private float x;
	private float z;
	private RawModel model;
	private ModelTexture texture;
	private Loader loader;
	private HeightGenerator generator;
	
	private int color = 0;
	private int flag = 0;
	
	private int vertexPointer = 0;

	float kretanjeX;
	float kretanjeZ;

	public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture) {
		this.texture = texture;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		kretanjeX = gridX;
		kretanjeZ = gridZ;
		generator = new HeightGenerator(kretanjeX, kretanjeZ, this);
		//try {
		//	UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
		//JFrame.setDefaultLookAndFeelDecorated(false);
		//} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		//		| UnsupportedLookAndFeelException e) {
		//	e.printStackTrace();
		//}
		
		this.loader = loader;
		this.model = generateTerrain(loader);
		
		SettingsFrame SF = new SettingsFrame(generator, this);
		SF.setVisible(true);
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setKretanjeXZ(float kretanjeX, float kretanjeZ) {
		this.kretanjeX += kretanjeX;
		this.kretanjeZ += kretanjeZ;

		this.model = generateTerrain(loader);
	}
	
	public void gen() {
		this.model = generateTerrain(loader);
		flag = 0;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}

	private RawModel generateTerrain(Loader loader) {

		
		

		int count = VERTEX_COUNT * VERTEX_COUNT;
		//System.out.println(VERTEX_COUNT);
		//System.out.println(count);
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		vertexPointer = 0;
		// Thread t1 = new Thread(new Runnable() {
		// public void run() {
		// int vertexPointer = 0;
		// for(int i=0;i<VERTEX_COUNT/2;i++){
		// for(int j=0;j<VERTEX_COUNT;j++){
		// vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
		// vertices[vertexPointer*3+1] = generator.generateHeight(j, i);
		// vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
		// normals[vertexPointer*3] = 0;
		// normals[vertexPointer*3+1] = 1;
		// normals[vertexPointer*3+2] = 0;
		// textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
		// textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
		// vertexPointer++;
		// }
		// }
		// }
		// });
		// Thread t2 = new Thread(new Runnable() {
		// public void run() {
		// int vertexPointer = (VERTEX_COUNT/2)*VERTEX_COUNT;
		// for(int i=VERTEX_COUNT/2;i<VERTEX_COUNT;i++){
		// for(int j=0;j<VERTEX_COUNT;j++){
		// vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
		// vertices[vertexPointer*3+1] = generator.generateHeight(j, i);
		// vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
		// normals[vertexPointer*3] = 0;
		// normals[vertexPointer*3+1] = 1;
		// normals[vertexPointer*3+2] = 0;
		// textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
		// textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
		// vertexPointer++;
		// }
		// }
		// }
		// });
		// //t1.start();
		// //t2.start();
		//
		// try {
		// t1.join();
		// t2.join();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

//		Thread t2 = new Thread(new Runnable() {
//			public void run() {
//				int vertexPointer = 0;
//				for (int i = 0; i < VERTEX_COUNT; i++) {
//					for (int j = 0; j < VERTEX_COUNT; j++) {
//						vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
//						vertices[vertexPointer * 3 + 1] = generator.generateHeight(j, i);
//						vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
//						normals[vertexPointer * 3] = 0;
//						normals[vertexPointer * 3 + 1] = 1;
//						normals[vertexPointer * 3 + 2] = 0;
//						textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
//						textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
//						vertexPointer++;
//					}
//				}
//			}
//		});
//		Thread t1 = new Thread(new Runnable() {
//			public void run() {
//				int pointer = 0;
//				for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
//					for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
//						int topLeft = (gz * VERTEX_COUNT) + gx;
//						int topRight = topLeft + 1;
//						int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
//						int bottomRight = bottomLeft + 1;
//						indices[pointer++] = topLeft;
//						indices[pointer++] = bottomLeft;
//						indices[pointer++] = topRight;
//						indices[pointer++] = topRight;
//						indices[pointer++] = bottomLeft;
//						indices[pointer++] = bottomRight;
//					}
//				}
//			}
//		});
//		
//		t1.start();
//		t2.start();
//
//		try {
//			t1.join();
//			t2.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		IntStream.range(0, VERTEX_COUNT).parallel().forEach(i -> {
//		    IntStream.range(0, i).forEach(j -> { 
//		    	System.out.println(i + "   +   " + j);
//		    
//		    });
//		});
		
		
		/*IntStream.range(0, VERTEX_COUNT).parallel().forEach(i -> {
		    IntStream.range(0, i).forEach(j -> {
		    	vertices[(i*VERTEX_COUNT + j) * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[(i*VERTEX_COUNT + j) * 3 + 1] = generator.generateHeight(j, i, kretanjeX, kretanjeZ);
				vertices[(i*VERTEX_COUNT + j) * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				
				
				// jako zapucava doda previse proracuna pravi nacin racunanja senke
//				Vector3f normal = calculateNormal(j, i);
//				normals[(i*VERTEX_COUNT + j) * 3] = normal.x;
//				normals[(i*VERTEX_COUNT + j) * 3 + 1] = normal.y;
//				normals[(i*VERTEX_COUNT + j) * 3 + 2] = normal.z;
//				
				// moj skraceni nacin racunanja senke
//				Vector3f normal = new Vector3f(0,1,0);
//				if((i*VERTEX_COUNT + j) != 0 && i != 0) {
//					normal = new Vector3f(vertices[(i*VERTEX_COUNT + j) * 3 + 1] - vertices[((i*VERTEX_COUNT + j)-1) * 3 + 1], 2f, vertices[(i*VERTEX_COUNT + j) * 3 + 1] - vertices[((i*VERTEX_COUNT + j)-VERTEX_COUNT) * 3 + 1]);
//				}
//				normals[(i*VERTEX_COUNT + j) * 3] = normal.x;
//				normals[(i*VERTEX_COUNT + j) * 3 + 1] = normal.y;
//				normals[(i*VERTEX_COUNT + j) * 3 + 2] = normal.z;
				
				normals[(i*VERTEX_COUNT + j) * 3] = 0;
				normals[(i*VERTEX_COUNT + j) * 3 + 1] = 1;
				normals[(i*VERTEX_COUNT + j) * 3 + 2] = 0;
				
				
				//System.out.println(vertices[(i*VERTEX_COUNT + j) * 3 + 1]);
//				if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 0)
//					textureCoords[(i*VERTEX_COUNT + j) * 2] = 0;
////				else if (vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 0)
////					textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.85f;
////				else if (vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 20)
////					textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.170f;
////				else if (vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 40)
////					textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.205f;
//				else
				if(color == 1) {
//					textureCoords[(i*VERTEX_COUNT + j) * 2] = vertices[(i*VERTEX_COUNT + j) * 3 + 1]/HeightGenerator.getAMPLITUDE();
//					if(HeightGenerator.getAMPLITUDE() < 50) 
//							textureCoords[(i*VERTEX_COUNT + j) * 2] /= 2;
//					if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 0.9)
//						textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.01f;
//					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] >  45)
//						textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.99f;
//					
					if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < -50)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.01f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < -30)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.078125f - 0f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - -50f) / (-30f - -50f)) + 0.01f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < -15)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.15625f - 0.078125f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - -30f) / (-15f - -30f)) + 0.078125f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 0)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.234375f - 0.15625f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - -15f) / (0f - -15f)) + 0.15625f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 5)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.3125f - 0.234375f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 0) / (5f - 0)) + 0.234375f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 10)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.390625f - 0.3125f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 5f) / (10 - 5f)) + 0.3125f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 17)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.46875f - 0.390625f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 10f) / (17f - 10f)) + 0.390625f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 35)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.546875f - 0.46875f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 17f) / (35f - 17f)) + 0.46875f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 47)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.625f - 0.546875f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 35f) / (47f - 35f)) + 0.546875f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 55)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.703125f - 0.625f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 47f) / (55f - 47f)) + 0.625f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 60)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.78125f - 0.703125f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 55f) / (60f - 55f)) + 0.703125f;
					else if(vertices[(i*VERTEX_COUNT + j) * 3 + 1] < 75)
						textureCoords[(i*VERTEX_COUNT + j) * 2] = ((0.859375f - 0.78125f) * (vertices[(i*VERTEX_COUNT + j) * 3 + 1] - 60f) / (75 - 60f)) + 0.78125f;
					else
						textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.95f;
					
					
					
				}else {
					textureCoords[(i*VERTEX_COUNT + j) * 2] = 0.5f;
				}
					
				textureCoords[(i*VERTEX_COUNT + j) * 2 + 1] = 0f;
				
				System.out.println(i + "  +  " + j);
				//vertexPointer++;
				
		    });
		});*/
		
		

		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = generator.generateHeight(j, i, kretanjeX, kretanjeZ);
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				
				
				// jako zapucava doda previse proracuna pravi nacin racunanja senke
//				Vector3f normal = calculateNormal(j, i);
//				normals[vertexPointer * 3] = normal.x;
//				normals[vertexPointer * 3 + 1] = normal.y;
//				normals[vertexPointer * 3 + 2] = normal.z;
//				
				// moj skraceni nacin racunanja senke
				Vector3f normal = new Vector3f(0,1,0);
				if(vertexPointer != 0 && i != 0) {
					normal = new Vector3f(vertices[vertexPointer * 3 + 1] - vertices[(vertexPointer-1) * 3 + 1], 2f, vertices[vertexPointer * 3 + 1] - vertices[(vertexPointer-VERTEX_COUNT) * 3 + 1]);
				}
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				
//				normals[vertexPointer * 3] = 0;
//				normals[vertexPointer * 3 + 1] = 1;
//				normals[vertexPointer * 3 + 2] = 0;
				
				
				//System.out.println(vertices[vertexPointer * 3 + 1]);
//				if(vertices[vertexPointer * 3 + 1] < 0)
//					textureCoords[vertexPointer * 2] = 0;
////				else if (vertices[vertexPointer * 3 + 1] < 0)
////					textureCoords[vertexPointer * 2] = 0.85f;
////				else if (vertices[vertexPointer * 3 + 1] < 20)
////					textureCoords[vertexPointer * 2] = 0.170f;
////				else if (vertices[vertexPointer * 3 + 1] < 40)
////					textureCoords[vertexPointer * 2] = 0.205f;
//				else
				if(color == 1) {
//					textureCoords[vertexPointer * 2] = vertices[vertexPointer * 3 + 1]/HeightGenerator.getAMPLITUDE();
//					if(HeightGenerator.getAMPLITUDE() < 50) 
//							textureCoords[vertexPointer * 2] /= 2;
//					if(vertices[vertexPointer * 3 + 1] < 0.9)
//						textureCoords[vertexPointer * 2] = 0.01f;
//					else if(vertices[vertexPointer * 3 + 1] >  45)
//						textureCoords[vertexPointer * 2] = 0.99f;
//					
					if(vertices[vertexPointer * 3 + 1] < -50)
						textureCoords[vertexPointer * 2] = 0.01f;
					else if(vertices[vertexPointer * 3 + 1] < -30)
						textureCoords[vertexPointer * 2] = ((0.078125f - 0f) * (vertices[vertexPointer * 3 + 1] - -50f) / (-30f - -50f)) + 0.01f;
					else if(vertices[vertexPointer * 3 + 1] < -15)
						textureCoords[vertexPointer * 2] = ((0.15625f - 0.078125f) * (vertices[vertexPointer * 3 + 1] - -30f) / (-15f - -30f)) + 0.078125f;
					else if(vertices[vertexPointer * 3 + 1] < 0)
						textureCoords[vertexPointer * 2] = ((0.234375f - 0.15625f) * (vertices[vertexPointer * 3 + 1] - -15f) / (0f - -15f)) + 0.15625f;
					else if(vertices[vertexPointer * 3 + 1] < 5)
						textureCoords[vertexPointer * 2] = ((0.3125f - 0.234375f) * (vertices[vertexPointer * 3 + 1] - 0) / (5f - 0)) + 0.234375f;
					else if(vertices[vertexPointer * 3 + 1] < 10)
						textureCoords[vertexPointer * 2] = ((0.390625f - 0.3125f) * (vertices[vertexPointer * 3 + 1] - 5f) / (10 - 5f)) + 0.3125f;
					else if(vertices[vertexPointer * 3 + 1] < 17)
						textureCoords[vertexPointer * 2] = ((0.46875f - 0.390625f) * (vertices[vertexPointer * 3 + 1] - 10f) / (17f - 10f)) + 0.390625f;
					else if(vertices[vertexPointer * 3 + 1] < 35)
						textureCoords[vertexPointer * 2] = ((0.546875f - 0.46875f) * (vertices[vertexPointer * 3 + 1] - 17f) / (35f - 17f)) + 0.46875f;
					else if(vertices[vertexPointer * 3 + 1] < 47)
						textureCoords[vertexPointer * 2] = ((0.625f - 0.546875f) * (vertices[vertexPointer * 3 + 1] - 35f) / (47f - 35f)) + 0.546875f;
					else if(vertices[vertexPointer * 3 + 1] < 55)
						textureCoords[vertexPointer * 2] = ((0.703125f - 0.625f) * (vertices[vertexPointer * 3 + 1] - 47f) / (55f - 47f)) + 0.625f;
					else if(vertices[vertexPointer * 3 + 1] < 60)
						textureCoords[vertexPointer * 2] = ((0.78125f - 0.703125f) * (vertices[vertexPointer * 3 + 1] - 55f) / (60f - 55f)) + 0.703125f;
					else if(vertices[vertexPointer * 3 + 1] < 75)
						textureCoords[vertexPointer * 2] = ((0.859375f - 0.78125f) * (vertices[vertexPointer * 3 + 1] - 60f) / (75 - 60f)) + 0.78125f;
					else
						textureCoords[vertexPointer * 2] = 0.95f;
					
					
					
				}else {
					textureCoords[vertexPointer * 2] = 0.5f;
				}
					
				textureCoords[vertexPointer * 2 + 1] = 0f;
				vertexPointer++;
				
			}
		}
		
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		
		
		
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private Vector3f calculateNormal(int x, int z) {
		float heightL = generator.generateHeight(x - 1, z, kretanjeX, kretanjeZ);
		float heightR = generator.generateHeight(x + 1, z, kretanjeX, kretanjeZ);
		float heightD = generator.generateHeight(x, z - 1, kretanjeX, kretanjeZ);
		float heightU = generator.generateHeight(x, z + 1, kretanjeX, kretanjeZ);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}

}

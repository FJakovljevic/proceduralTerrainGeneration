package renderengine;

import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int WIDTH = 1300;
	private static final int HEIGHT = 800;
	private static final int FPS_CAP = 100;

	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

//		Canvas openglSurface = new Canvas();
//        JFrame frame = new JFrame();
//        frame.setSize(1300, 1100);
//        frame.add(openglSurface);
//        frame.setVisible(true);
//        frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent evt) {
//				System.exit(0);
//			}
//		});
//        openglSurface.setSize(WIDTH, HEIGHT);
//        try {
//			Display.setParent(openglSurface);
//		} catch (LWJGLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Terrain Generator");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	public static void updateDisplay() {

		Display.sync(FPS_CAP);
		Display.update();

	}

	public static void closeDisplay() {

		Display.destroy();

	}

}

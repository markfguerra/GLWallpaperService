package com.glwallpaperservice.testing.wallpapers.nehe.lesson02;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.hardware.SensorEvent;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.glwallpaperservice.testing.wallpapers.nehe.lesson02.objects.Square;
import com.glwallpaperservice.testing.wallpapers.nehe.lesson02.objects.Triangle;

public class NeheLesson02Renderer implements GLWallpaperService.Renderer {

	/** Triangle instance */
	private Triangle triangle;

	/** Square instance */
	private Square square;

	/**
	 * Set this class as renderer for this GLSurfaceView. Request Focus and set
	 * if focusable in touch mode to receive the Input from Screen
	 * 
	 * @param context
	 *            - The Activity Context
	 */

	public void onSensorChanged(SensorEvent event) {

	}

	public NeheLesson02Renderer() {
		triangle = new Triangle();
		square = new Square();
	}

	public void release() {
		// TODO stuff to release
	}

	/**
	 * The Surface is created/init()
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	}

	/**
	 * Here we do our drawing
	 */
	public void onDrawFrame(GL10 gl) {
		// Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The Current Modelview Matrix

		gl.glTranslatef(0.0f, -1.2f, -6.0f); // Move down 1.2 Unit And Into The Screen 6.0
		square.draw(gl); // Draw the square

		gl.glTranslatef(0.0f, 2.5f, 0.0f); // Move up 2.5 Units
		triangle.draw(gl); // Draw the triangle

	}

	/**
	 * If the surface changes, reset the view
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0) { // Prevent A Divide By Zero By
			height = 1; // Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); // Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
		gl.glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
		gl.glLoadIdentity(); // Reset The Modelview Matrix
	}

	/**
	 * Override the touch screen listener.
	 * 
	 * React to moves and presses on the touchscreen.
	 */
	public boolean onTouchEvent(MotionEvent event) {
		// We handled the event
		return true;
	}
}

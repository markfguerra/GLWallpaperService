package com.glwallpaperservice.testing.wallpapers.nehe.lesson08;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.content.Context;
import android.hardware.SensorEvent;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.glwallpaperservice.testing.wallpapers.nehe.lesson08.objects.Cube;

/**
 * Walter Reid (androidgametutorials.com)
 */
class MyRenderer implements GLWallpaperService.Renderer {

	private int height;

	/** Cube instance */
	private Cube cube;

	/* Rotation values */
	private float xrot; // X Rotation
	private float yrot; // Y Rotation

	/* Rotation speed values */
	private float xspeed = 0.5f; // X Rotation Speed
	private float yspeed = 0.5f; // Y Rotation Speed

	private float z = -5.0f; // Depth Into The Screen

	private int filter = 0; // Which texture filter?

	/** Is light enabled */
	private boolean light = true;
	/** Is blending enabled ( NEW ) */
	private boolean blend = true;

	/* The initial light values */
	private float[] lightAmbient = { 0.5f, 0.5f, 0.5f, 1.0f };
	private float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float[] lightPosition = { 0.0f, 0.0f, 2.0f, 1.0f };

	/* The buffers for our light values */
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightPositionBuffer;

	/* Variables and factor for the input handler */
	private float oldX;
	private float oldY;
	private final float TOUCH_SCALE = 0.2f; // Proved to be good for normal rotation

	/** The Activity Context */
	private Context context;

	public void onSensorChanged(SensorEvent event) {
	}

	public MyRenderer() {
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(lightAmbient);
		lightAmbientBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(lightDiffuse);
		lightDiffuseBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(lightPosition);
		lightPositionBuffer.position(0);

		cube = new Cube();
	}

	public void setContext(Context value) {
		context = value;
	}

	public void release() {
		// TODO stuff to release
	}

	/**
	 * The Surface is created/init()
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// And there'll be light!
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer); // Setup The Ambient Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer); // Setup The Diffuse Light
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer); // Position The Light
		gl.glEnable(GL10.GL_LIGHT0); // Enable Light 0

		// Blending
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Full Brightness. 50% Alpha ( NEW )
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Set The Blending Function For Translucency ( NEW )

		// Settings
		gl.glDisable(GL10.GL_DITHER); // Disable dithering
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		// Load the texture for the cube once during Surface creation
		cube.loadGLTexture(gl, this.context);
	}

	/**
	 * Here we do our drawing
	 */
	public void onDrawFrame(GL10 gl) {
		// Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The Current Modelview Matrix

		// Check if the light flag has been set to enable/disable lighting
		if (light) {
			gl.glEnable(GL10.GL_LIGHTING);
		} else {
			gl.glDisable(GL10.GL_LIGHTING);
		}

		// Check if the blend flag has been set to enable/disable blending
		if (blend) {
			gl.glEnable(GL10.GL_BLEND); // Turn Blending On ( NEW )
			gl.glDisable(GL10.GL_DEPTH_TEST); // Turn Depth Testing Off ( NEW )

		} else {
			gl.glDisable(GL10.GL_BLEND); // Turn Blending On ( NEW )
			gl.glEnable(GL10.GL_DEPTH_TEST); // Turn Depth Testing Off ( NEW )
		}

		// Drawing
		gl.glTranslatef(0.0f, 0.0f, z); // Move z units into the screen
		// Scale the Cube to 80 percent, otherwise it would be too large for the screen
		gl.glScalef(0.8f, 0.8f, 0.8f);

		// Rotate around the axis based on the rotation matrix (rotation, x, y, z)
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); // X
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); // Y

		cube.draw(gl, filter); // Draw the Cube

		// Change rotation factors
		xrot += xspeed;
		yrot += yspeed;
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
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
		gl.glLoadIdentity(); // Reset The Modelview Matrix
	}

	/**
	 * Override the touch screen listener.
	 * 
	 * React to moves and presses on the touchscreen.
	 */
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		// If a touch is moved on the screen
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// Calculate the change
			float dx = x - oldX;
			float dy = y - oldY;
			// Define an upper area of 10% on the screen
			int upperArea = this.height / 10;

			// Zoom in/out if the touch move has been made in the upper
			if (y < upperArea) {
				z -= dx * TOUCH_SCALE / 2;

				// Rotate around the axis otherwise
			} else {
				xrot += dy * TOUCH_SCALE;
				yrot += dx * TOUCH_SCALE;
			}
		}

		// Remember the values
		oldX = x;
		oldY = y;

		// We handled the event
		return true;
	}
}

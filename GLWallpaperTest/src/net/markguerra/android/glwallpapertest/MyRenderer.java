package net.markguerra.android.glwallpapertest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.rbgrn.android.glwallpaperservice.*;
import android.opengl.GLU;

public class MyRenderer implements GLWallpaperService.Renderer {
	private GLCube mCube;

	public void onDrawFrame(GL10 gl) {
		gl.glClearColor(0.2f, 0.6f, 0.2f, 1f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Draw a cube
		autoRotate(gl);
		mCube.draw(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println("setup");
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 60f, (float)width/(float)height, 1f, 100f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glTranslatef(0, 0, -5);
		gl.glRotatef(30f, 1, 0, 0);

		gl.glEnable(GL10.GL_RESCALE_NORMAL);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		mCube = new GLCube();

		gl.glClearDepthf(1f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		//Turn on culling, so OpenGL only draws one side of the primitives
		gl.glEnable(GL10.GL_CULL_FACE);
		//Define the front of a primitive to be the side where the listed vertexes are counterclockwise
		gl.glFrontFace(GL10.GL_CCW);
		//Do not draw the backs of primitives
		gl.glCullFace(GL10.GL_BACK);
	}

	private void autoRotate(GL10 gl) {
		gl.glRotatef(1, 0, 1, 0);
		gl.glRotatef(0.5f, 1, 0, 0);
	}

	/**
	 * Called when the engine is destroyed. Do any necessary clean up because
	 * at this point your renderer instance is now done for.
	 */
	public void release() {

	}
}

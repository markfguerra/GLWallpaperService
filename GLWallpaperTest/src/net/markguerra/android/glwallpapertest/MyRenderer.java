package net.markguerra.android.glwallpapertest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.rbgrn.android.glwallpaperservice.*;

public class MyRenderer implements GLWallpaperService.Renderer {

	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

	}

	/**
	 * Called when the engine is destroyed. Do any necessary clean up because
	 * at this point your renderer instance is now done for.
	 */
	public void release() {

	}
}

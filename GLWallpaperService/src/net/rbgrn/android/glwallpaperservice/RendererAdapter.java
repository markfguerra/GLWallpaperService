package net.rbgrn.android.glwallpaperservice;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

/**
 * There are two kinds of OpenGL renderer classes that matter here, and they are 
 * GLSurfaceView.Renderer and GLWallpaperService.Renderer. Although they are very similar, 
 * they cannot easily be used interchangeably. To be able to treat them as the same, you 
 * can use RendererAdapter.
 * 
 * You might use this class if you want to display your wallpaper renderer inside of an 
 * activity using a GLSurfaceView. This is useful to make a "gallery" style app that uses
 * your renderer. Also, you may want to test your renderer using an Activity.
 * 
 * This class is a two-way adapter, which means it doesn't matter what type your renderer
 * actually is. However if you are writing your renderer from scratch, it would make sense
 * to make your Renderer implement GLWallpaperService.Renderer, so that your 
 * GLWallpaperService class does not need to depend on this adapter.
 * 
 * To use, create a new RendererAdapter using your renderer class as a parameter in the 
 * constructor. Then supply your RendererAdapter where ever you want to use your Renderer. 
 * 
 * You don't necessarily need to use this class, mostly because the method names are the 
 * same for both interface. Alternatively, you can cast from one class to the other as needed.
 * The other option would be to have your renderer implement both GLSurfaceView.Renderer and 
 * GLWallpaperService.Renderer. However these choices might not be ideal for your situation, 
 * so this adapter was provided.
 */
public class RendererAdapter implements GLSurfaceView.Renderer, GLWallpaperService.Renderer {
	GLSurfaceView.Renderer surfaceRenderer;
	GLWallpaperService.Renderer wallpaperRenderer;

	public RendererAdapter(GLSurfaceView.Renderer renderer) {
		surfaceRenderer = renderer;
	}

	public RendererAdapter(GLWallpaperService.Renderer renderer) {
		wallpaperRenderer = renderer;
	}

	public void onDrawFrame(GL10 gl) {
		if (wallpaperRenderer != null)
			wallpaperRenderer.onDrawFrame(gl);
		else
			surfaceRenderer.onDrawFrame(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (wallpaperRenderer != null)
			wallpaperRenderer.onSurfaceChanged(gl, width, height);
		else
			surfaceRenderer.onSurfaceChanged(gl, width, height);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		if (wallpaperRenderer != null)
			wallpaperRenderer.onSurfaceCreated(gl, config);
		else
			surfaceRenderer.onSurfaceCreated(gl, config);
	}

	/**
	 * Provides access to your original Renderer class.
	 * @return The renderer class used to create RendererAdapter.
	 */
	public Object getRenderer() {
		if (wallpaperRenderer != null)
			return wallpaperRenderer;
		else
			return surfaceRenderer;
	}
}

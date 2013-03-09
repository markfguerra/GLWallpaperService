package net.markguerra.android.glwallpapertest;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.ViewGroup;

public class GalleryActivity extends Activity {
	GLSurfaceView glView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Instantiate OpenGL drawing surface and hold a reference
		ViewGroup container = (ViewGroup)findViewById(R.id.container);
		glView = new GLSurfaceView(this);
		MyRenderer renderer = new MyRenderer();
		glView.setRenderer(renderer);

		//Put the container on the screen for the world to see.
		container.addView(glView);
	}

	/**
	 * Allows GLSurfaceView to respond to the Resume event
	 */
	@Override
	protected void onResume() {
		super.onResume();
		glView.onResume();
	}

	/**
	 * Allows GLSurfaceView to respond to the Pause event
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
	}
}

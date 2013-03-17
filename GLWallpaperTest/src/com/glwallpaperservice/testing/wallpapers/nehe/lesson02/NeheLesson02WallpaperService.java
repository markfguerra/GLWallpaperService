package com.glwallpaperservice.testing.wallpapers.nehe.lesson02;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * 
 * @author Daniel Sundberg
 * 
 */
public class NeheLesson02WallpaperService extends GLWallpaperService {

	public NeheLesson02WallpaperService() {
		super();
	}

	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine();
		return engine;
	}

	class MyEngine extends GLEngine implements
			SharedPreferences.OnSharedPreferenceChangeListener,
			SensorEventListener {
		NeheLesson02Renderer renderer;

		public MyEngine() {
			super();
			// handle prefs, other initialization
			renderer = new NeheLesson02Renderer();
			setRenderer(renderer);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
		}

		public void onDestroy() {
			// Unregister this as listener
			sm.unregisterListener(this);

			// Kill renderer
			if (renderer != null) {
				renderer.release(); // assuming yours has this method - it
									// should!
			}
			renderer = null;

			setTouchEventsEnabled(false);

			super.onDestroy();
		}

		private SensorManager sm;

		@Override
		public void onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);
			renderer.onTouchEvent(event);
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			// Add touch events
			setTouchEventsEnabled(true);

			// Get sensormanager and register as listener.
			sm = (SensorManager) getSystemService(SENSOR_SERVICE);
			Sensor orientationSensor = sm
					.getDefaultSensor(SensorManager.SENSOR_ORIENTATION);
			sm.registerListener(this, orientationSensor,
					SensorManager.SENSOR_DELAY_GAME);

		}

		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			// /renderer.onSensorChanged(event);
		}
	}

}

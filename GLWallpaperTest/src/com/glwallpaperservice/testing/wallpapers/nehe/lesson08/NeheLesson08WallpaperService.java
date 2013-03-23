package com.glwallpaperservice.testing.wallpapers.nehe.lesson08;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class NeheLesson08WallpaperService extends GLWallpaperService {

	public static final String PREFERENCES = "nu.danielsundberg.droid.spinbox.livewallpaper";

	private SensorManager sm;

	public NeheLesson08WallpaperService() {
		super();
	}

	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine();
		return engine;
	}

	class MyEngine extends GLEngine implements
			SharedPreferences.OnSharedPreferenceChangeListener,
			SensorEventListener {
		NeheLesson08Renderer renderer;

		public MyEngine() {
			super();
			// handle prefs, other initialization
			renderer = new NeheLesson08Renderer();
			renderer.setContext(getBaseContext());
			setRenderer(renderer);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
		}

		public void onDestroy() {
			// Unregister this as listener
			sm.unregisterListener(this);

			// Kill renderer
			if (renderer != null) {
				renderer.release(); // assuming yours has this method - it should!
			}
			renderer = null;

			setTouchEventsEnabled(false);

			super.onDestroy();
		}

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
			Sensor orientationSensor = sm.getDefaultSensor(SensorManager.SENSOR_ORIENTATION);
			sm.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
		}

		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
		}
	}
}

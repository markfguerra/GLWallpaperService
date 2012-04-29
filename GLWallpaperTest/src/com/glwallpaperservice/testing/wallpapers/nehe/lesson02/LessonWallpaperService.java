package com.glwallpaperservice.testing.wallpapers.nehe.lesson02;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.glwallpaperservice.testing.wallpapers.nehe.lesson02.objects.Square;
import com.glwallpaperservice.testing.wallpapers.nehe.lesson02.objects.Triangle;
import net.rbgrn.android.glwallpaperservice.*;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * 
 * @author Daniel Sundberg
 *
 */
public class LessonWallpaperService extends GLWallpaperService {

	public static final String PREFERENCES = "nu.danielsundberg.droid.spinbox.livewallpaper";
		
	public LessonWallpaperService() {
		super();
	}

	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine();
		return engine;
	}


	class MyEngine extends GLEngine implements SharedPreferences.OnSharedPreferenceChangeListener, SensorEventListener {
		MyRenderer renderer;
		
		public MyEngine() {
			super();
			// handle prefs, other initialization
			renderer = new MyRenderer();
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
		    Sensor orientationSensor = sm.getDefaultSensor(SensorManager.SENSOR_ORIENTATION);	
		    sm.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
		
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
//			/renderer.onSensorChanged(event);
		}
	}
	
}
		
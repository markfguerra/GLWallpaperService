package net.markguerra.android.glwallpapertest;

public class MyWallpaperService extends GLWallpaperService {
	public MyWallpaperService() {
		super();
	}
	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine();
		return engine;
	}

	// prefs and sensor interface is optional, just showing that this is where you do all of that - everything that would normally be in an activity is in here.
	class MyEngine extends GLEngine implements SharedPreferences.OnSharedPreferenceChangeListener, SensorEventListener {
		MyRenderer renderer;
		public MyEngine() {
			super();
			// handle prefs, other initialization
			renderer = new MyRenderer();
			setRenderer(renderer);
			setRenderMode(RENDERMODE_WHEN_DIRTY);
		}

		public void onDestroy() {
			super.onDestroy();
			if (renderer != null) {
				renderer.release(); // assuming yours has this method - it should!
			}
			renderer = null;
		}
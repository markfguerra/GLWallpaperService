package net.markguerra.android.glwallpapertest;

import net.rbgrn.android.glwallpaperservice.*;

public class MyWallpaperService extends GLWallpaperService {
	public MyWallpaperService() {
		super();
	}
	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine();
		return engine;
	}

	class MyEngine extends GLEngine {
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
	}
}

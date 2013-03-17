/*
 * Copyright (C) 2010 Daniel Sundberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glwallpaperservice.testing.wallpapers.nehe.lesson08;

import net.markguerra.android.glwallpapertest.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Settings for the wallpaper
 * 
 * TODO These preferences seem to have no effect
 * 
 * @author Daniel Sundberg
 * 
 */
public class NeheLesson08SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getPreferenceManager().setSharedPreferencesName(NeheLesson08WallpaperService.PREFERENCES);
		addPreferencesFromResource(R.xml.nehelesson08preferences);
	}
}

package com.akelio.android.acollab.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import com.akelio.android.acollab.BuildConfig;
import com.akelio.android.acollab.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

	// TutorialSyncHelper mTutorialSyncHelper;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.settings);
		PreferenceManager preferenceManager = getPreferenceManager();
		preferenceManager.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("notificationPref")) {
			if (sharedPreferences.getBoolean(key, true)) {
				if (BuildConfig.DEBUG) {
					// Log.d(YourApplication.LOG_TAG, "settings notificationPref changed: addPeriodicSync()");
				}
				// mTutorialSyncHelper.addPeriodicSync();
			} else {
				if (BuildConfig.DEBUG) {
					// Log.d(YourApplication.LOG_TAG, "settings notificationPref changed: removePeriodicSync()");
				}
				// mTutorialSyncHelper.removePeriodicSync();
			}
		}
	}
}
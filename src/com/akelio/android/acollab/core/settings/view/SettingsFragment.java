package com.akelio.android.acollab.core.settings.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import com.akelio.android.acollab.BuildConfig;
import com.akelio.android.acollab.R;

public class SettingsFragment extends PreferenceCompatFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_users, menu);
	}
}
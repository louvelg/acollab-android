package com.akelio.android.acollab.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.akelio.android.acollab.R;

public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

}

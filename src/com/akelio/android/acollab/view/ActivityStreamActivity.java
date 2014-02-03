package com.akelio.android.acollab.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ActivityStreamActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("start activityStreamActivity");
		super.onCreate(savedInstanceState);
		ActivityStreamFragment fragment = new ActivityStreamFragment();
		getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, fragment.getClass().getSimpleName()).commit();
	}

}

package com.akelio.android.acollab.core.news.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class NewsActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("start activityStreamActivity");
		super.onCreate(savedInstanceState);
		NewsFragment fragment = new NewsFragment();
		getSupportFragmentManager()
				.beginTransaction()
				.add(android.R.id.content, fragment,
						fragment.getClass().getSimpleName()).commit();
	}

}
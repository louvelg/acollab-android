package com.akelio.android.acollab.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.akelio.android.acollab.R;

public class UsersActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
//		UsersFragment fragment = (UsersFragment) getFragmentManager().findFragmentById(R.id.fragmentUsers);
		
//		UsersFragment fragment = new UsersFragment(); // 
//        getSupportFragmentManager().beginTransaction()
//           .add(android.R.id.content, fragment,
//              fragment.getClass().getSimpleName()).commit(); // 
	}


}

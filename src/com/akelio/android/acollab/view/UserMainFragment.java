package com.akelio.android.acollab.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.service.ActivityStreamService;
import com.akelio.android.acollab.service.ContactService;
import com.akelio.android.acollab.service.SpaceService;

public class UserMainFragment extends Fragment {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(false);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		
		View view = inflater.inflate(R.layout.users, container, false);

		MasterDetailFragments currentFragments = MasterDetailFragmentHelper.getCurrentFragments(R.id.usermain_fragment, R.id.userdetail_fragment, FragmentUserDetails.class, getChildFragmentManager());
		if (currentFragments.master == null) {
			currentFragments.master = FragmentListUsers.newInstance();
		}

		MasterDetailFragmentHelper.initFragments(currentFragments, R.id.usermain_fragment, R.id.userdetail_fragment, getResources().getConfiguration(), getChildFragmentManager());

		return view;
	}


}

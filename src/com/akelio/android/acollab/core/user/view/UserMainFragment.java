package com.akelio.android.acollab.core.user.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.core.menu.view.MasterDetailFragmentHelper;
import com.akelio.android.acollab.core.menu.view.MasterDetailFragments;

public class UserMainFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(false);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.users, container, false);

		MasterDetailFragments currentFragments = MasterDetailFragmentHelper.getCurrentFragments(R.id.usermain_fragment, R.id.userdetail_fragment, UserDetailsFragment.class, getChildFragmentManager());
		if (currentFragments.master == null) {
			currentFragments.master = UserListFragment.newInstance();
		}

		MasterDetailFragmentHelper.initFragments(currentFragments, R.id.usermain_fragment, R.id.userdetail_fragment, getResources().getConfiguration(), getChildFragmentManager());

		return view;
	}

}

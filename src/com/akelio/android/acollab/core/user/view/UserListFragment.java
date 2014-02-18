/*
 * Copyright (C) 2012 The Android Open Source Project
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
package com.akelio.android.acollab.core.user.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.core.user.dao.UserDAO;
import com.akelio.android.acollab.core.user.service.UserService;
import com.akelio.android.acollab.entity.User;
import com.akelio.android.acollab.utils.FragmentHelper;
import de.greenrobot.event.EventBus;

public class UserListFragment extends ListFragment {
	OnUserSelectedListener		mCallback;
	private UserDAO				userDAO;
	private boolean				dualPanel;
	private SimpleAdapter		mSchedule;
	private FragmentActivity	fa;
	// private LinearLayout linear;

	static final String			URL	= "http://geb.test1.acollab.com/rest/v1/1/users";

	public interface OnUserSelectedListener {
		public void onUserSelected(int position, ListView l);
	}

	public static UserListFragment newInstance() {
		UserListFragment frag = new UserListFragment();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
		if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HashMap obj = (HashMap) l.getItemAtPosition(position);
		String idValue = (String) obj.get("textViewInvisible");
		UserDetailsFragment detailFragment = UserDetailsFragment.newInstance(idValue);
		if (!this.dualPanel) {
			FragmentHelper.initFragmentWithBackstack(detailFragment, R.id.usermain_fragment, this.getParentFragment().getChildFragmentManager());

		} else {
			FragmentHelper.initFragment(detailFragment, R.id.userdetail_fragment, this.getParentFragment().getChildFragmentManager());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		fa = super.getActivity();
		View mainView = inflater.inflate(R.layout.friendlist, container, false);
		// linear = (LinearLayout) container.findViewById(R.id.linear);
		fillData();
		return mainView;
	}

	private void fillData() {
		userDAO = new UserDAO(getActivity());
		List<User> users = userDAO.getUsers();
		userDAO.close();

		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		for (User user : users) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("textViewName", user.getUsername());
			map.put("textViewCompanyName", user.getCompany());
			map.put("textViewNumber", user.getPhone1());
			map.put("textViewInvisible", user.getUserId());
			listItem.add(map);
		}

		mSchedule = new SimpleAdapter(getActivity(), listItem, R.layout.list_user_item, new String[] { "textViewName", "textViewCompanyName", "textViewNumber", "textViewInvisible" }, new int[] {
				R.id.textViewName, R.id.textViewCompanyName, R.id.textViewNumber, R.id.textViewInvisible });
		this.setListAdapter(mSchedule);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.dualPanel = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_users, menu);
	}

	private void startRequest() {
		Intent contactIntent = new Intent(fa, UserService.class);
		fa.startService(contactIntent);
	}

	public void onEvent(String event) {
//		System.out.println("Event = " + event);
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				fillData();
				getListView().invalidate();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.news_menuRefreshUsers:
				this.startRequest();
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroyView() {
		EventBus.getDefault().unregister(this);
		super.onDestroyView();
	}
}
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
package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.dao.UserDAO;
import com.akelio.android.acollab.entity.User;

public class FragmentListUsers extends ListFragment {
	OnUserSelectedListener	mCallback;
	private UserDAO			userDAO;
	private boolean 		dualPanel;
	
	public interface OnUserSelectedListener {
		public void onUserSelected(int position, ListView l);
	}
	
	public static FragmentListUsers newInstance() {
		FragmentListUsers frag = new FragmentListUsers();
		Bundle args = new Bundle();
		frag.setArguments(args);
	return frag;
	}
	


	@Override
	public void onStart() {
		super.onStart();
		if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HashMap obj =(HashMap) l.getItemAtPosition(position);
    	String idValue = (String) obj.get("textViewInvisible");
		FragmentUserDetails detailFragment = FragmentUserDetails.newInstance(idValue);
		if (!this.dualPanel) {
			FragmentHelper.initFragmentWithBackstack(detailFragment,
					R.id.usermain_fragment, this.getParentFragment()
							.getChildFragmentManager());
			
		} else {
			FragmentHelper.initFragment(detailFragment,
					R.id.userdetail_fragment, this.getParentFragment()
							.getChildFragmentManager());
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.friendlist, container, false);
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

		SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItem, R.layout.list_user_item, new String[] { "textViewName", "textViewCompanyName", "textViewNumber", "textViewInvisible" },
				new int[] { R.id.textViewName, R.id.textViewCompanyName, R.id.textViewNumber, R.id.textViewInvisible });
		this.setListAdapter(mSchedule);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.dualPanel = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}
	
	
}
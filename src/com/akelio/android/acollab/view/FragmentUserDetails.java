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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.dao.UserDAO;
import com.akelio.android.acollab.entity.User;

public class FragmentUserDetails extends Fragment {
	final static String	ARG_IDVALUE			= "idValue";
	int					mCurrentPosition	= -1;

	private String		idUser;

	private UserDAO		userDAO;

	private TextView	name;

	private TextView	company;

	private TextView	number;

	public static FragmentUserDetails newInstance() {
		FragmentUserDetails frag = new FragmentUserDetails();
		return frag;
	}

	public static FragmentUserDetails newInstance(String idUser) {
		FragmentUserDetails frag = new FragmentUserDetails();
		Bundle args = new Bundle();

		args.putString("idUser", idUser);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.fragment_details_user, container, false);
		name = (TextView) mainView.findViewById(R.id.textViewFirstname);
		company = (TextView) mainView.findViewById(R.id.textViewCompanyName);
		number = (TextView) mainView.findViewById(R.id.textViewNumber);

		this.idUser = (savedInstanceState == null) ? null : (String) savedInstanceState.getString("idUser");

		Bundle extras = getArguments();
		if (extras != null && this.idUser == null) {

			this.idUser = extras.getString("idUser");
		}

		if (this.idUser != null) {
			fillData(this.idUser);
		}

		return mainView;
	}

	private void fillData(String uri) {

		userDAO = new UserDAO(getActivity());
		User user = userDAO.getUser(uri);
		userDAO.close();
		name.setText(user.getUsername());
		number.setText(user.getPhone1());
		company.setText(user.getCompany());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_IDVALUE, mCurrentPosition);
	}
}
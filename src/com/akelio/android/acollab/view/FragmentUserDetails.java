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

	private UserDAO		userDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		userDAO = new UserDAO(getActivity());
		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_IDVALUE);
		}

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_details_user, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already been
		// applied to the fragment at this point so we can safely call the method
		// below that sets the article text.
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			updateDetailsUserView(args.getString(ARG_IDVALUE));
			// } else if (mCurrentPosition != -1) {
			// Set article based on saved instance state defined during onCreateView
			// updateDetailsUserView(mCurrentPosition);
		}
	}

	public void updateDetailsUserView(String idValue) {
		TextView name = (TextView) getActivity().findViewById(R.id.textViewFirstname);
		TextView company = (TextView) getActivity().findViewById(R.id.textViewCompanyName);
		TextView number = (TextView) getActivity().findViewById(R.id.textViewNumber);
		System.out.println(idValue);
		User user = userDAO.getUser(idValue);
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
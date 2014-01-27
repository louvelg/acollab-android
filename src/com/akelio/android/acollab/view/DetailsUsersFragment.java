package com.akelio.android.acollab.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akelio.android.acollab.R;

public class DetailsUsersFragment extends Fragment {

	private TextView firstName;
	public static String TAG="fragmentDetailsUsersFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_details, container,
				false);
		firstName = (TextView) view.findViewById(R.id.textViewFirstname);
		return view;
	}

	public void setText(String item) {
		// TextView view = (TextView) getView().findViewById(R.id.text);
		// view.setText(item);
	}

	public void updateView(long id) {
		firstName.setText("id : " + id);
	}
}

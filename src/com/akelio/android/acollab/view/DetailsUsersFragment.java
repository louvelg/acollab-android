package com.akelio.android.acollab.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akelio.android.acollab.R;

public class DetailsUsersFragment extends Fragment{
	
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_details, container, false);
	    return view;
	  }

	  public void setText(String item) {
	    //TextView view = (TextView) getView().findViewById(R.id.text);
	    //view.setText(item);
	  }
}

package com.akelio.android.acollab.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.entity.Space;

public class SpaceBoardFragment extends Fragment implements android.view.View.OnClickListener {

	private Space	space;

	public SpaceBoardFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_space_board, container, false);
		Button projectButton = (Button) view.findViewById(R.id.buttonProject);
		projectButton.setOnClickListener(this);
		space = (Space) getArguments().getSerializable("space");
		System.out.println(space.getName() + " " + space.getSpaceId());
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonProject:
				System.out.println("clic buttonProject");
				getFragmentManager().beginTransaction().replace(R.id.content_frame, new ActivityStreamFragment()).commit();
				break;
		}
	}
}

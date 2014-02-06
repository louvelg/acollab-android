package com.akelio.android.acollab.view;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.dao.UserDAO;
import com.akelio.android.acollab.entity.User;

import android.graphics.Path.FillType;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendDetailFragment extends Fragment {

	private String detailUri;

	private TextView name;

	private TextView company;
	
	private TextView number; 

	private ImageView faceField;

	private UserDAO		userDAO;
	/**
	 * @return
	 */
	public static FriendDetailFragment newInstance() {
		FriendDetailFragment frag = new FriendDetailFragment();
		return frag;
	}

	/**
	 * @param detailUri
	 * @return
	 */
	public static FriendDetailFragment newInstance(String detailUri) {
		FriendDetailFragment frag = new FriendDetailFragment();
		Bundle args = new Bundle();
		
		args.putString("detailUri", detailUri);
		frag.setArguments(args);
		return frag;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		View mainView = inflater.inflate(R.layout.frienddetail, container,
				false);
		name = (TextView) mainView.findViewById(R.id.friend_name_label);
		company = (TextView) mainView.findViewById(R.id.friend_name_value);
		number = (TextView) mainView.findViewById(R.id.friend_job);
//		this.nameField = (TextView) mainView
//				.findViewById(R.id.friend_name_value);
//		this.jobField = (TextView) mainView.findViewById(R.id.friend_job_value);
//		this.faceField = (ImageView) mainView
//				.findViewById(R.id.friend_face_value);
//
//	this.detailUri = (savedInstanceState == null) ? null
//				: (Uri) savedInstanceState
//						.getParcelable("detailUri");

		Bundle extras = getArguments();
		if (extras != null && this.detailUri == null) {

			this.detailUri = extras
					.getString("detailUri");
		}

		if (this.detailUri != null) {
			fillData(this.detailUri);
		}

		return mainView;
	}

	/**
	 * @param uri
	 */
	private void fillData(String uri) {
		
		//String idValue = uri.toString();
		userDAO = new UserDAO(getActivity());
		User user = userDAO.getUser(uri);
		userDAO.close();
		name.setText(user.getUsername());
		number.setText(user.getPhone1());
		company.setText(user.getCompany());
	}

	/**
	 * @param selectedItemUri
	 */
	//public void update(Uri selectedItemUri) {
	//	this.detailUri = selectedItemUri;
	//	fillData(this.detailUri);
	//}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//		outState.putParcelable(FriendContentProvider.CONTENT_ITEM_TYPE,
//				this.detailUri);
	}

	public String getDetailUri() {
		return detailUri;
	}
}
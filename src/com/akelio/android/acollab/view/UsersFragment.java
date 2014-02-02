package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.UserContract;
import com.akelio.android.acollab.db.DbHelper;

public class UsersFragment extends ListFragment {

	public static String		TAG	= "fragmentUsersFragment";
	OnArticleSelectedListener	mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_users, container, false);
		final ListView listview = (ListView) view.findViewById(R.id.list_fragment);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		DbHelper dbHelper = new DbHelper(view.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, null, null, null, null, null, UserContract.DEFAULT_SORT);
		if (cursor != null) cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			map = new HashMap<String, String>();
			map.put("textViewName", cursor.getString(2));

			map.put("textViewDescription", cursor.getString(3));
			listItem.add(map);
			cursor.moveToNext();
		}

		db.close();

		SimpleAdapter mSchedule = new SimpleAdapter(view.getContext(), listItem, R.layout.list_user_item, new String[] { "textViewName", "textViewDescription" }, new int[] { R.id.textViewName,
				R.id.textViewDescription });
		listview.setAdapter(mSchedule);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				// List<Fragment> list = getFragmentManager().getFragments();

				// mDetailsUserFragment = new DetailsUsersFragment();
				// if(mDetailsUserFragment !=null ){

				// FragmentTransaction ft = getFragmentManager().beginTransaction();
				// ft.replace(R.id.frameLayoutView, mDetailsUserFragment);
				// ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				// ft.commit();
				// }

				// mListener.onArticleSelected();

			}
		});
		return view;
	}

	// Container Activity must implement this interface
	public interface OnArticleSelectedListener {
		public void onArticleSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnArticleSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}
	}

}

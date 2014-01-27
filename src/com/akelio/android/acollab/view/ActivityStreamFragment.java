package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.ActivityContract;
import com.akelio.android.acollab.db.DbHelper;

public class ActivityStreamFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_activity_stream,
				container, false);
		final ListView listview = (ListView) view
				.findViewById(R.id.listViewActivityStream);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(ActivityContract.TABLE);
		DbHelper dbHelper = new DbHelper(view.getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, null, null, null, null, null,
				ActivityContract.DEFAULT_SORT);
		if (cursor != null)
			cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			System.out.println("Title : " + cursor.getString(cursor
					.getColumnIndex(ActivityContract.Column.TITLE)));
			System.out.println("Desc : " + cursor.getString(cursor
					.getColumnIndex(ActivityContract.Column.USER_NAME)));
			map = new HashMap<String, String>();
			map.put("textViewActivityName", cursor.getString(cursor
					.getColumnIndex(ActivityContract.Column.TITLE)));
			map.put("textViewActivityDescription", cursor.getString(cursor
					.getColumnIndex(ActivityContract.Column.USER_NAME)));
			listItem.add(map);
			cursor.moveToNext();
		}

		db.close();

		SimpleAdapter mSchedule = new SimpleAdapter(view.getContext(),
				listItem, R.layout.list_activity_stream,
				new String[] { "textViewActivityName",
						"textViewActivityDescription" }, new int[] {
						R.id.textViewActivityName,
						R.id.textViewActivityDescription });
		listview.setAdapter(mSchedule);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Toast.makeText(v.getContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
			}
		});

		return view;
	}

	public void setText(String item) {
	}
}

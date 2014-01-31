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
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.UserContract;
import com.akelio.android.acollab.db.DbHelper;

public class UsersFrag extends ListFragment {
    OnUserSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnUserSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onUserSelected(int position, View view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        //-----------------CONNEXION DATABASE------------------
        //View view = inflater.inflate(R.layout.list_users, container, false);
        Context mContext = getActivity();
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		DbHelper dbHelper = new DbHelper(mContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, null, null, null, null, null,
				UserContract.DEFAULT_SORT);
		if (cursor != null)
			cursor.moveToFirst();

		while (!cursor.isAfterLast()) {


			map = new HashMap<String, String>();
			map.put("textViewName", cursor.getString(cursor.getColumnIndex(UserContract.Column.FIRST_NAME))+" "+cursor.getString(cursor.getColumnIndex(UserContract.Column.LAST_NAME)));
			map.put("textViewCompanyName", cursor.getString(cursor.getColumnIndex(UserContract.Column.COMPANY)) );
			map.put("textViewNumber", cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE1)) );
			map.put("textViewInvisible", cursor.getString(cursor.getColumnIndex(UserContract.Column.ID)) );
			listItem.add(map);
			cursor.moveToNext();
		}
		//-------------------------------------------------------
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        
        SimpleAdapter mSchedule = new SimpleAdapter(mContext,
				listItem, R.layout.list_user_item, new String[] {
						"textViewName", "textViewCompanyName", "textViewNumber", "textViewInvisible" }, new int[] {
						R.id.textViewName, R.id.textViewCompanyName, R.id.textViewNumber, R.id.textViewInvisible });
        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(mSchedule);
        
        //setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnUserSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onUserSelected(position, v);
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }
}
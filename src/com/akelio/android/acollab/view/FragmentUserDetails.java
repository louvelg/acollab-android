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

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.UserContract;
import com.akelio.android.acollab.db.DbHelper;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFrag extends Fragment {
    final static String ARG_IDVALUE = "idValue";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_IDVALUE);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details_view, container, false);
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
       // 	updateDetailsUserView(mCurrentPosition);
        }
    }

    public void updateDetailsUserView(String idValue) {
        //TextView username = (TextView) getActivity().findViewById(R.id.user);
        //username.setText(Ipsum.Articles[position]);
        //mCurrentPosition = position;
        //-----------------CONNEXION DATABASE------------------
        //View view = inflater.inflate(R.layout.list_users, container, false);
    	TextView name = (TextView)  getActivity().findViewById(R.id.textViewFirstname);
    	TextView company = (TextView)  getActivity().findViewById(R.id.textViewCompanyName);
    	TextView number = (TextView)  getActivity().findViewById(R.id.textViewNumber);
        Context mContext = getActivity();
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        System.out.println(idValue);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		DbHelper dbHelper = new DbHelper(mContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, null, "_id="+idValue, null, null, null,
				UserContract.DEFAULT_SORT);
		if (cursor != null)
			cursor.moveToFirst();

		//while (!cursor.isAfterLast()) {
			
			
			
			name.setText(cursor.getString(cursor.getColumnIndex(UserContract.Column.FIRST_NAME))+" "+cursor.getString(cursor.getColumnIndex(UserContract.Column.LAST_NAME)));
			number.setText(cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE1)));
			company.setText(cursor.getString(cursor.getColumnIndex(UserContract.Column.COMPANY)));
			//map = new HashMap<String, String>();
			//map.put("textViewName", cursor.getString(cursor.getColumnIndex(UserContract.Column.FIRST_NAME))+" "+cursor.getString(cursor.getColumnIndex(UserContract.Column.LAST_NAME)));
			//map.put("textViewCompanyName", cursor.getString(cursor.getColumnIndex(UserContract.Column.COMPANY)) );
			//map.put("textViewNumber", cursor.getString(cursor.getColumnIndex(UserContract.Column.PHONE1)) );
			//map.put("textViewInvisible", cursor.getString(cursor.getColumnIndex(UserContract.Column.ID)) );
			//listItem.add(map);
			//cursor.moveToNext();
		//}
		//-------------------------------------------------------<
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_IDVALUE, mCurrentPosition);
    }
}
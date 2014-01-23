package com.akelio.android.acollab.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.UserContract;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.UserListItem;

public class UsersActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
		final ListView listview = (ListView) findViewById(R.id.listViewUsers);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
				"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
				"Android", "iPhone", "WindowsMobile" };

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
//			list.add(values[i]);
		}

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(UserContract.TABLE);
		DbHelper dbHelper = new DbHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, null, null, null, null, null,
				UserContract.DEFAULT_SORT);

		if (cursor != null)
			cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			System.out.println(cursor.getInt(0));

			System.out.println(cursor.getString(1));
			System.out.println(cursor.getString(2));
			System.out.println(cursor.getString(3));
			list.add(cursor.getString(2) + " " + cursor.getString(3));
			cursor.moveToNext();
		}

		db.close();

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

//		LoadWebPageASYNC task = new LoadWebPageASYNC();
//		task.execute();
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	private class LoadWebPageASYNC extends AsyncTask<String, Void, UserListItem> {

		@Override
		protected UserListItem doInBackground(String... urls) {
			String url = "http://geb.test1.acollab.com/rest/v1/users/user/2";
			System.out.println("url : " + url);

			try {
				HttpAuthentication authHeader = new HttpBasicAuthentication(
						"admin", "admin");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				HttpEntity<?> requestEntity = new HttpEntity<Object>(
						requestHeaders);

				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new GsonHttpMessageConverter());
				ResponseEntity<UserListItem> response2 = restTemplate.exchange(url,
						HttpMethod.GET, requestEntity, UserListItem.class);

				System.out.println("url : "
						+ response2.getBody().getFirstName());

				return response2.getBody();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(UserListItem result) {
			super.onPostExecute(result);
			Toast.makeText(UsersActivity.this,
					result.getFirstName() + " - " + result.getLastName(),
					Toast.LENGTH_LONG).show();
			;
		}
	}


}

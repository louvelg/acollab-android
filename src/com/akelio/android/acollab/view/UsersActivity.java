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
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.akelio.android.acollab.R;

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
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		LoadWebPageASYNC task = new LoadWebPageASYNC();
		task.execute();
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

	private class LoadWebPageASYNC extends AsyncTask<String, Void, UserItem> {

		@Override
		protected UserItem doInBackground(String... urls) {
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
				ResponseEntity<UserItem> response2 = restTemplate.exchange(url,
						HttpMethod.GET, requestEntity, UserItem.class);

				System.out.println("url : "
						+ response2.getBody().getFirstName());

				return response2.getBody();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(UserItem result) {
			super.onPostExecute(result);
			Toast.makeText(UsersActivity.this,
					result.getFirstName() + " - " + result.getLastName(),
					Toast.LENGTH_LONG).show();
			;
		}
	}

	private class UserItem {
		private String tenantId;
		private String userId;
		private String lastName;
		private String firstName;
		private String phone1;
		private String phone2;
		private String company;

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getPhone1() {
			return phone1;
		}

		public void setPhone1(String phone1) {
			this.phone1 = phone1;
		}

		public String getPhone2() {
			return phone2;
		}

		public void setPhone2(String phone2) {
			this.phone2 = phone2;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

	}
}

package com.akelio.android.acollab.service;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akelio.android.acollab.contract.UserContract;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.UserListItem;

public class ContactService extends IntentService {

	static final String TAG = "contactService";

	public ContactService() {
		super(TAG);
		Log.d(TAG, "contactService start");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "contactService launched");

//		String url = "http://geb.test1.acollab.com/rest/v1/users/user/2";
//		System.out.println("url : " + url);

		try {
			HttpAuthentication authHeader = new HttpBasicAuthentication(
					"admin", "admin");
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAuthorization(authHeader);
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getMessageConverters().add(
//					new GsonHttpMessageConverter());
//			ResponseEntity<UserListItem> response2 = restTemplate.exchange(url,
//					HttpMethod.GET, requestEntity, UserListItem.class);
//			System.out
//					.println("contact : " + response2.getBody().getUsername());
//
//			ResponseEntity<UserListItem> response = restTemplate.exchange(url,
//					HttpMethod.GET, requestEntity, UserListItem.class);

			String url = "http://geb.test1.acollab.com/rest/v1/1/users";
			restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new GsonHttpMessageConverter());
			// restTemplate.getMessageConverters().add(new
			// StringHttpMessageConverter());

			// ResponseEntity<String> response3 = restTemplate.exchange(url,
			// HttpMethod.GET, requestEntity, String.class);

			// String result = restTemplate.getForObject(url, String.class);
			// System.out.println(response3.getBody());

			
			DbHelper dbHelper = new DbHelper(this);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			// db.execSQL("drop table if exists " + UserContract.TABLE);

			ResponseEntity<UserListItem[]> res = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, UserListItem[].class);
			UserListItem[] ulis = res.getBody();
			for (int i = 0; i < ulis.length; i++) {
				UserListItem u = ulis[i];
				System.out.println(u.getUsername());
				values.clear(); //
				values.put(UserContract.Column.ID, u.getUserId());
				values.put(UserContract.Column.FIRST_NAME, u.getFirstName());
				values.put(UserContract.Column.LAST_NAME, u.getLastName());
				values.put(UserContract.Column.PHONE1, u.getPhone1());
				values.put(UserContract.Column.PHONE2, u.getPhone2());
				values.put(UserContract.Column.TENANT_ID, u.getTenantId());
				values.put(UserContract.Column.COMPANY, u.getCompany());
				db.insertWithOnConflict(UserContract.TABLE, null, values,
						SQLiteDatabase.CONFLICT_REPLACE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "contactService stop");
	}

}

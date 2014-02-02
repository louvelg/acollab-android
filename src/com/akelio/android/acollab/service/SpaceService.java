package com.akelio.android.acollab.service;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import com.akelio.android.acollab.contract.SpaceContract;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.Space;
import com.akelio.android.acollab.utils.NetworkUtils;

public class SpaceService extends IntentService {

	static final String	TAG	= "spaceService";
	static final String	URL	= "http://geb.test1.acollab.com/rest/v1/1/user/spaces";

	public SpaceService() {
		super(TAG);
		Log.d(TAG, "spaceService start");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "spaceService launched");

		if (NetworkUtils.isNetworkReachable(getApplicationContext())) {
			String login = "admin";
			String password = "admin";
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			password = prefs.getString("password", "admin");
			login = prefs.getString("login", "admin");
			try {
				HttpAuthentication authHeader = new HttpBasicAuthentication(login, password);
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

				ResponseEntity<Space[]> res = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, Space[].class);
				Space[] ulis = res.getBody();

				DbHelper dbHelper = new DbHelper(this);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();

				db.execSQL("delete from " + SpaceContract.TABLE);
				for (int i = 0; i < ulis.length; i++) {
					Space u = ulis[i];
					values.clear(); //
					values.put(SpaceContract.Column.ID, u.getSpaceId());
					values.put(SpaceContract.Column.NAME, u.getName());
					values.put(SpaceContract.Column.TENANT_ID, u.getTenantId());
					values.put(SpaceContract.Column.APPLICATIONS, StringUtils.collectionToCommaDelimitedString(u.getApplications()));
					db.insertWithOnConflict(SpaceContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				}
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "spaceService no network reachable");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "spaceService stop");
	}
}

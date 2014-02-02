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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.akelio.android.acollab.R;
import com.akelio.android.acollab.contract.ActivityContract;
import com.akelio.android.acollab.db.DbHelper;
import com.akelio.android.acollab.entity.Activity;
import com.akelio.android.acollab.utils.NetworkUtils;
import com.akelio.android.acollab.view.ActivityStreamActivity;

public class ActivityStreamService extends IntentService {

	static final String	TAG	= "activityStreamService";
	static final String	URL	= "http://geb.test1.acollab.com/rest/v1/news";

	public ActivityStreamService() {
		super(TAG);
		Log.d(TAG, "activityStreamService start");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "activityStreamService launched");
		System.out.println("activityStreamService launched");

		if (NetworkUtils.isNetworkReachable(getApplicationContext())) {
			try {
				HttpAuthentication authHeader = new HttpBasicAuthentication("admin", "admin");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

				RestTemplate restTemplate = new RestTemplate();
				restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

				ResponseEntity<Activity[]> res = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, Activity[].class);
				Activity[] ulis = res.getBody();
				
				DbHelper dbHelper = new DbHelper(this);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();

				db.execSQL("delete from " + ActivityContract.TABLE);

				int i = 0;
				for (i = 0; i < ulis.length; i++) {
					Activity a = ulis[i];
					values.clear(); //
					values.put(ActivityContract.Column.ID, a.getActivityId());
					values.put(ActivityContract.Column.TENANT_ID, a.getTenantId());
					values.put(ActivityContract.Column.USER_ID, a.getUserId());
					values.put(ActivityContract.Column.SPACE_ID, a.getUserId());
					values.put(ActivityContract.Column.SPACE_NAME, a.getSpaceName());
					values.put(ActivityContract.Column.TITLE, a.getTitle());
					values.put(ActivityContract.Column.USER_NAME, a.getUserName());
					values.put(ActivityContract.Column.DATE_CREATED, a.getDateCreated());
					values.put(ActivityContract.Column.MODULE_ID, a.getModuleId());
					values.put(ActivityContract.Column.MODULE_TYPE, a.getModuleType());
					values.put(ActivityContract.Column.DATE_CREATED, a.getDateCreated());

					db.insertWithOnConflict(ActivityContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				}
				System.out.println("Nb activity : " + i);
				db.close();

				NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Intent launchIntent = new Intent(getApplicationContext(), ActivityStreamActivity.class);
				PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, 0);

				// Create notification with the time it was fired
				Notification note = new Notification(R.drawable.logo_acollab_android_24, "Something Happened", System.currentTimeMillis());
				// Set notification information
				note.setLatestEventInfo(getApplicationContext(), "We're Finished!", "Click Here!", contentIntent);
				note.defaults |= Notification.DEFAULT_SOUND;
				note.flags |= Notification.FLAG_AUTO_CANCEL;

				manager.notify(15, note);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "activityStreamService no network reachable");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "activityStreamService stop");
	}

}

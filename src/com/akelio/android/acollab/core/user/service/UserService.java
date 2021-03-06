package com.akelio.android.acollab.core.user.service;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.akelio.android.acollab.core.user.dao.UserDAO;
import com.akelio.android.acollab.entity.User;
import com.akelio.android.acollab.utils.NetworkUtils;
import de.greenrobot.event.EventBus;

public class UserService extends IntentService {

	static final String	TAG	= "contactService";
	static final String	URL	= "http://geb.test1.acollab.com/rest/v1/1/users";
	private UserDAO		userDAO;

	public UserService() {
		super(TAG);
		Log.d(TAG, "contactService start");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "contactService launched");
		if (NetworkUtils.isNetworkReachable(getApplicationContext())) {
			userDAO = new UserDAO(this);
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

//				DbHelper dbHelper = new DbHelper(this);
//				ContentValues values = new ContentValues();

				ResponseEntity<User[]> res = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, User[].class);
				User[] ulis = res.getBody();
//				SQLiteDatabase db = dbHelper.getWritableDatabase();
				userDAO.deleteAllUser();
				
				for (int i = 0; i < ulis.length; i++) {
					// System.out.println(u.getUsername());
					userDAO.createUser(ulis[i]);
//					values.clear();
//					values.put(UserContract.Column.ID, u.getUserId());
//					values.put(UserContract.Column.FIRST_NAME, u.getFirstName());
//					values.put(UserContract.Column.LAST_NAME, u.getLastName());
//					values.put(UserContract.Column.PHONE1, u.getPhone1());
//					values.put(UserContract.Column.PHONE2, u.getPhone2());
//					values.put(UserContract.Column.TENANT_ID, u.getTenantId());
//					values.put(UserContract.Column.COMPANY, u.getCompany());
//					db.insertWithOnConflict(UserContract.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				}
				userDAO.close();
				EventBus.getDefault().post(new String("refresh user service"));
//				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "contactService no wifi reachable");
		}
	}

	
	
	@Override
	public void onCreate() {
		super.onCreate();
//		EventBus.getDefault().register(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		EventBus.getDefault().unregister(this);
		Log.d(TAG, "contactService stop");
	}
}

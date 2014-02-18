package com.akelio.android.acollab.view;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.akelio.android.acollab.dao.UserDAO;
import com.akelio.android.acollab.entity.User;
import com.akelio.android.acollab.utils.NetworkUtils;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class RefreshUsers extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
	
		return null;
	}

}

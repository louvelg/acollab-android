package com.akelio.android.acollab.view;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.service.ActivityStreamService;
import com.akelio.android.acollab.service.ContactService;

public class MainActivity extends Activity implements android.view.View.OnClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button buttonSend = (Button) findViewById(R.id.buttonLogin);
		final Button buttonUsers = (Button) findViewById(R.id.buttonUsers);
		final Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
		final Button buttonActivityStream = (Button) findViewById(R.id.buttonActivityStream);
		final Button buttonRefreshContactService = (Button) findViewById(R.id.buttonRefrechContactService);
		final Button buttonRefreshActivityStreamService = (Button) findViewById(R.id.buttonRefrechActivityStreamService);
		final EditText editLogin = (EditText) findViewById(R.id.editTextLogin);
		final EditText editPasswd = (EditText) findViewById(R.id.editTextPasswd);
		buttonSend.setOnClickListener(this);
		buttonUsers.setOnClickListener(this);
		buttonSettings.setOnClickListener(this);
		buttonActivityStream.setOnClickListener(this);
		buttonRefreshContactService.setOnClickListener(this);
		buttonRefreshActivityStreamService.setOnClickListener(this);
		
	}

	private class LoadWebPageASYNC extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String url = "https://ajax.googleapis.com/ajax/"
					+ "services/search/web?v=1.0&q={query}";
			System.out.println("url : " + url);

			try {
				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				String result = restTemplate.getForObject(url, String.class,
						"Android");
				System.out.println("url : " + result);

				url = "http://geb.test1.acollab.com/rest/v1/login";

				HttpAuthentication authHeader = new HttpBasicAuthentication(
						"admin", "admin");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAuthorization(authHeader);
				HttpEntity<?> requestEntity = new HttpEntity<Object>(
						requestHeaders);

				restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				ResponseEntity<String> response = restTemplate.exchange(url,
						HttpMethod.GET, requestEntity, String.class);
				System.out.println("url : " + response.getBody());

				restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new GsonHttpMessageConverter());
				ResponseEntity<Login> response2 = restTemplate.exchange(url,
						HttpMethod.GET, requestEntity, Login.class);
				System.out
						.println("url : " + response2.getBody().getTenantId());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

		}
	}

	@Override
	// test
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class Login {
		private String tenantId;
		private String userId;

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
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.buttonUsers:
				System.out.println("clic buttonUsers");
				String frag = "UsersFragment";
				Intent i = new Intent(MainActivity.this, UsersActivity.class);
				i.putExtra("fragment", frag);
				startActivity(i);
				break;
			case R.id.buttonActivityStream:
				System.out.println("Start buttonActivityStream");
				startActivity(new Intent(MainActivity.this, ActivityStreamActivity.class));
				break;
			case R.id.buttonSettings:
				System.out.println("Start buttonSettings");
				startActivity(new Intent(MainActivity.this, SettingsActivity.class));
				break;
			case R.id.buttonRefrechContactService:
				System.out.println("Start buttonRefrechContactService");
				startService(new Intent(this, ContactService.class));
				break;
			case R.id.buttonRefrechActivityStreamService:
				System.out.println("Start buttonRefrechActivityStreamService");
				startService(new Intent(this, ActivityStreamService.class));
				break;
			case R.id.buttonLogin:
				System.out.println("Start buttonLogin");
				LoadWebPageASYNC task = new LoadWebPageASYNC();
				task.execute(new String[] { "http://www.javacodegeeks.com" });
				break;
		}
	}
}

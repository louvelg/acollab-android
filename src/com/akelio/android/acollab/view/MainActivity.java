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
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.akelio.android.acollab.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button buttonSend = (Button) findViewById(R.id.buttonLogin);
		final EditText editLogin = (EditText) findViewById(R.id.editTextLogin);
		final EditText editPasswd = (EditText) findViewById(R.id.editTextPasswd);
		buttonSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				String url = "https://ajax.googleapis.com/ajax/"
						+ "services/search/web?v=1.0&q={query}";
				System.out.println("url : " + url);

				try {
					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate();
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					String result = restTemplate.getForObject(url,
							String.class, "Android");
					System.out.println("url : " + result);

					url = "https://geb.test1.acollab.com/rest/v1/login";

					HttpAuthentication authHeader = new HttpBasicAuthentication(
							"admin", "admin");
					HttpHeaders requestHeaders = new HttpHeaders();
					requestHeaders.setAuthorization(authHeader);
					HttpEntity<?> requestEntity = new HttpEntity<Object>(
							requestHeaders);

					restTemplate = new RestTemplate();
					restTemplate.getMessageConverters().add(
							new StringHttpMessageConverter());
					ResponseEntity<String> response = restTemplate.exchange(
							url, HttpMethod.GET, requestEntity, String.class);
					System.out.println("url : " + response.getBody());

					restTemplate = new RestTemplate();
					restTemplate.getMessageConverters().add(
							new GsonHttpMessageConverter());
					ResponseEntity<Login> response2 = restTemplate.exchange(
							url, HttpMethod.GET, requestEntity, Login.class);
					System.out.println("url : "
							+ response2.getBody().getTenantId());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
}

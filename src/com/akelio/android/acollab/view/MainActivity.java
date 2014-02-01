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
import com.akelio.android.acollab.service.SpaceService;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button buttonUsers = (Button) findViewById(R.id.buttonUsers);
		final Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
		final Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
		final Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
		final Button buttonActivityStream = (Button) findViewById(R.id.buttonActivityStream);
		final Button buttonRefreshContactService = (Button) findViewById(R.id.buttonRefrechContactService);
		final Button buttonRefreshSpaceService = (Button) findViewById(R.id.buttonRefrechSpaceService);
		final Button buttonRefreshActivityStreamService = (Button) findViewById(R.id.buttonRefrechActivityStreamService);
		buttonUsers.setOnClickListener(this);
		buttonMenu.setOnClickListener(this);
		buttonSettings.setOnClickListener(this);
		buttonActivityStream.setOnClickListener(this);
		buttonRefreshContactService.setOnClickListener(this);
		buttonRefreshSpaceService.setOnClickListener(this);
		buttonRefreshActivityStreamService.setOnClickListener(this);
		buttonLogin.setOnClickListener(this);
	}

	@Override
	// test
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonLogin:
				Intent iLogin = new Intent(MainActivity.this, ActivityLogin.class);
				startActivity(iLogin);
				break;
			case R.id.buttonUsers:
				System.out.println("clic buttonUsers");
				String frag = "UsersFragment";
				Intent i = new Intent(MainActivity.this, ActivityListUsers.class);
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
			case R.id.buttonMenu:
				System.out.println("Start buttonMenu");
				startActivity(new Intent(MainActivity.this, MenuTestActivity.class));
				break;
			case R.id.buttonRefrechContactService:
				System.out.println("Start buttonRefrechContactService");
				startService(new Intent(this, ContactService.class));
				break;
			case R.id.buttonRefrechSpaceService:
				System.out.println("Start buttonRefrechSpaceService");
				startService(new Intent(this, SpaceService.class));
				break;
			case R.id.buttonRefrechActivityStreamService:
				System.out.println("Start buttonRefrechActivityStreamService");
				startService(new Intent(this, ActivityStreamService.class));
				break;
			
		}
	}
}

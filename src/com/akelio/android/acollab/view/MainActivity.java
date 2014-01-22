package com.akelio.android.acollab.view;

import org.apache.http.auth.AuthScope;

import com.akelio.android.acollab.R;
import com.akelio.android.acollab.R.layout;
import com.akelio.android.acollab.R.menu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
			public void onClick(View view){
				
				AsyncHttpClient client = new AsyncHttpClient();
				client.setBasicAuth(editLogin.getText().toString(),editPasswd.getText().toString(), new AuthScope("https://acollab-android.acollab.com/rest/v1/login", 443, null));
				client.get("https://acollab-android.acollab.com/rest/v1/login", new AsyncHttpResponseHandler() {
				    @Override
				    public void onSuccess(String response) {
				       System.out.println(response);
				    }
				});
			}
		});
	}

	@Override
	//test
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

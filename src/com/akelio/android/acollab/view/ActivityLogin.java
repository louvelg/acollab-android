package com.akelio.android.acollab.view;

import org.springframework.util.StringUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.akelio.android.acollab.R;

public class ActivityLogin extends Activity implements android.view.View.OnClickListener {

	EditText	login;
	EditText	password;
	Button		buttonSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (hasLoginInfo()) {
			startActivity(new Intent(ActivityLogin.this, MainActivity.class));
			return;
		}

		setContentView(R.layout.activity_login);

		login = (EditText) findViewById(R.id.editTextLogin);
		password = (EditText) findViewById(R.id.editTextPasswd);
		buttonSend = (Button) findViewById(R.id.buttonLogin);
		buttonSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonLogin:
				login(v);
				break;
		}
	}

	private boolean hasLoginInfo() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		if (StringUtils.hasText(prefs.getString("login", null)) && StringUtils.hasText(prefs.getString("password", null)) && StringUtils.hasText(prefs.getString("url", null))) { return true; }
		return false;
	}

	private void login(View v) {
		String l = login.getText().toString();
		String p = password.getText().toString();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = prefs.edit();
		if (StringUtils.hasText(l)) {
			editor.putString("login", l);
		}
		if (StringUtils.hasText(p)) {
			editor.putString("password", p);
		}
		editor.commit();
		startActivity(new Intent(ActivityLogin.this, MainActivity.class));
	}
}
package com.loop_anime.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.loop_anime.app.R;
import com.loop_anime.app.service.ServiceReceiver;
import com.loop_anime.app.service.UserService;

public class LoginActivity extends AbstractActivity implements View.OnClickListener {

	private AutoCompleteTextView mUsernameText;

	private EditText mPasswordText;

	private Button mEmailLoginButton;

	private ProgressBar mEmailLoadingProgressBar;


	public static void startActivity(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}

	@Override
	public boolean enableReceiver() {
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mUsernameText = (AutoCompleteTextView) findViewById(R.id.text_login_username);
		mPasswordText = (EditText) findViewById(R.id.text_login_password);

		//Prefill for test usage
		mUsernameText.setText("test");
		mPasswordText.setText("test");

		mEmailLoginButton = (Button) findViewById(R.id.email_login_button);
		mEmailLoadingProgressBar = (ProgressBar) findViewById(R.id.email_loading_progress_bar);
		mEmailLoginButton.setOnClickListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
			case ServiceReceiver.STATUS_ERROR:
				setEmailLoginButtonLoading(false);
				break;
			case ServiceReceiver.STATUS_FINISHED:
				MainActivity.startActivity(this);
				finish();
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.email_login_button:
				setEmailLoginButtonLoading(true);
				UserService.requestEmailLogin(this, mUsernameText.getText().toString(), mPasswordText.getText().toString(), mReceiver);
				break;
		}
	}

	private void setEmailLoginButtonLoading(boolean loading) {
		if (loading) {
			mEmailLoginButton.setClickable(false);
			mEmailLoadingProgressBar.setVisibility(View.VISIBLE);
		} else {
			mEmailLoginButton.setClickable(true);
			mEmailLoadingProgressBar.setVisibility(View.GONE);
		}
	}
}

package com.loop_anime.app.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loop_anime.app.R;
import com.loop_anime.app.ui.fragment.AnimeFragment;

public class AnimeActivity extends AbstractActivity {

	private static final String EXTRA_ANIME_SERVER_ID = "EXTRA_ANIME_SERVER_ID";

	private int animeServerId;

	public static void startActivity(Context context, int animeServerId, View view) {
		Intent intent = getIntent(context, animeServerId);
		if (view == null) {
			context.startActivity(intent);
		} else {
			Bundle bundle = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight()).toBundle();
			context.startActivity(intent, bundle);
		}
	}

	public static Intent getIntent(Context context, int serverId) {
		Intent intent = new Intent(context, AnimeActivity.class);
		intent.putExtra(EXTRA_ANIME_SERVER_ID, serverId);
		return intent;
	}

	@Override
	public boolean enableReceiver() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		animeServerId = getIntent().getIntExtra(EXTRA_ANIME_SERVER_ID, -1);
		setContentView(R.layout.activity_anime);
		setTitle("");
		if (savedInstanceState == null) {
			AnimeFragment animeFragment = new AnimeFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(EXTRA_ANIME_SERVER_ID, animeServerId);
			animeFragment.setArguments(bundle);
			getFragmentManager().beginTransaction()
					.add(R.id.container, animeFragment)
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.anime, menu);
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

}

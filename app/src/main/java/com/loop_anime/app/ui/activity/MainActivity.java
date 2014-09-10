package com.loop_anime.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loop_anime.app.R;
import com.loop_anime.app.User;
import com.loop_anime.app.service.UserService;
import com.loop_anime.app.ui.fragment.AnimesFragment;
import com.loop_anime.app.ui.fragment.EpisodesFragment;


public class MainActivity extends AbstractActivity implements ListView.OnItemClickListener {

	private static final int DRAWER_ANIMES = 0;

	private static final int DRAWER_EPISODES = 1;

	private String[] mDrawerTitles;

	private ListView mDrawerList;

	private DrawerLayout mDrawerLayout;

	private ActionBarDrawerToggle mDrawerToggle;

	private String mTitle;

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}

	@Override
	public boolean enableReceiver() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerTitles = getResources().getStringArray(R.array.drawer_array);
		mTitle = mDrawerTitles[0];
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_list);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerTitles));
		mDrawerList.setOnItemClickListener(this);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				setTitle(mTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				setTitle(getResources().getString(R.string.app_name));
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new AnimesFragment())
					.commit();
			setTitle(mDrawerTitles[0]);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		User user = User.instance(this);
		if (!user.isLoggedIn()) {
			LoginActivity.startActivity(this);
			finish();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		int id = item.getItemId();
		switch (id) {
			case R.id.action_settings:
				return true;
			case R.id.action_logout:
				UserService.logout(this, mReceiver);
				LoginActivity.startActivity(this);
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			case DRAWER_ANIMES:
				getFragmentManager().beginTransaction()
						.replace(R.id.container, new AnimesFragment())
						.commit();
				break;
			case DRAWER_EPISODES:
				getFragmentManager().beginTransaction()
						.replace(R.id.container, new EpisodesFragment())
						.commit();
				break;
			default:
				break;
		}
		mDrawerList.setSelection(position);
		mTitle = mDrawerTitles[position];
		setTitle(mTitle);
		mDrawerLayout.closeDrawers();
	}
}

package com.loop_anime.app.ui.activity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loop_anime.app.R;
import com.loop_anime.app.ui.fragment.AnimesFragment;
import com.loop_anime.app.ui.fragment.EpisodesFragment;


public class MainActivity extends AbstractActivity implements ListView.OnItemClickListener{

    private static final int DRAWER_ANIMES = 0;
    private static final int DRAWER_EPISODES = 1;
    private String[] mDrawerTitles;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    @Override
    public boolean enableReceiver() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerTitles));
        mDrawerList.setOnItemClickListener(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AnimesFragment())
                    .commit();
        }
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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
        setTitle(mDrawerTitles[position]);
        mDrawerLayout.closeDrawers();
    }
}

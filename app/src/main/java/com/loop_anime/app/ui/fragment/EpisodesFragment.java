package com.loop_anime.app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loop_anime.app.R;

/**
 * Created by allan on 14/8/6.
 */
public class EpisodesFragment extends AbstractFragment {

    private View mView;

    @Override
    public boolean enableReceiver() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_episodes, null, false);
        return mView;
    }
}

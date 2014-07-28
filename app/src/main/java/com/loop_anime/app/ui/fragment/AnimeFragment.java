package com.loop_anime.app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loop_anime.app.R;
import com.loop_anime.app.service.AnimeService;

/**
 * Created by allan on 14/7/28.
 */
public class AnimeFragment extends Fragment {

    private static final String LOG_TAG = AnimeFragment.class.getSimpleName();

    private static final String EXTRA_ANIME_SERVER_ID = "EXTRA_ANIME_SERVER_ID";

    private int animeServerId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            animeServerId = arguments.getInt(EXTRA_ANIME_SERVER_ID, -1);
        }
        if (animeServerId == -1) {
            throw new IllegalArgumentException("Anime Fragment don't receive Anime Server Id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_anime, container, false);
        TextView titleText = (TextView) rootView.findViewById(R.id.text_anime_title);
        titleText.setText(String.valueOf(animeServerId));
        return rootView;
    }
}

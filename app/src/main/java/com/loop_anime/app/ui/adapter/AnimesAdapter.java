package com.loop_anime.app.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.loop_anime.app.R;
import com.loop_anime.app.ui.activity.AbstractActivity;
import com.loop_anime.app.ui.fragment.AnimesFragment;
import com.loop_anime.app.util.ImageUtil;

/**
 * Created by allan on 14/7/27.
 */
public class AnimesAdapter extends CursorAdapter {


    public AnimesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.animes_list_item_row, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String titleStr = cursor.getString(AnimesFragment.COL_TITLE);
        viewHolder.titleText.setText(titleStr);
        viewHolder.subtitleText.setText(cursor.getString(AnimesFragment.COL_START_TIME));
        String imageUrlStr = cursor.getString(AnimesFragment.COL_POSTER);
        imageUrlStr = ImageUtil.getFullImageUrl(imageUrlStr);
        viewHolder.posterImage.setImageUrl(imageUrlStr, ((AbstractActivity)context).getImageLoaderMemoryCache());
        viewHolder.statusText.setText(cursor.getString(AnimesFragment.COL_STATUS));
    }



    public static class ViewHolder {

        NetworkImageView posterImage;
        TextView titleText;
        TextView subtitleText;
        TextView statusText;

        public ViewHolder(View view) {
            posterImage = (NetworkImageView) view.findViewById(R.id.image_anime_poster);
            titleText = (TextView) view.findViewById(R.id.text_anime_title);
            subtitleText = (TextView) view.findViewById(R.id.text_anime_description);
            statusText = (TextView) view.findViewById(R.id.text_anime_status);
        }
    }

}

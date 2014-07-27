package com.loop_anime.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.loop_anime.app.R;
import com.loop_anime.app.api.model.Anime;
import com.loop_anime.app.ui.fragment.AnimesFragment;
import com.loop_anime.app.util.BitmapLruCache;
import com.loop_anime.app.util.ImageUtil;

/**
 * Created by allan on 14/7/27.
 */
public class AnimesAdapter extends CursorAdapter {

    private ImageLoader.ImageCache imageCache;
    private ImageLoader imageLoader;

    public AnimesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        imageCache = new BitmapLruCache();
        imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
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
        String subTitleStr = cursor.getString(AnimesFragment.COL_PLOT_SUMMERY);
        viewHolder.subtitleText.setText(subTitleStr);
        String imageUrlStr = cursor.getString(AnimesFragment.COL_POSTER);
        imageUrlStr = ImageUtil.getFullImageUrl(imageUrlStr);
        viewHolder.posterImage.setDefaultImageResId(android.R.drawable.ic_menu_gallery);
        viewHolder.posterImage.setImageUrl(imageUrlStr, imageLoader);
    }



    public static class ViewHolder {

        NetworkImageView posterImage;
        TextView titleText;
        TextView subtitleText;

        public ViewHolder(View view) {
            posterImage = (NetworkImageView) view.findViewById(R.id.image_anime_poster);
            titleText = (TextView) view.findViewById(R.id.text_anime_title);
            subtitleText = (TextView) view.findViewById(R.id.text_anime_description);
        }
    }

}

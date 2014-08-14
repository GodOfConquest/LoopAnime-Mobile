package com.loop_anime.app.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by allan on 14/8/14.
 */
public class UiUtil {

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            return 0;
        }
    }
}

package com.loop_anime.app.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.loop_anime.app.ui.listener.NotifyingScrollViewListener;

/**
 * Created by allan on 14/8/13.
 */
public class NotifyingScrollView extends ScrollView {

    //Disable the overScroll Effects by default
    private boolean mIsOverScrollEnabled = false;

    private NotifyingScrollViewListener notifyingScrollViewListener = null;

    public NotifyingScrollView(Context context) {
        super(context);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (notifyingScrollViewListener != null) {
            notifyingScrollViewListener.onScrolled(this, l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setNotifyingScrollViewListener(NotifyingScrollViewListener notifyingScrollViewListener) {
        this.notifyingScrollViewListener = notifyingScrollViewListener;
    }

    public void setOverScrollEnabled(boolean enabled) {
        mIsOverScrollEnabled = enabled;
    }

    public boolean isOverScrollEnabled() {
        return mIsOverScrollEnabled;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy( deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                mIsOverScrollEnabled ? maxOverScrollX : 0, mIsOverScrollEnabled ? maxOverScrollY : 0, isTouchEvent);
    }
}

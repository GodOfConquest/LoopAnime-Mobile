package com.loop_anime.app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;

import com.loop_anime.app.service.ServiceReceiver;

/**
 * Created by yilun on 31/07/2014.
 */
public abstract class AbstractFragment extends Fragment implements ServiceReceiver.Receiver {

	ServiceReceiver mReceiver;

	public abstract boolean enableReceiver();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (enableReceiver()) {
			mReceiver = new ServiceReceiver(new Handler());
			mReceiver.setReceiver(this);
		}
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
	}

	;

}

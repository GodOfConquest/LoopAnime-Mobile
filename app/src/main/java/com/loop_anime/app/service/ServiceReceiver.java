package com.loop_anime.app.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by yilun on 31/07/2014.
 */
public class ServiceReceiver extends ResultReceiver {

	public static final int STATUS_RUNNING = 101;

	public static final int STATUS_FINISHED = 102;

	public static final int STATUS_ERROR = 103;

	public static final int STATUS_DATA = 104;

	public static final String EXTRA_DATA = "EXTRA_DATA";

	public static final String EXTRA_ERROR_MESSAGE = "ERROR_MESSAGE";

	public static final String EXTRA_RECEIVER = "EXTRA_RECEIVER";

	public static final String EXTRA_ORIGINAL_INTENT = "EXTRA_ORIGINAL_INTENT";

	public static final String EXTRA_ERROR_EXCEPTION_NAME = "EXTRA_ERROR_EXCEPTION_NAME";

	public static final String EXTRA_ERROR_CODE = "EXTRA_ERROR_CODE";

	private Receiver mReceiver;

	public ServiceReceiver(Handler handler) {
		super(handler);
	}

	public interface Receiver {

		public void onReceiveResult(int resultCode, Bundle resultData);
	}

	public void setReceiver(Receiver mReceiver) {
		this.mReceiver = mReceiver;
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		}
	}
}

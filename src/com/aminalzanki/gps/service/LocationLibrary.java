package com.aminalzanki.gps.service;

import java.lang.ref.WeakReference;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class LocationLibrary {

	public static final String TAG = LocationLibrary.class.getSimpleName();

	protected Context mContext;
	protected int mInterval = 1;

	protected GpsOutReceiver mReceiver;

	protected ILocationCallback mListener;

	// constructor
	public LocationLibrary(Context context, ILocationCallback listener) {
		super();
		this.mContext = context;
		this.mListener = listener;
		this.mReceiver = new GpsOutReceiver();

		// init BroadcastReceivr
		registerReceiver();
		startService();
	}

	// onDestroy
	public void onDestroy() {
		this.unregisterReceiver();
	}

	// startService
	private void startService() {
		Intent serviceIntent = new Intent(mContext, LocationService.class);
		serviceIntent.putExtra(LocationService.BROADCAST_INTERVAL, mInterval);
		mInterval = mInterval % 5 + 1;
		mContext.startService(serviceIntent);

	}

	// stopService
	private void stopService() {
		Intent serviceIntent = new Intent(mContext, LocationService.class);
		mContext.stopService(serviceIntent);
	}

	// isServiceRunning
	public boolean isServiceRunning() {
		ActivityManager manager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE))
			if (LocationService.class.getName().equals(
					service.service.getClassName()))
				return true;
		return false;
	}

	// startSpeedo
	public void startSpeedo() {
		Intent intent = new Intent();
		intent.setAction(BroadcastAction.ACTION_IN);
		intent.putExtra(BroadcastAction.EXTRA_SPEED_STATE, true);
		mContext.sendBroadcast(intent);
	}

	// stopSpeedo
	public void stopSpeedo() {
		Intent intent = new Intent();
		intent.setAction(BroadcastAction.ACTION_IN);
		intent.putExtra(BroadcastAction.EXTRA_SPEED_STATE, false);
		mContext.sendBroadcast(intent);
		stopService();
	}

	// GpsOutReceiver
	private class GpsOutReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extra = intent.getExtras();
			if (extra != null) {
				boolean state = extra
						.getBoolean(BroadcastAction.EXTRA_SPEED_STATE);
				if (state) {
					WeakReference<LocationModel> model = new WeakReference<LocationModel>(
							(LocationModel) intent
									.getSerializableExtra(BroadcastAction.EXTRA_MODEL));
					if (model.get() != null) {
						mListener.OnDataChanged(model.get());
					}
				}
			}

		}

	}

	// registerReceiver
	private void registerReceiver() {
		if (this.mReceiver != null) {
			IntentFilter filter = new IntentFilter(BroadcastAction.ACTION_OUT);
			this.mContext.registerReceiver(this.mReceiver, filter);
		}
	}

	// unregisterReceiver
	private void unregisterReceiver() {
		if (this.mReceiver != null) {
			this.mContext.unregisterReceiver(this.mReceiver);
		}
	}

}

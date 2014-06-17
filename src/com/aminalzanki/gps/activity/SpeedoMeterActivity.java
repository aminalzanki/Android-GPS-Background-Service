package com.aminalzanki.gps.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aminalzanki.gps.R;
import com.aminalzanki.gps.service.ILocationCallback;
import com.aminalzanki.gps.service.LocationLibrary;
import com.aminalzanki.gps.service.LocationModel;
import com.aminalzanki.gps.service.LocationService;
import com.aminalzanki.gps.utils.SpeedoMeterUtils;

public class SpeedoMeterActivity extends Activity implements ILocationCallback {

	private static final String TAG = SpeedoMeterActivity.class.getSimpleName();

	protected TextView mTotalDistance;

	protected LocationLibrary mLocationLibrary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speedometer);

		mTotalDistance = (TextView) findViewById(R.id.distance_value);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Init LocationLibrary
		mLocationLibrary = new LocationLibrary(this, this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.mLocationLibrary.onDestroy();
	}

	public void startService(View v) {
		this.mLocationLibrary.startSpeedo();
	}

	public void stopService(View v) {
		this.mLocationLibrary.stopSpeedo();
	}

	@Override
	public void OnLocationChanged(Location location) {
		// Don't care

	}

	@Override
	public void OnDataChanged(LocationModel model) {
		mTotalDistance.setText(SpeedoMeterUtils.getDistanceText(model
				.getmDistanceTraveled()));

		Log.d(TAG, "Total Distance: " + model.getmDistanceTraveled());
	}

}

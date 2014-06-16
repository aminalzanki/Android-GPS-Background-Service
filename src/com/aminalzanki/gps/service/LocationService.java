package com.aminalzanki.gps.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.aminalzanki.gps.R;
import com.aminalzanki.gps.activity.SpeedoMeterLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationService extends Service implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener {

	private static final String TAG = LocationService.class.getSimpleName();

	public static final String BROADCAST_INTERVAL = "broadcast_interval";
	public static final Integer BROADCAST_INTERVAL_DEFAULT = 1;

	protected static final int SECONDS = 1000;
	protected static final int MINUTES = 5 * SECONDS;
	protected static final int FASTEST_INTERVAL = 5 * SECONDS;

	protected LocationRequest locationRequest = null;
	protected LocationClient locationClient = null;
	
	protected SpeedoMeterLocation mSpeedoMeterLocation;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");

		this.locationRequest = new LocationRequest();
		if (hasFineLocationPermission())
			this.locationRequest
					.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		else if (hasCoarseLocationPermission())
			this.locationRequest
					.setPriority(LocationRequest.PRIORITY_LOW_POWER);
		else
			this.locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);

		this.locationRequest.setFastestInterval(FASTEST_INTERVAL);
		this.locationRequest.setInterval(FASTEST_INTERVAL);
		this.locationClient = new LocationClient(this, this, this);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");

		if (!hasFineLocationPermission()
				&& !hasCoarseLocationPermission()
				|| GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
			Log.e(TAG, getString(R.string.gpsServiceStartError));
			this.stopSelf();
			return START_STICKY;
		}

		if (intent != null) {
			if (this.locationClient.isConnected()
					|| this.locationClient.isConnecting())
				this.locationClient.disconnect();

			Integer broadcast_interval = intent.getIntExtra(BROADCAST_INTERVAL,
					BROADCAST_INTERVAL_DEFAULT);
			this.locationRequest.setInterval(broadcast_interval * MINUTES);

			this.locationClient.connect();
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");

		if (this.locationClient.isConnected()) {
			this.locationClient.removeLocationUpdates(this);
		}
		this.locationClient.disconnect();
		super.onDestroy();
	}

	// Google Play Location Services

	@Override
	public void onConnected(Bundle dataBundle) {
		Log.d(TAG, "Play: onConnected");
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

		locationRequest = LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		this.locationClient.requestLocationUpdates(this.locationRequest, this);
	}

	@Override
	public void onDisconnected() {
		Log.d(TAG, "Play: onDisconnected");
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.d(TAG, "Play: onConnectionFailed");
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "Play: onLocationChanged");

		// Track location changes
		Log.d(TAG, "Latitude: " + location.getLatitude());
		Log.d(TAG, "Longitude: " + location.getLongitude());
		
		if (this.mSpeedoMeterLocation != null) {
			this.mSpeedoMeterLocation.OnLocationChanged(location);
		}
	}

	private boolean hasFineLocationPermission() {
		return this
				.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED;
	}

	private boolean hasCoarseLocationPermission() {
		return this
				.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED;
	}
	
}

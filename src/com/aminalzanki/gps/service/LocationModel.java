package com.aminalzanki.gps.service;

import java.io.Serializable;

public class LocationModel implements Serializable {

	private static final long serialVersionUID = 1L;

	protected float mDistanceTraveled;

	protected long mTotalTime;

	public LocationModel(float distanceTraveled, long totalTime) {
		this.mDistanceTraveled = distanceTraveled;
		this.mTotalTime = totalTime;
	}

	public void updateModel(float distanceTraveled, long totalTime) {
		this.mDistanceTraveled = distanceTraveled;
		this.mTotalTime = totalTime;
	}

	public float getmDistanceTraveled() {
		return mDistanceTraveled;
	}

	public void setmDistanceTraveled(float mDistanceTraveled) {
		this.mDistanceTraveled = mDistanceTraveled;
	}

	public long getmTotalTime() {
		return mTotalTime;
	}

	public void setmTotalTime(long mTotalTime) {
		this.mTotalTime = mTotalTime;
	}

}

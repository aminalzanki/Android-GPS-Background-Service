package com.aminalzanki.gps.service;

import java.io.Serializable;

public class LocationModel implements Serializable {

	private static final long serialVersionUID = 1L;

	protected float mDistanceTraveled;

	public LocationModel(float distanceTraveled) {
		this.mDistanceTraveled = distanceTraveled;
	}

	public void updateModel(float distanceTraveled) {
		this.mDistanceTraveled = distanceTraveled;
	}

	public float getmDistanceTraveled() {
		return mDistanceTraveled;
	}

	public void setmDistanceTraveled(float mDistanceTraveled) {
		this.mDistanceTraveled = mDistanceTraveled;
	}

}

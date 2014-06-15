package com.aminalzanki.gps.service;

import android.location.Location;

public interface ILocationCallback {
	
	/**
	 * Call back on location changed
	 * 
	 * @param location
	 */
	public void OnLocationChanged(Location location);
	
	/**
	 * Call back on data changed
	 * 
	 * @param model
	 */
	public void OnDataChanged(LocationModel model);

}

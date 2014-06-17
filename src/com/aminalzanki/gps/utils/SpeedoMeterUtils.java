package com.aminalzanki.gps.utils;

import java.text.DecimalFormat;

public final class SpeedoMeterUtils {

	public static String getDistanceText(float distance) {
		String distanceText;

		DecimalFormat df = new DecimalFormat("0.00000000");
		distanceText = df.format(distance);

		return distanceText;
	}

}

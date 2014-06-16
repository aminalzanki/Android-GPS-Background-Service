package com.aminalzanki.gps.utils;

import android.annotation.SuppressLint;
import java.text.DecimalFormat;

public final class SpeedoMeterUtils {
	
	public static String getDistanceText(float distance) {
		String distanceText;

		DecimalFormat df = new DecimalFormat("0.00");
		distanceText = df.format(distance);

		return distanceText;
	}
	
	@SuppressLint("DefaultLocale")
	public static String getTimeText(long time) {
		String timeText;

		long seconds = getSeconds(time);
		long minutes = getMinutes(time);
		long hours = getHours(time);

		timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);

		return timeText;
	}
	
	public static long getSeconds(long time) {
		return (time / 1000) % 60;
	}

	public static long getMinutes(long time) {
		return (((time / 1000) / 60) % 60);
	}

	public static long getHours(long time) {
		return (((time / 1000) / (60 * 60)) % 24);
	}

}

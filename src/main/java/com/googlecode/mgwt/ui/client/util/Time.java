package com.googlecode.mgwt.ui.client.util;

/**
 * Simple data structure to store time (only hours and minutes)
 * <p/>
 * Created by Guillaume on 2014-11-16.
 */
public class Time {
	private int hours;
	private int minutes;

	public Time() {
		this(0, 0);
	}

	public Time(int hours, int minutes) {
		this.setHours(hours);
		this.setMinutes(minutes);
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		if (hours < 0 || hours > 23) {
			throw new IllegalArgumentException("hours should be between 0 and 23");
		}
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		if (minutes < 0 || minutes > 59) {
			throw new IllegalArgumentException("minutes should be between 0 and 59");
		}
		this.minutes = minutes;
	}
}

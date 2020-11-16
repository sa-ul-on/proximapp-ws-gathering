package com.proximapp.gathering.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Gathering {

	private long id;
	private Place place;
	private final Map<Long, String> trackings = new HashMap<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Place getPlace() {
		return place;
	}

	public boolean register(Tracking tracking, String datetime) {
		if (tracking == null)
			return false;
		if (datetime == null)
			return false;
		if (trackings.containsKey(tracking.getId())) {
			if (trackings.get(tracking.getId()) == null) {
				trackings.put(tracking.getId(), datetime);
				return true;
			} else {
				// log: B <-> A ripetuto
				return false;
			}
		} else {
			trackings.put(tracking.getId(), datetime);
			return true;
		}
	}

	public boolean register(Tracking tracking) {
		if (tracking == null)
			return false;
		if (!trackings.containsKey(tracking.getId())) {
			trackings.put(tracking.getId(), null);
		}
		return true;
	}

	public String getStartDatetime() {
		if (trackings.isEmpty())
			return null;
		List<String> vals = new ArrayList<>(trackings.values());
		int i = 0;
		String min = null;
		while (min == null && i < vals.size())
			min = vals.get(i++);
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (; i < vals.size(); i++) {
			if (vals.get(i) == null)
				continue;
			try {
				Date a = sdformat.parse(min);
				Date b = sdformat.parse(vals.get(i));
				if (a.compareTo(b) > 0) {
					min = vals.get(i);
				}
			} catch (ParseException e) {
				// warning log
				throw new IllegalStateException();
			}
		}
		return min;
	}

	public String getEndDatetime() {
		if (trackings.isEmpty())
			return null;
		List<String> vals = new ArrayList<>(trackings.values());
		int i = 0;
		String max = null;
		while (max == null && i < vals.size())
			max = vals.get(i++);
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (; i < vals.size(); i++) {
			if (vals.get(i) == null)
				continue;
			try {
				Date a = sdformat.parse(max);
				Date b = sdformat.parse(vals.get(i));
				if (a.compareTo(b) < 0) {
					max = vals.get(i);
				}
			} catch (ParseException e) {
				// warning log
				throw new IllegalStateException();
			}
		}
		return max;
	}

	public String toString() {
		return "Id: " + id
				+ "\nPlace: " + place.getId()
				+ "\nFrom: " + getStartDatetime()
				+ "\nTo: " + getEndDatetime()
				+ "\nWith: " + trackings.keySet().toString();
	}

	public Set<Long> getTrackings() {
		return trackings.keySet();
	}

}

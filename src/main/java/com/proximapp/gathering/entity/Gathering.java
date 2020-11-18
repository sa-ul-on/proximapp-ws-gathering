package com.proximapp.gathering.entity;

import java.util.*;

public class Gathering {

	private long id;
	private long placeId;
	private final Map<Long, Date> trackings = new HashMap<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	public boolean register(Tracking tracking, Date date) {
		if (tracking == null)
			return false;
		if (date == null)
			return false;
		if (trackings.containsKey(tracking.getId())) {
			if (trackings.get(tracking.getId()) == null) {
				trackings.put(tracking.getId(), date);
				return true;
			} else {
				// log: B <-> A ripetuto
				return false;
			}
		} else {
			trackings.put(tracking.getId(), date);
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

	public Date getStartDate() {
		return trackings.values().stream().filter(Objects::nonNull).min(Date::compareTo).orElse(null);
	}

	public Date getEndDate() {
		return trackings.values().stream().filter(Objects::nonNull).max(Date::compareTo).orElse(null);
	}

	public String toString() {
		return "Id: " + id
				+ "\nPlace: " + placeId
				+ "\nFrom: " + getStartDate()
				+ "\nTo: " + getEndDate()
				+ "\nWith: " + trackings.keySet().toString();
	}

	public Set<Long> getTrackings() {
		return trackings.keySet();
	}

}

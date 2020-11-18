package com.proximapp.gathering.repo.impl;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.repo.IGatheringRepo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PlainGatheringRepo implements IGatheringRepo {

	private final List<Gathering> gatherings = new LinkedList<>();
	private final long GATHERING_DURATION_THRESHOLD_MILLIS = 1000 * 60 * 5;

	@Override
	public boolean addGatheringInfo(Tracking t1, Tracking t2, Place place, double distance, Date date) {
		Gathering gathering = null;
		for (Gathering curGath : gatherings) {
			if (curGath.getPlaceId() == place.getId()) {
				long diffInMillies = date.getTime() - curGath.getEndDate().getTime();
				if (diffInMillies <= GATHERING_DURATION_THRESHOLD_MILLIS) {
					gathering = curGath;
					break;
				}
			}
		}

		if (gathering == null) {
			long maxUsedId = 0;
			for (Gathering curGathering : gatherings)
				if (curGathering.getId() > maxUsedId)
					maxUsedId = curGathering.getId();
			gathering = new Gathering();
			gathering.setId(maxUsedId + 1);
			gathering.setPlaceId(place.getId());
			gatherings.add(gathering);
		}
		gathering.register(t1, date);
		gathering.register(t2);

		return true;
	}

	public List<Gathering> getGatherings() {
		return gatherings;
	}

}

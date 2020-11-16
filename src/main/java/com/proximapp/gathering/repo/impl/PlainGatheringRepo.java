package com.proximapp.gathering.repo.impl;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.util.StdDateTimeManager;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class PlainGatheringRepo implements IGatheringRepo {

	private final StdDateTimeManager dateTimeManager = new StdDateTimeManager();
	private final List<Gathering> gatherings = new LinkedList<>();
	private final long gatheringDurationThresholdMillis = 1000 * 60 * 5;

	@Override
	public boolean addGatheringInfo(Tracking t1, Tracking t2, Place place, double distance, String datetime) {
		Gathering gathering = null;
		for (Gathering curGath : gatherings) {
			if (curGath.getPlace().getId() == place.getId()) {
				try {
					long diffInMillies = dateTimeManager.getMillisFromDateTimes(curGath.getEndDatetime(), datetime);
					if (diffInMillies <= gatheringDurationThresholdMillis) {
						gathering = curGath;
						break;
					}
				} catch (ParseException e) {
					e.printStackTrace();
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
			gathering.setPlace(place);
			gatherings.add(gathering);
		}
		if (datetime.isEmpty())
			datetime = dateTimeManager.getCurrentDateTime();
		gathering.register(t1, datetime);
		gathering.register(t2);

		return true;
	}

	public List<Gathering> getGatherings() {
		return gatherings;
	}

}

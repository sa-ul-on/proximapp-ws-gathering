package com.proximapp.gathering.repo.impl.local;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.repo.IGatheringRepo;

import java.util.*;

public class LocalGatheringRepo implements IGatheringRepo {

	private static final Map<Long, Gathering> gatherings = new HashMap<>();

	@Override
	public Gathering createGathering(Gathering gathering) {
		long maxId = gatherings.keySet().stream().max(Long::compare).orElse(0L);
		maxId++;
		gathering.setId(maxId);
		gatherings.put(gathering.getId(), gathering);
		return gathering;
	}

	@Override
	public Gathering findGatheringById(long gatheringId) {
		return gatherings.get(gatheringId);
	}

	@Override
	public Gathering findGatheringInPlaceBeforeThreshold(long placeId, Date date, long threshold) {
		for (Gathering gathering : gatherings.values()) {
			if (gathering.getPlaceId() == placeId) {
				long diffInMillies = date.getTime() - gathering.getEndDate().getTime();
				if (diffInMillies <= threshold) {
					return gathering;
				}
			}
		}
		return null;
	}

	@Override
	public List<Gathering> findAll() {
		return new LinkedList<>(gatherings.values());
	}

	@Override
	public Gathering updateGathering(Gathering gathering) {
		Gathering original = gatherings.get(gathering.getId());
		if (original != null) {
			original.setPlaceId(gathering.getPlaceId());
			return original;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteGathering(long gatheringId) {
		gatherings.remove(gatheringId);
		return true;
	}

}

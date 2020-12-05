package com.proximapp.gathering.repo.impl.local;

import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.repo.ITrackingRepo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LocalTrackingRepo implements ITrackingRepo {

	private static final Map<Long, Tracking> trackings = new HashMap<>();

	@Override
	public Tracking createTracking(Tracking tracking) {
		long maxId = trackings.keySet().stream().max(Long::compare).orElse(0L);
		maxId++;
		tracking.setId(maxId);
		trackings.put(tracking.getId(), tracking);
		return tracking;
	}

	@Override
	public Tracking findTrackingById(long trackingId) {
		return trackings.getOrDefault(trackingId, null);
	}

	@Override
	public List<Tracking> findAll() {
		return new LinkedList<>(trackings.values());
	}

	@Override
	public boolean deleteTracking(long trackingId) {
		trackings.remove(trackingId);
		return true;
	}

}

package com.proximapp.gathering.repo.impl.local;

import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.repo.IPlaceRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalPlaceRepo implements IPlaceRepo {

	private static final Map<Long, Place> places = new HashMap<>();

	@Override
	public Place createPlace(Place place) {
		long maxId = places.keySet().stream().max(Long::compare).orElse(0L);
		maxId++;
		place.setId(maxId);
		places.put(place.getId(), place);
		return place;
	}

	@Override
	public Place findPlaceById(long placeId) {
		return places.getOrDefault(placeId, null);
	}

	@Override
	public List<Place> findAll() {
		return new ArrayList<>(places.values());
	}

	@Override
	public boolean deletePlace(long placeId) {
		places.remove(placeId);
		return true;
	}

}

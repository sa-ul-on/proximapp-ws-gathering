package com.proximapp.gathering.repo.impl;

import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.repo.IPlaceRepo;

import java.util.HashMap;
import java.util.Map;

public class PlainPlaceRepo implements IPlaceRepo {

	private final Map<Long, Place> places = new HashMap<>();

	public PlainPlaceRepo() {
		Place stanza1 = new Place();
		stanza1.setId(1);
		stanza1.setName("Stanza 1");
		places.put(stanza1.getId(), stanza1);

		Place stanza2 = new Place();
		stanza2.setId(2);
		stanza2.setName("Stanza 2");
		places.put(stanza2.getId(), stanza2);

		Place stanza3 = new Place();
		stanza3.setId(3);
		stanza3.setName("Stanza 3");
		places.put(stanza3.getId(), stanza3);
	}

	@Override
	public Place findPlaceById(long placeId) {
		return places.getOrDefault(placeId, null);
	}

}

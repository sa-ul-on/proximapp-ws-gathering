package com.proximapp.gathering.external.repo.mongodb;

import com.mongodb.*;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.repo.IPlaceRepo;

import java.util.LinkedList;
import java.util.List;

public class MongoDbPlaceRepo implements IPlaceRepo {

	private final DBCollection placesCollection;

	public MongoDbPlaceRepo(DB database) {
		placesCollection = database.getCollection("places");
	}

	@Override
	public Place createPlace(Place place) {
		long maxId = findAll().stream().map(Place::getId).max(Long::compareTo).orElse(0L);
		maxId++;
		place.setId(maxId);
		placesCollection.insert(new BasicDBObject()
				.append("_id", place.getId())
				.append("name", place.getName()));
		return place;
	}

	@Override
	public Place findPlaceById(long placeId) {
		Place place = null;
		DBObject query = new BasicDBObject("_id", placeId);
		DBCursor cursor = placesCollection.find(query);
		if (cursor.hasNext()) {
			DBObject placeObj = cursor.next();
			place = new Place();
			place.setId((long) placeObj.get("_id"));
			place.setName((String) placeObj.get("name"));
		}
		return place;
	}

	@Override
	public List<Place> findAll() {
		List<Place> places = new LinkedList<>();
		for (DBObject dbObject : placesCollection.find()) {
			Place place = new Place();
			place.setId((long) dbObject.get("_id"));
			place.setName((String) dbObject.get("name"));
			places.add(place);
		}
		return places;
	}

	@Override
	public boolean deletePlace(long placeId) {
		return placesCollection.remove(new BasicDBObject("_id", placeId)).getN() == 1;
	}

}

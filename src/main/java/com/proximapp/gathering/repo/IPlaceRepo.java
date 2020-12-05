package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Place;

import java.util.List;

public interface IPlaceRepo {

	Place createPlace(Place place);

	Place findPlaceById(long placeId);

	List<Place> findAll();

	boolean deletePlace(long placeId);

}

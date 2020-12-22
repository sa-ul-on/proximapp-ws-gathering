package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Place;

import java.util.List;

public interface IPlaceRepo {

	Place createPlace(Place place);

	Place findPlaceById(long placeId);

	List<Place> findPlacesByCompany(long companyId);

	boolean deletePlace(long placeId);

}

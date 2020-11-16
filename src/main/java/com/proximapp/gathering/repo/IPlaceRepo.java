package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Place;

public interface IPlaceRepo {

	Place findPlaceById(long placeId);

}

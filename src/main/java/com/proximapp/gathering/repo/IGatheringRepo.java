package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Gathering;

import java.util.Date;
import java.util.List;

public interface IGatheringRepo {

	Gathering createGathering(Gathering gathering);

	Gathering findGatheringById(long gatheringId);

	Gathering findGatheringInPlaceBeforeThreshold(long placeId, Date date, long threshold);

	List<Gathering> findAll();

	Gathering updateGathering(Gathering gathering);

	boolean deleteGathering(long gatheringId);

}

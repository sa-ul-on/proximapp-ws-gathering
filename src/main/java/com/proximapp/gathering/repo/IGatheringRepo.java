package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Gathering;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IGatheringRepo {

	Gathering createGathering(Gathering gathering);

	Gathering findGatheringById(long gatheringId);

	Gathering findGatheringInPlaceBeforeThreshold(long placeId, Date date, long threshold);

	List<Gathering> findGatheringsByQuery(long companyId, Date dateFrom, Date dateTo, Set<Long> trackingIds,
	                                      Set<Long> placeIds);

	Gathering updateGathering(Gathering gathering);

	boolean deleteGathering(long gatheringId);

}

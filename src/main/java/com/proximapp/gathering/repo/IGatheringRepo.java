package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;

import java.util.Date;
import java.util.List;

public interface IGatheringRepo {

	boolean addGatheringInfo(Tracking t1, Tracking t2, Place place, double distance, Date date);

	List<Gathering> getGatherings();

}

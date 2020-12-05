package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Tracking;

import java.util.List;

public interface ITrackingRepo {

	Tracking createTracking(Tracking tracking);

	Tracking findTrackingById(long trackingId);

	List<Tracking> findAll();

	boolean deleteTracking(long trackingId);

}

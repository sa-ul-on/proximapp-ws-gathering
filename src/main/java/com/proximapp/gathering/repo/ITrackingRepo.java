package com.proximapp.gathering.repo;

import com.proximapp.gathering.entity.Tracking;

public interface ITrackingRepo {

	Tracking findTrackingById(long trackingId);

}

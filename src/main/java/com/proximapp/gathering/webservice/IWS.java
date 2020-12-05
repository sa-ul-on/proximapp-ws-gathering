package com.proximapp.gathering.webservice;

import com.proximapp.gathering.external.repo.DriverSetter;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.ITrackingRepo;

public abstract class IWS {

	protected ITrackingRepo trackingRepo;
	protected IPlaceRepo placeRepo;
	protected IGatheringRepo gatheringRepo;

	protected void initRepos() {
		if (trackingRepo == null) {
			DriverSetter driverSetter = new DriverSetter();
			driverSetter.init();
			trackingRepo = driverSetter.getTrackingRepo();
			placeRepo = driverSetter.getPlaceRepo();
			gatheringRepo = driverSetter.getGatheringRepo();
		}
	}

}

package com.proximapp.gathering.repo.impl.local;

import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.IRepoSystem;
import com.proximapp.gathering.repo.ITrackingRepo;

public class LocalRepoSystem implements IRepoSystem {

	private final ITrackingRepo trackingRepo;
	private final IPlaceRepo placeRepo;
	private final IGatheringRepo gatheringRepo;

	public LocalRepoSystem() {
		this.trackingRepo = new LocalTrackingRepo();
		this.placeRepo = new LocalPlaceRepo();
		this.gatheringRepo = new LocalGatheringRepo();
	}

	@Override
	public ITrackingRepo getTrackingRepo() {
		return trackingRepo;
	}

	@Override
	public IPlaceRepo getPlaceRepo() {
		return placeRepo;
	}

	@Override
	public IGatheringRepo getGatheringRepo() {
		return gatheringRepo;
	}

}

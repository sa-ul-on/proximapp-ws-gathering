package com.proximapp.gathering.external.repo;

import com.proximapp.gathering.external.repo.mongodb.MongoDbRepoSystem;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.IRepoSystem;
import com.proximapp.gathering.repo.ITrackingRepo;

public class DriverSetter {

	private IRepoSystem repoSystem;

	public void init() {
		repoSystem = new MongoDbRepoSystem();
		((MongoDbRepoSystem) repoSystem).connect();
	}

	public ITrackingRepo getTrackingRepo() {
		return repoSystem.getTrackingRepo();
	}

	public IPlaceRepo getPlaceRepo() {
		return repoSystem.getPlaceRepo();
	}

	public IGatheringRepo getGatheringRepo() {
		return repoSystem.getGatheringRepo();
	}

}

package com.proximapp.gathering.external.repo.mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.IRepoSystem;
import com.proximapp.gathering.repo.ITrackingRepo;

public class MongoDbRepoSystem implements IRepoSystem {

	private ITrackingRepo trackingRepo;
	private IPlaceRepo placeRepo;
	private IGatheringRepo gatheringRepo;

	public void connect() {
		String connString = "mongodb+srv://umbertolo:ayvrew37AJQ3YGkb@proximapp-cluster-db.aynr4.mongodb.net" +
				"/proximapp?retryWrites=true&w=majority";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(connString));
		DB database = mongoClient.getDB("proximapp");
		trackingRepo = new MongoDbTrackingRepo(database);
		placeRepo = new MongoDbPlaceRepo(database);
		gatheringRepo = new MongoDbGatheringRepo(database);
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

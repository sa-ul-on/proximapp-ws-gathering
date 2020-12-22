package com.proximapp.gathering.external.repo.mongodb;

import com.mongodb.*;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.repo.ITrackingRepo;

import java.util.LinkedList;
import java.util.List;

public class MongoDbTrackingRepo implements ITrackingRepo {

	private final DBCollection trackingCollection;

	public MongoDbTrackingRepo(DB database) {
		trackingCollection = database.getCollection("tracking");
	}

	@Override
	public Tracking createTracking(Tracking tracking) {
		long maxId = 0;
		for (DBObject dbObject : trackingCollection.find()) {
			long curId = (long) dbObject.get("_id");
			if (curId > maxId)
				maxId = curId;
		}
		maxId++;
		tracking.setId(maxId);
		BasicDBObject obj = new BasicDBObject()
				.append("_id", tracking.getId())
				.append("firstname", tracking.getNome())
				.append("lastname", tracking.getCognome())
				.append("address", tracking.getIndirizzo())
				.append("health_insurance_card", tracking.getTesseraSanitaria())
				.append("phone", tracking.getTelefono())
				.append("user_id", tracking.getUserId())
				.append("company_id", tracking.getCompanyId());
		trackingCollection.insert(obj);
		return tracking;
	}

	@Override
	public Tracking findTrackingById(long trackingId) {
		Tracking tracking = null;
		DBObject query = new BasicDBObject("_id", trackingId);
		DBCursor cursor = trackingCollection.find(query);
		if (cursor.hasNext()) {
			DBObject placeObj = cursor.next();
			tracking = new Tracking();
			tracking.setId((long) placeObj.get("_id"));
			tracking.setNome((String) placeObj.get("firstname"));
			tracking.setCognome((String) placeObj.get("lastname"));
			tracking.setIndirizzo((String) placeObj.get("address"));
			tracking.setTesseraSanitaria((String) placeObj.get("health_insurance_card"));
			tracking.setTelefono((String) placeObj.get("phone"));
			tracking.setUserId((long) placeObj.get("user_id"));
			tracking.setCompanyId((long) placeObj.get("company_id"));
		}
		return tracking;
	}

	@Override
	public List<Tracking> findTrackingsByCompany(long companyId) {
		List<Tracking> trackings = new LinkedList<>();
		DBObject query = new BasicDBObject("company_id", companyId);
		DBCursor cursor = trackingCollection.find(query);
		for (DBObject dbObject : cursor) {
			Tracking tracking = new Tracking();
			tracking.setId((long) dbObject.get("_id"));
			tracking.setNome((String) dbObject.get("firstname"));
			tracking.setCognome((String) dbObject.get("lastname"));
			tracking.setIndirizzo((String) dbObject.get("address"));
			tracking.setTesseraSanitaria((String) dbObject.get("health_insurance_card"));
			tracking.setTelefono((String) dbObject.get("phone"));
			tracking.setUserId((long) dbObject.get("user_id"));
			tracking.setCompanyId((long) dbObject.get("company_id"));
			trackings.add(tracking);
		}
		return trackings;
	}

	@Override
	public boolean deleteTracking(long trackingId) {
		return trackingCollection.remove(new BasicDBObject("_id", trackingId)).getN() == 1;
	}

}

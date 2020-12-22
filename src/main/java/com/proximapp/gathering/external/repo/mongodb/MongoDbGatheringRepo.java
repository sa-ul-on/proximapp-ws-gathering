package com.proximapp.gathering.external.repo.mongodb;

import com.mongodb.*;
import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.util.DatetimeManager;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MongoDbGatheringRepo implements IGatheringRepo {

	private final DBCollection gatheringCollection, placeCollection;

	public MongoDbGatheringRepo(DB database) {
		gatheringCollection = database.getCollection("gatherings");
		placeCollection = database.getCollection("places");
	}

	@Override
	public Gathering createGathering(Gathering gathering) {
		long maxId = 0;
		for (DBObject dbObject : gatheringCollection.find()) {
			long curId = (long) dbObject.get("_id");
			if (curId > maxId)
				maxId = curId;
		}
		maxId++;
		gathering.setId(maxId);
		BasicDBList trackingDbList = new BasicDBList();
		for (long trackingId : gathering.getTrackings()) {
			BasicDBObject trackingObj = new BasicDBObject();
			trackingObj.put("tracking_id", trackingId);
			Date date = gathering.getDateFromTrackingId(trackingId);
			if (date != null)
				trackingObj.put("datetime", DatetimeManager.format(date));
			trackingDbList.add(trackingObj);
		}
		BasicDBObject obj = new BasicDBObject()
				.append("_id", gathering.getId())
				.append("place_id", gathering.getPlaceId())
				.append("trackings", trackingDbList);
		gatheringCollection.insert(obj);
		return gathering;
	}

	@Override
	public Gathering findGatheringById(long gatheringId) {
		DBCursor cursor = gatheringCollection.find(new BasicDBObject("_id", gatheringId));
		if (cursor.hasNext()) {
			DBObject gatheringObj = cursor.next();
			Gathering gathering = new Gathering();
			gathering.setId((long) gatheringObj.get("_id"));
			gathering.setPlaceId((long) gatheringObj.get("place_id"));
			if (gatheringObj.get("trackings") != null) {
				BasicDBList trackingsList = (BasicDBList) gatheringObj.get("trackings");
				for (Object o : trackingsList) {
					BasicDBObject obj = (BasicDBObject) o;
					long trackingId = (long) obj.get("tracking_id");
					Date date = null;
					try {
						String datetime = (String) obj.get("datetime");
						if (datetime != null) {
							date = DatetimeManager.parse(datetime);
						}
					} catch (ParseException e) {
						e.printStackTrace();
						return null;
					}
					gathering.register(trackingId, date);
				}
			}
			return gathering;
		} else {
			return null;
		}
	}

	@Override
	public Gathering findGatheringInPlaceBeforeThreshold(long placeId, Date date, long threshold) {
		DBCursor cursor = gatheringCollection.find(new BasicDBObject("place_id", placeId));
		for (DBObject dbObject : cursor) {
			long gatheringId = (long) dbObject.get("_id");
			Gathering gathering = findGatheringById(gatheringId);
			if (gathering.getPlaceId() == placeId) {
				long diffInMillies = date.getTime() - gathering.getEndDate().getTime();
				if (diffInMillies <= threshold) {
					return gathering;
				}
			}
		}
		return null;
	}

	@Override
	public List<Gathering> findGatheringsByCompany(long companyId) {
		List<Gathering> gatherings = new LinkedList<>();
		List<Long> placeIds = new LinkedList<>();
		for (DBObject dbObject : placeCollection.find(new BasicDBObject("company_id", companyId))) {
			long placeId = (long) dbObject.get("_id");
			placeIds.add(placeId);
		}
		/* TODO: Use aggregate
		DBObject lookupFields = new BasicDBObject("from", "places");
		lookupFields.put("localField", "place_id");
		lookupFields.put("foreignField", "_id");
		lookupFields.put("as", "place");
		DBObject lookup = new BasicDBObject("$lookup", lookupFields);
		Cursor cursor = gatheringCollection.aggregate(Collections.singletonList(lookup), null);
		*/
		for (DBObject dbObject : gatheringCollection.find()) {
			long placeId = (long) dbObject.get("place_id");
			if (placeIds.contains(placeId)) {
				Gathering gathering = findGatheringById((long) dbObject.get("_id"));
				gatherings.add(gathering);
			}
		}
		return gatherings;
	}

	@Override
	public Gathering updateGathering(Gathering gathering) {
		BasicDBList trackingDbList = new BasicDBList();
		for (long trackingId : gathering.getTrackings()) {
			BasicDBObject trackingObj = new BasicDBObject();
			trackingObj.put("tracking_id", trackingId);
			Date date = gathering.getDateFromTrackingId(trackingId);
			if (date != null)
				trackingObj.put("datetime", DatetimeManager.format(date));
			trackingDbList.add(trackingObj);
		}
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("place_id", gathering.getPlaceId());
		updateObj.put("trackings", trackingDbList);
		BasicDBObject searchQuery = new BasicDBObject("_id", gathering.getId());
		BasicDBObject updateQuery = new BasicDBObject("$set", updateObj);
		WriteResult wr = gatheringCollection.update(searchQuery, updateQuery);
		return wr.getN() == 1 ? gathering : null;
	}

	@Override
	public boolean deleteGathering(long gatheringId) {
		return gatheringCollection.remove(new BasicDBObject("_id", gatheringId)).getN() == 1;
	}

}

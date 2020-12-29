package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.util.DatetimeManager;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class GatheringController extends IWS {

	private final long GATHERING_DURATION_THRESHOLD_MILLIS = 1000 * 60 * 5;

	/** notifyGathering(companyId, t1id, t2id, pid, dist, datetime) */
	@PostMapping("/gatherings/{companyId}")
	public Gathering create(@PathVariable("companyId") long companyId, @RequestParam(value = "t1id") long t1id,
	                        @RequestParam(value = "t2id") long t2id, @RequestParam(value = "pid") long pid,
	                        @RequestParam(value = "dist") double dist,
	                        @RequestParam(value = "datetime", defaultValue = "") String datetime) {

		// Distance validation
		if (dist < 0)
			throw new IllegalStateException("Invalid distance");
		// Tracking IDs validation
		if (t1id == t2id)
			throw new IllegalStateException("Tracking IDs must be different");

		// Date validation/retrieval
		Date date;
		if (datetime.isEmpty()) {
			date = new Date(System.currentTimeMillis());
		} else {
			try {
				date = DatetimeManager.parse(datetime);
			} catch (ParseException e) {
				throw new IllegalStateException("Invalid datetime");
			}
		}

		initRepos();

		Tracking t1 = trackingRepo.findTrackingById(t1id);
		if (t1 == null || t1.getCompanyId() != companyId)
			return null;
		Tracking t2 = trackingRepo.findTrackingById(t2id);
		if (t2 == null || t2.getCompanyId() != companyId)
			return null;
		Place place = placeRepo.findPlaceById(pid);
		if (place == null || place.getCompanyId() != companyId)
			return null;

		Gathering gathering = gatheringRepo.findGatheringInPlaceBeforeThreshold(place.getId(), date,
				GATHERING_DURATION_THRESHOLD_MILLIS);
		if (gathering == null) {
			gathering = new Gathering();
			gathering.setPlaceId(place.getId());
			gathering.register(t1.getId(), date);
			gathering.register(t2.getId(), null);
			gathering = gatheringRepo.createGathering(gathering);
		} else {
			gathering.register(t1.getId(), date);
			gathering.register(t2.getId(), null);
			gathering = gatheringRepo.updateGathering(gathering);
		}
		return gathering;
	}

	/** findGatheringsByQuery(companyId, dateFrom, dateTo, trackingIds, placeIds) */
	@GetMapping("/gatherings/{companyId}/query")
	public List<Gathering> find(@PathVariable("companyId") long companyId,
	                            @RequestParam(value = "date_from", defaultValue = "") String strDateFrom,
	                            @RequestParam(value = "date_to", defaultValue = "") String strDateTo,
	                            @RequestParam(value = "trackings", defaultValue = "") String strTrackingIds,
	                            @RequestParam(value = "places", defaultValue = "") String strPlaceIds) {
		initRepos();
		final String ID_LIST_REG_EXP = "((\\d+,)*\\d+|)";
		if (!strTrackingIds.matches(ID_LIST_REG_EXP))
			return null;
		if (!strPlaceIds.matches(ID_LIST_REG_EXP))
			return null;
		Set<Long> trackingIds = strTrackingIds.isEmpty() ? new HashSet<>() :
				Arrays.stream(strTrackingIds.split(","))
						.map(Long::parseLong)
						.filter(possibleTrackingId -> trackingRepo.findTrackingById(possibleTrackingId) != null)
						.collect(Collectors.toSet());
		Set<Long> placeIds = strPlaceIds.isEmpty() ? new HashSet<>() :
				Arrays.stream(strPlaceIds.split(","))
						.map(Long::parseLong)
						.filter(possiblePlaceId -> placeRepo.findPlaceById(possiblePlaceId) != null)
						.collect(Collectors.toSet());
		Date dateFrom = null;
		Date dateTo = null;
		try {
			if (!strDateFrom.isEmpty())
				dateFrom = DatetimeManager.parse(strDateFrom + " 00:00:00");
			if (!strDateTo.isEmpty())
				dateTo = DatetimeManager.parse(strDateTo + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		List<Gathering> gatherings = gatheringRepo.findGatheringsByQuery(companyId, dateFrom, dateTo, trackingIds,
				placeIds);
		return gatherings != null ? gatherings : new LinkedList<>();
	}

	/** deleteGathering(gatheringId, companyId) */
	@DeleteMapping("/gatherings/{companyId}/{gatheringId}")
	public boolean destroy(@PathVariable("gatheringId") long gatheringId, @PathVariable("companyId") long companyId) {
		Gathering gathering = gatheringRepo.findGatheringById(gatheringId);
		if (gathering == null)
			return false;
		Place place = placeRepo.findPlaceById(gathering.getPlaceId());
		if (place == null || place.getCompanyId() != companyId)
			return false;
		return gatheringRepo.deleteGathering(gatheringId);
	}

}

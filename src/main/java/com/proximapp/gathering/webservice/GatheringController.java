package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.util.DatetimeManager;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class GatheringController extends IWS {

	private final long GATHERING_DURATION_THRESHOLD_MILLIS = 1000 * 60 * 5;

	@GetMapping("/gatherings")
	public List<Gathering> index() {
		initRepos();
		return gatheringRepo.findAll();
	}

	@PostMapping("/gatherings")
	public boolean create(@RequestParam(value = "t1id") long t1id, @RequestParam(value = "t2id") long t2id,
	                      @RequestParam(value = "pid") long pid, @RequestParam(value = "dist") double dist,
	                      @RequestParam(value = "datetime", defaultValue = "") String datetime) {

		// Distance validation
		if (dist < 0)
			throw new IllegalStateException("Invalid distance");

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
		Tracking t2 = trackingRepo.findTrackingById(t2id);
		Place place = placeRepo.findPlaceById(pid);

		boolean result;
		Gathering gathering = gatheringRepo.findGatheringInPlaceBeforeThreshold(place.getId(), date,
				GATHERING_DURATION_THRESHOLD_MILLIS);
		if (gathering == null) {
			gathering = new Gathering();
			gathering.setPlaceId(place.getId());
			gathering.register(t1.getId(), date);
			gathering.register(t2.getId(), null);
			result = gatheringRepo.createGathering(gathering) != null;
		} else {
			gathering.register(t1.getId(), date);
			gathering.register(t2.getId(), null);
			result = gatheringRepo.updateGathering(gathering) != null;
		}
		return result;
	}

	@DeleteMapping("/gatherings/{id}")
	public boolean destroy(@PathVariable("id") long gatheringId) {
		Gathering gathering = gatheringRepo.findGatheringById(gatheringId);
		if (gathering == null)
			return false;
		return gatheringRepo.deleteGathering(gathering.getId());
	}

}

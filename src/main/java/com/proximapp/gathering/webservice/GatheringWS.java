package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.ITrackingRepo;
import com.proximapp.gathering.repo.impl.PlainGatheringRepo;
import com.proximapp.gathering.repo.impl.PlainPlaceRepo;
import com.proximapp.gathering.repo.impl.PlainTrackingRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GatheringWS {

	private ITrackingRepo trackingRepo;
	private IPlaceRepo placeRepo;
	private IGatheringRepo gatheringRepo;

	@GetMapping("/gatherings/new")
	public boolean newGathering(@RequestParam(value = "t1id") long t1id, @RequestParam(value = "t2id") long t2id,
	                            @RequestParam(value = "pid") long pid, @RequestParam(value = "dist") double dist,
	                            @RequestParam(value = "datetime", defaultValue = "") String datetime) {
		if (dist < 0)
			throw new IllegalStateException("shit happens...");

		initRepos();

		Tracking t1 = trackingRepo.findTrackingById(t1id);
		Tracking t2 = trackingRepo.findTrackingById(t2id);
		Place place = placeRepo.findPlaceById(pid);

		return gatheringRepo.addGatheringInfo(t1, t2, place, dist, datetime);
	}

	@GetMapping("/gatherings")
	public List<Gathering> getGatherings() {
		initRepos();
		return gatheringRepo.getGatherings();
	}

	private void initRepos() {
		if (trackingRepo == null)
			trackingRepo = new PlainTrackingRepo();
		if (placeRepo == null)
			placeRepo = new PlainPlaceRepo();
		if (gatheringRepo == null)
			gatheringRepo = new PlainGatheringRepo();
	}

}

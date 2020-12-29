package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Tracking;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class TrackingController extends IWS {

	/** createTracking(firstname, lastname, address, hicard, phone, userId, companyId) */
	@PostMapping("/trackings/{companyId}")
	public Tracking create(@RequestParam(value = "firstname") String firstname,
	                       @RequestParam(value = "lastname") String lastname,
	                       @RequestParam(value = "address") String address,
	                       @RequestParam(value = "hicard") String hicard,
	                       @RequestParam(value = "phone") String phone,
	                       @RequestParam(value = "user_id") long userId,
	                       @PathVariable("companyId") long companyId) {
		initRepos();
		Tracking tracking = new Tracking();
		tracking.setFirstname(firstname);
		tracking.setLastname(lastname);
		tracking.setAddress(address);
		tracking.setHicard(hicard);
		tracking.setPhone(phone);
		tracking.setUserId(userId);
		tracking.setCompanyId(companyId);
		return trackingRepo.createTracking(tracking);
	}

	/** findTrackingsByCompany(companyId) */
	@GetMapping("/trackings/{companyId}")
	public List<Tracking> index(@PathVariable("companyId") long companyId) {
		initRepos();
		List<Tracking> trackings = trackingRepo.findTrackingsByCompany(companyId);
		return trackings != null ? trackings : new LinkedList<>();
	}

	/** findTrackingById(trackingId, companyId) */
	@GetMapping("/trackings/{companyId}/{trackingId}")
	public Tracking show(@PathVariable("trackingId") long trackingId,
	                     @PathVariable("companyId") long companyId) {
		initRepos();
		Tracking tracking = trackingRepo.findTrackingById(trackingId);
		if (tracking != null && tracking.getCompanyId() == companyId)
			return tracking;
		else
			return null;
	}

	/** deleteTracking(trackingId, companyId) */
	@DeleteMapping("/trackings/{companyId}/{trackingId}")
	public boolean destroy(@PathVariable("trackingId") long trackingId, @PathVariable("companyId") long companyId) {
		Tracking tracking = trackingRepo.findTrackingById(trackingId);
		if (tracking != null && tracking.getCompanyId() == companyId)
			return trackingRepo.deleteTracking(trackingId);
		else
			return false;
	}

}

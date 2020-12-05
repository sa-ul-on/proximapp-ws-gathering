package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Tracking;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrackingController extends IWS {

	@GetMapping("/trackings")
	public List<Tracking> index() {
		initRepos();
		return trackingRepo.findAll();
	}

	@PostMapping("/trackings")
	public Tracking create(@RequestParam(value = "firstname") String firstname,
	                       @RequestParam(value = "lastname") String lastname,
	                       @RequestParam(value = "address") String address,
	                       @RequestParam(value = "health_insurance_card") String healthInsuranceCard,
	                       @RequestParam(value = "phone") String phone) {
		initRepos();
		Tracking tracking = new Tracking();
		tracking.setNome(firstname);
		tracking.setCognome(lastname);
		tracking.setIndirizzo(address);
		tracking.setTesseraSanitaria(healthInsuranceCard);
		tracking.setTelefono(phone);
		return trackingRepo.createTracking(tracking);
	}

	@DeleteMapping("/trackings/{id}")
	public boolean destroy(@PathVariable("id") long trackingId) {
		Tracking tracking = trackingRepo.findTrackingById(trackingId);
		if (tracking == null)
			return false;
		return trackingRepo.deleteTracking(trackingId);
	}

}

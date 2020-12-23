package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Place;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class PlaceController extends IWS {

	/** createPlace(name, companyId) */
	@PostMapping("/places")
	public Place create(@RequestParam(value = "name") String name,
	                    @RequestParam(value = "company_id") long companyId) {
		initRepos();
		Place place = new Place();
		place.setName(name);
		place.setCompanyId(companyId);
		return placeRepo.createPlace(place);
	}

	/** findPlacesByCompany(companyId) */
	@GetMapping("/places")
	public List<Place> index(@RequestParam(value = "company_id") long companyId) {
		initRepos();
		List<Place> places = placeRepo.findPlacesByCompany(companyId);
		return places != null ? places : new LinkedList<>();
	}

	/** findPlaceById(placeId, companyId) */
	@PostMapping("/places/{companyId}/{placeId}")
	public Place show(@PathVariable("placeId") long placeId, @PathVariable("companyId") long companyId) {
		initRepos();
		Place place = placeRepo.findPlaceById(placeId);
		if (place != null && place.getCompanyId() == companyId)
			return place;
		else
			return null;
	}

	/** deletePlace(placeId, companyId) */
	@DeleteMapping("/places/{companyId}/{placeId}")
	public boolean destroy(@PathVariable("placeId") long placeId, @PathVariable("companyId") long companyId) {
		Place place = placeRepo.findPlaceById(placeId);
		if (place != null && place.getCompanyId() == companyId)
			return placeRepo.deletePlace(placeId);
		else
			return false;
	}

}

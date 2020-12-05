package com.proximapp.gathering.webservice;

import com.proximapp.gathering.entity.Place;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController extends IWS {

	@GetMapping("/places")
	public List<Place> index() {
		initRepos();
		return placeRepo.findAll();
	}

	@PostMapping("/places")
	public Place create(@RequestParam(value = "name") String name) {
		initRepos();
		Place place = new Place();
		place.setName(name);
		return placeRepo.createPlace(place);
	}

	@DeleteMapping("/places/{id}")
	public boolean destroy(@PathVariable("id") long placeId) {
		Place place = placeRepo.findPlaceById(placeId);
		if (place == null)
			return false;
		return placeRepo.deletePlace(place.getId());
	}

}

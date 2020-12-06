package com.proximapp.gathering.webservice.apigateway;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.external.repo.DriverSetter;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.ITrackingRepo;
import com.proximapp.gathering.util.DatetimeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ApiGateway {

	private static final String ID_LIST_REG_EXP = "((\\d+,)*\\d+|)";

	private IGatheringRepo gatheringRepo;
	private IPlaceRepo placeRepo;
	private ITrackingRepo trackingRepo;

	@GetMapping("/api/find")
	public List<Object> find(@RequestParam(value = "date_from", defaultValue = "") String strDateFrom,
	                         @RequestParam(value = "date_to", defaultValue = "") String strDateTo,
	                         @RequestParam(value = "trackings", defaultValue = "") String strTrackingIds,
	                         @RequestParam(value = "places", defaultValue = "") String strPlaceIds) {
		initRepos();

		if (!strTrackingIds.matches(ID_LIST_REG_EXP))
			return null;
		if (!strPlaceIds.matches(ID_LIST_REG_EXP))
			return null;

		List<Tracking> trackings = strTrackingIds.isEmpty() ? new LinkedList<>()
				: Arrays.stream(strTrackingIds.split(
				","))
				.map(Long::parseLong)
				.distinct()
				.map(trackingRepo::findTrackingById)
				.collect(Collectors.toList());
		if (trackings.contains(null))
			return null;
		Set<Long> trackingIds = trackings.stream().map(Tracking::getId).collect(Collectors.toSet());

		List<Place> places = strPlaceIds.isEmpty() ? new LinkedList<>()
				: Arrays.stream(strPlaceIds.split(","))
				.map(Long::parseLong)
				.distinct()
				.map(placeRepo::findPlaceById)
				.collect(Collectors.toList());
		if (places.contains(null))
			return null;
		Set<Long> placeIds = places.stream().map(Place::getId).collect(Collectors.toSet());

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

		final long dateFromTime = dateFrom != null ? dateFrom.getTime() : -1;
		final long dateToTime = dateTo != null ? dateTo.getTime() : -1;
		List<Gathering> survived = gatheringRepo.findAll().stream().filter(gg -> {
			if (!places.isEmpty() && !placeIds.contains(gg.getPlaceId()))
				return false;
			if (!trackings.isEmpty()) {
				Set<Long> localTrackingIds = new HashSet<>(gg.getTrackings());
				localTrackingIds.retainAll(trackingIds);
				if (localTrackingIds.isEmpty())
					return false;
			}
			if (dateFromTime > -1 && gg.getStartDate().getTime() < dateFromTime)
				return false;
			if (dateToTime > -1 && gg.getEndDate().getTime() > dateToTime)
				return false;
			return true;
		}).collect(Collectors.toList());

		List<Object> resultMap = new LinkedList<>();
		for (Gathering gg : survived) {
			Map<String, Object> ll = new LinkedHashMap<>();
			ll.put("id", gg.getId());
			ll.put("start", DatetimeManager.format(gg.getStartDate()));
			ll.put("end", DatetimeManager.format(gg.getEndDate()));
			Map<String, Object> pp = new LinkedHashMap<>();
			Place pl = placeRepo.findPlaceById(gg.getPlaceId());
			pp.put("id", pl.getId());
			pp.put("name", pl.getName());
			ll.put("place", pp);
			List<Object> trackingsList = new LinkedList<>();
			for (long trackingId : gg.getTrackings()) {
				Tracking tracking = trackingRepo.findTrackingById(trackingId);
				Map<String, Object> trackingMap = new LinkedHashMap<>();
				trackingMap.put("id", tracking.getId());
				trackingMap.put("nome", tracking.getNome());
				trackingMap.put("cognome", tracking.getCognome());
				trackingMap.put("indirizzo", tracking.getIndirizzo());
				trackingMap.put("tessera_sanitaria", tracking.getTesseraSanitaria());
				trackingMap.put("telefono", tracking.getTelefono());
				trackingsList.add(trackingMap);
			}
			ll.put("trackings", trackingsList);
			resultMap.add(ll);
		}
		return resultMap;
	}

	private void initRepos() {
		if (gatheringRepo == null) {
			DriverSetter driverSetter = new DriverSetter();
			driverSetter.init();
			gatheringRepo = driverSetter.getGatheringRepo();
			placeRepo = driverSetter.getPlaceRepo();
			trackingRepo = driverSetter.getTrackingRepo();
			if (gatheringRepo.findAll().isEmpty()) {
				Tracking t1 = new Tracking();
				t1.setNome("Persona 1");
				trackingRepo.createTracking(t1);
				Tracking t2 = new Tracking();
				t2.setNome("Persona 2");
				trackingRepo.createTracking(t2);
				Tracking t3 = new Tracking();
				t3.setNome("Persona 3");
				trackingRepo.createTracking(t3);
				Tracking t4 = new Tracking();
				t4.setNome("Persona 4");
				trackingRepo.createTracking(t4);

				Place p1 = new Place();
				p1.setName("Stanza 1");
				placeRepo.createPlace(p1);
				Place p2 = new Place();
				p2.setName("Stanza 2");
				placeRepo.createPlace(p2);
				Place p3 = new Place();
				p3.setName("Stanza 3");
				placeRepo.createPlace(p3);

				Gathering g1 = new Gathering();
				g1.setPlaceId(1);
				g1.register(1, DatetimeManager.parseSafe("2020-12-10 00:00:00"));
				g1.register(2, DatetimeManager.parseSafe("2020-12-10 00:00:03"));
				g1.register(3, DatetimeManager.parseSafe("2020-12-10 00:00:08"));
				gatheringRepo.createGathering(g1);

				Gathering g2 = new Gathering();
				g2.setPlaceId(2);
				g2.register(1, DatetimeManager.parseSafe("2020-12-11 00:00:00"));
				g2.register(2, DatetimeManager.parseSafe("2020-12-11 00:00:03"));
				g2.register(4, DatetimeManager.parseSafe("2020-12-11 00:00:08"));
				gatheringRepo.createGathering(g2);

				Gathering g3 = new Gathering();
				g3.setPlaceId(2);
				g3.register(2, DatetimeManager.parseSafe("2020-12-11 10:00:03"));
				g3.register(3, DatetimeManager.parseSafe("2020-12-11 10:00:08"));
				gatheringRepo.createGathering(g3);
			}
		}
	}

}

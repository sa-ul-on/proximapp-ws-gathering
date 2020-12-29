package com.proximapp.gathering.webservice.apigateway;

import com.proximapp.gathering.entity.Gathering;
import com.proximapp.gathering.entity.Place;
import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.external.repo.DriverSetter;
import com.proximapp.gathering.repo.IGatheringRepo;
import com.proximapp.gathering.repo.IPlaceRepo;
import com.proximapp.gathering.repo.ITrackingRepo;
import com.proximapp.gathering.util.DatetimeManager;
import com.proximapp.gathering.webservice.GatheringController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class ApiGateway {

	private static final String ID_LIST_REG_EXP = "((\\d+,)*\\d+|)";

	private IGatheringRepo gatheringRepo;
	private IPlaceRepo placeRepo;
	private ITrackingRepo trackingRepo;

	@GetMapping("/api/find")
	public List<Object> find(@RequestParam(value = "company_id", defaultValue = "") long companyId,
	                         @RequestParam(value = "date_from", defaultValue = "") String strDateFrom,
	                         @RequestParam(value = "date_to", defaultValue = "") String strDateTo,
	                         @RequestParam(value = "trackings", defaultValue = "") String strTrackingIds,
	                         @RequestParam(value = "places", defaultValue = "") String strPlaceIds) {
		initRepos();
		List<Object> resultMap = new LinkedList<>();
		List<Gathering> resultedGatherings = new GatheringController().find(companyId, strDateFrom, strDateTo,
				strTrackingIds, strPlaceIds);
		for (Gathering gg : resultedGatherings) {
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
				trackingMap.put("nome", tracking.getFirstname());
				trackingMap.put("cognome", tracking.getLastname());
				trackingMap.put("indirizzo", tracking.getAddress());
				trackingMap.put("tessera_sanitaria", tracking.getHicard());
				trackingMap.put("telefono", tracking.getPhone());
				trackingsList.add(trackingMap);
			}
			ll.put("trackings", trackingsList);
			resultMap.add(ll);
		}
		return resultMap;
	}

	private void initRepos() {
		final boolean FILL_TEST_DATA = false;
		if (gatheringRepo == null) {
			DriverSetter driverSetter = new DriverSetter();
			driverSetter.init();
			gatheringRepo = driverSetter.getGatheringRepo();
			placeRepo = driverSetter.getPlaceRepo();
			trackingRepo = driverSetter.getTrackingRepo();
			if (FILL_TEST_DATA) {
				Tracking t1 = new Tracking();
				t1.setFirstname("Persona 1");
				t1.setCompanyId(1);
				trackingRepo.createTracking(t1);
				Tracking t2 = new Tracking();
				t2.setFirstname("Persona 2");
				t2.setCompanyId(1);
				trackingRepo.createTracking(t2);
				Tracking t3 = new Tracking();
				t3.setFirstname("Persona 3");
				t3.setCompanyId(1);
				trackingRepo.createTracking(t3);
				Tracking t4 = new Tracking();
				t4.setFirstname("Persona 4");
				t4.setCompanyId(1);
				trackingRepo.createTracking(t4);

				Place p1 = new Place();
				p1.setName("Stanza 1");
				p1.setCompanyId(1);
				placeRepo.createPlace(p1);
				Place p2 = new Place();
				p2.setName("Stanza 2");
				p2.setCompanyId(1);
				placeRepo.createPlace(p2);
				Place p3 = new Place();
				p3.setName("Stanza 3");
				p3.setCompanyId(1);
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

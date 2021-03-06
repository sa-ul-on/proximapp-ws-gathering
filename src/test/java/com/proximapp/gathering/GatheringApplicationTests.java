package com.proximapp.gathering;

import com.proximapp.gathering.util.DatetimeManager;
import com.proximapp.gathering.webservice.GatheringController;
import com.proximapp.gathering.webservice.PlaceController;
import com.proximapp.gathering.webservice.TrackingController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GatheringApplicationTests {

	@Test
	void contextLoads() {
		long companyId = 1;
		TrackingController trackingController = new TrackingController();
		trackingController.create("Umberto", "Loria", "Via Guglielmo Marconi 51", "xxxxx", "+393343480234", 1,
				companyId);
		trackingController.create("Orlando", "Napoli", "Via .....", "yyyyy", ".....", 2, companyId);
		trackingController.create("3", "", "", "", "", 3, companyId);
		trackingController.create("4", "", "", "", "", 4, companyId);
		trackingController.create("5", "", "", "", "", 5, companyId);
		trackingController.create("6", "", "", "", "", 6, companyId);
		trackingController.create("7", "", "", "", "", 7, companyId);
		assert trackingController.index(companyId).stream().filter(tracking -> {
			if (tracking.getId() == 1 && tracking.getFirstname().equals("Umberto")
					&& tracking.getLastname().equals("Loria")
					&& tracking.getAddress().equals("Via Guglielmo Marconi 51")
					&& tracking.getHicard().equals("xxxxx")
					&& tracking.getPhone().equals("+393343480234") && tracking.getUserId() == 1
					&& tracking.getCompanyId() == companyId)
				return true;
			else if (tracking.getId() == 2 && tracking.getFirstname().equals("Orlando")
					&& tracking.getLastname().equals("Napoli") && tracking.getAddress().equals("Via .....")
					&& tracking.getHicard().equals("yyyyy") && tracking.getPhone().equals(".....")
					&& tracking.getUserId() == 2 && tracking.getCompanyId() == companyId)
				return true;
			else if (tracking.getId() == 3 && tracking.getFirstname().equals("3")
					&& tracking.getLastname().equals(tracking.getAddress())
					&& tracking.getAddress().equals(tracking.getHicard())
					&& tracking.getHicard().equals(tracking.getPhone()) && tracking.getUserId() == 3
					&& tracking.getCompanyId() == companyId)
				return true;
			else if (tracking.getId() == 4 && tracking.getFirstname().equals("4")
					&& tracking.getLastname().equals(tracking.getAddress())
					&& tracking.getAddress().equals(tracking.getHicard())
					&& tracking.getHicard().equals(tracking.getPhone()) && tracking.getUserId() == 4
					&& tracking.getCompanyId() == companyId)
				return true;
			else if (tracking.getId() == 5 && tracking.getFirstname().equals("5")
					&& tracking.getLastname().equals(tracking.getAddress())
					&& tracking.getAddress().equals(tracking.getHicard())
					&& tracking.getHicard().equals(tracking.getPhone()) && tracking.getUserId() == 5
					&& tracking.getCompanyId() == companyId)
				return true;
			else if (tracking.getId() == 6 && tracking.getFirstname().equals("6")
					&& tracking.getLastname().equals(tracking.getAddress())
					&& tracking.getAddress().equals(tracking.getHicard())
					&& tracking.getHicard().equals(tracking.getPhone()) && tracking.getUserId() == 6
					&& tracking.getCompanyId() == companyId)
				return true;
			else if (tracking.getId() == 7 && tracking.getFirstname().equals("7")
					&& tracking.getLastname().equals(tracking.getAddress())
					&& tracking.getAddress().equals(tracking.getHicard())
					&& tracking.getHicard().equals(tracking.getPhone()) && tracking.getUserId() == 7
					&& tracking.getCompanyId() == companyId)
				return true;
			else
				return false;
		}).count() == 7;

		PlaceController placeController = new PlaceController();
		placeController.create("Stanza 1", companyId);
		placeController.create("Stanza 2", companyId);
		placeController.create("Stanza 3", companyId);
		assert placeController.index(companyId).stream().filter(place -> {
			if (place.getId() == 1 && place.getName().equals("Stanza 1") && place.getCompanyId() == companyId)
				return true;
			else if (place.getId() == 2 && place.getName().equals("Stanza 2") && place.getCompanyId() == companyId)
				return true;
			else if (place.getId() == 3 && place.getName().equals("Stanza 3") && place.getCompanyId() == companyId)
				return true;
			else
				return false;
		}).count() == 3;


		GatheringController gatheringController = new GatheringController();
		assert gatheringController.create(companyId, 1, 2, 1, 0, "2020-11-15 11:34:00") != null;
		assert gatheringController.create(companyId, 2, 1, 1, 0, "2020-11-15 11:35:00") != null;
		assert gatheringController.find(companyId, "", "", "", "").stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 1;
		assert gatheringController.create(companyId, 4, 1, 2, 0, "2020-11-15 12:07:00") != null;
		assert gatheringController.create(companyId, 1, 4, 2, 0, "2020-11-15 12:07:00") != null;
		assert gatheringController.find(companyId, "", "", "", "").stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 2;
		assert gatheringController.create(companyId, 6, 7, 3, 0, "2020-11-15 12:32:00") != null;
		assert gatheringController.create(companyId, 7, 6, 3, 0, "2020-11-15 12:32:00") != null;
		assert gatheringController.find(companyId, "", "", "", "").stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:32:00")
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 3;
		assert gatheringController.create(companyId, 3, 2, 1, 0, "2020-11-15 12:32:00") != null;
		assert gatheringController.create(companyId, 2, 3, 1, 0, "2020-11-15 12:33:00") != null;
		assert gatheringController.find(companyId, "", "", "", "").stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:32:00")
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 4;
		assert gatheringController.create(companyId, 5, 6, 3, 0, "2020-11-15 12:34:00") != null;
		assert gatheringController.create(companyId, 5, 7, 3, 0, "2020-11-15 12:34:00") != null;
		assert gatheringController.create(companyId, 7, 5, 3, 0, "2020-11-15 12:34:00") != null;
		assert gatheringController.create(companyId, 6, 5, 3, 0, "2020-11-15 12:34:00") != null;
		assert gatheringController.find(companyId, "", "", "", "").stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:34:00")
					&& gathering.getTrackings().contains(5L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 4;
		assert gatheringController.create(companyId, 3, 6, 3, 0, "2020-11-15 12:45:00") != null;
		assert gatheringController.create(companyId, 6, 3, 3, 0, "2020-11-15 12:45:00") != null;
		assert gatheringController.create(companyId, 7, 3, 3, 0, "2020-11-15 12:45:00") != null;
		assert gatheringController.create(companyId, 6, 7, 3, 0, "2020-11-15 12:45:00") != null;
		assert gatheringController.create(companyId, 7, 6, 3, 0, "2020-11-15 12:46:00") != null;
		assert gatheringController.create(companyId, 3, 7, 3, 0, "2020-11-15 12:46:00") != null;
		assert gatheringController.find(companyId, "", "", "", "").stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:34:00")
					&& gathering.getTrackings().contains(5L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& placeController.show(gathering.getPlaceId(), companyId).getCompanyId() == companyId
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:45:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:45:00")
					&& gathering.getTrackings().contains(3L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 5;
		assert gatheringController.destroy(1, companyId);
		assert gatheringController.destroy(2, companyId);
		assert gatheringController.destroy(3, companyId);
		assert gatheringController.destroy(4, companyId);
		assert gatheringController.destroy(5, companyId);
		assert placeController.destroy(1, companyId);
		assert placeController.destroy(2, companyId);
		assert placeController.destroy(3, companyId);
		assert trackingController.destroy(1, companyId);
		assert trackingController.destroy(2, companyId);
		assert trackingController.destroy(3, companyId);
		assert trackingController.destroy(4, companyId);
		assert trackingController.destroy(5, companyId);
		assert trackingController.destroy(6, companyId);
		assert trackingController.destroy(7, companyId);
	}

}

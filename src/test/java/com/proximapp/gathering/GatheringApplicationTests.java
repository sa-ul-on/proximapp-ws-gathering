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
		TrackingController trackingController = new TrackingController();
		trackingController.create("Umberto", "Loria", "Via Guglielmo Marconi 51", "xxxxx", "+393343480234");
		trackingController.create("Orlando", "Napoli", "Via .....", "yyyyy", ".....");
		trackingController.create("3", "", "", "", "");
		trackingController.create("4", "", "", "", "");
		trackingController.create("5", "", "", "", "");
		trackingController.create("6", "", "", "", "");
		trackingController.create("7", "", "", "", "");
		assert trackingController.index().stream().filter(tracking -> {
			if (tracking.getId() == 1 && tracking.getNome().equals("Umberto")
					&& tracking.getCognome().equals("Loria")
					&& tracking.getIndirizzo().equals("Via Guglielmo Marconi 51")
					&& tracking.getTesseraSanitaria().equals("xxxxx")
					&& tracking.getTelefono().equals("+393343480234"))
				return true;
			else if (tracking.getId() == 2 && tracking.getNome().equals("Orlando")
					&& tracking.getCognome().equals("Napoli") && tracking.getIndirizzo().equals("Via .....")
					&& tracking.getTesseraSanitaria().equals("yyyyy") && tracking.getTelefono().equals("....."))
				return true;
			else if (tracking.getId() == 3 && tracking.getNome().equals("3")
					&& tracking.getCognome().equals(tracking.getIndirizzo())
					&& tracking.getIndirizzo().equals(tracking.getTesseraSanitaria())
					&& tracking.getTesseraSanitaria().equals(tracking.getTelefono()))
				return true;
			else if (tracking.getId() == 4 && tracking.getNome().equals("4")
					&& tracking.getCognome().equals(tracking.getIndirizzo())
					&& tracking.getIndirizzo().equals(tracking.getTesseraSanitaria())
					&& tracking.getTesseraSanitaria().equals(tracking.getTelefono()))
				return true;
			else if (tracking.getId() == 5 && tracking.getNome().equals("5")
					&& tracking.getCognome().equals(tracking.getIndirizzo())
					&& tracking.getIndirizzo().equals(tracking.getTesseraSanitaria())
					&& tracking.getTesseraSanitaria().equals(tracking.getTelefono()))
				return true;
			else if (tracking.getId() == 6 && tracking.getNome().equals("6")
					&& tracking.getCognome().equals(tracking.getIndirizzo())
					&& tracking.getIndirizzo().equals(tracking.getTesseraSanitaria())
					&& tracking.getTesseraSanitaria().equals(tracking.getTelefono()))
				return true;
			else if (tracking.getId() == 7 && tracking.getNome().equals("7")
					&& tracking.getCognome().equals(tracking.getIndirizzo())
					&& tracking.getIndirizzo().equals(tracking.getTesseraSanitaria())
					&& tracking.getTesseraSanitaria().equals(tracking.getTelefono()))
				return true;
			else
				return false;
		}).count() == 7;

		PlaceController placeController = new PlaceController();
		placeController.create("Stanza 1");
		placeController.create("Stanza 2");
		placeController.create("Stanza 3");
		assert placeController.index().stream().filter(place -> {
			if (place.getId() == 1 && place.getName().equals("Stanza 1"))
				return true;
			else if (place.getId() == 2 && place.getName().equals("Stanza 2"))
				return true;
			else if (place.getId() == 3 && place.getName().equals("Stanza 3"))
				return true;
			else
				return false;
		}).count() == 3;


		GatheringController gatheringController = new GatheringController();
		assert gatheringController.create(1, 2, 1, 0, "2020-11-15 11:34:00");
		assert gatheringController.create(2, 1, 1, 0, "2020-11-15 11:35:00");
		assert gatheringController.index().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 1;
		assert gatheringController.create(4, 1, 2, 0, "2020-11-15 12:07:00");
		assert gatheringController.create(1, 4, 2, 0, "2020-11-15 12:07:00");
		assert gatheringController.index().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 2;
		assert gatheringController.create(6, 7, 3, 0, "2020-11-15 12:32:00");
		assert gatheringController.create(7, 6, 3, 0, "2020-11-15 12:32:00");
		assert gatheringController.index().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:32:00")
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 3;
		assert gatheringController.create(3, 2, 1, 0, "2020-11-15 12:32:00");
		assert gatheringController.create(2, 3, 1, 0, "2020-11-15 12:33:00");
		assert gatheringController.index().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:32:00")
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 4;
		assert gatheringController.create(5, 6, 3, 0, "2020-11-15 12:34:00");
		assert gatheringController.create(5, 7, 3, 0, "2020-11-15 12:34:00");
		assert gatheringController.create(7, 5, 3, 0, "2020-11-15 12:34:00");
		assert gatheringController.create(6, 5, 3, 0, "2020-11-15 12:34:00");
		assert gatheringController.index().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:34:00")
					&& gathering.getTrackings().contains(5L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 4;
		assert gatheringController.create(3, 6, 3, 0, "2020-11-15 12:45:00");
		assert gatheringController.create(6, 3, 3, 0, "2020-11-15 12:45:00");
		assert gatheringController.create(7, 3, 3, 0, "2020-11-15 12:45:00");
		assert gatheringController.create(6, 7, 3, 0, "2020-11-15 12:45:00");
		assert gatheringController.create(7, 6, 3, 0, "2020-11-15 12:46:00");
		assert gatheringController.create(3, 7, 3, 0, "2020-11-15 12:46:00");
		assert gatheringController.index().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:34:00")
					&& gathering.getTrackings().contains(5L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& DatetimeManager.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& DatetimeManager.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
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
		assert gatheringController.destroy(1);
		assert gatheringController.destroy(2);
		assert gatheringController.destroy(3);
		assert gatheringController.destroy(4);
		assert gatheringController.destroy(5);
		assert placeController.destroy(1);
		assert placeController.destroy(2);
		assert placeController.destroy(3);
		assert trackingController.destroy(1);
		assert trackingController.destroy(2);
		assert trackingController.destroy(3);
		assert trackingController.destroy(4);
		assert trackingController.destroy(5);
		assert trackingController.destroy(6);
		assert trackingController.destroy(7);
	}

}

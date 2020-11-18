package com.proximapp.gathering;

import com.proximapp.gathering.webservice.GatheringWS;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

@SpringBootTest
class GatheringApplicationTests {

	@Test
	void contextLoads() {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GatheringWS x = new GatheringWS();
		assert x.newGathering(1, 2, 1, 0, "2020-11-15 11:34:00");
		assert x.newGathering(2, 1, 1, 0, "2020-11-15 11:35:00");
//		System.out.println(x.getGatherings());
		assert x.getGatherings().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 1;
//		System.out.println();
		assert x.newGathering(4, 1, 2, 0, "2020-11-15 12:07:00");
		assert x.newGathering(1, 4, 2, 0, "2020-11-15 12:07:00");
//		System.out.println(x.getGatherings());
		assert x.getGatherings().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 2;
//		System.out.println();
		assert x.newGathering(6, 7, 3, 0, "2020-11-15 12:32:00");
		assert x.newGathering(7, 6, 3, 0, "2020-11-15 12:32:00");
//		System.out.println(x.getGatherings());
		assert x.getGatherings().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:32:00")
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 3;
//		System.out.println();
		assert x.newGathering(3, 2, 1, 0, "2020-11-15 12:32:00");
		assert x.newGathering(2, 3, 1, 0, "2020-11-15 12:33:00");
//		System.out.println(x.getGatherings());
		assert x.getGatherings().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:32:00")
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 4;
//		System.out.println();
		assert x.newGathering(5, 6, 3, 0, "2020-11-15 12:34:00");
		assert x.newGathering(5, 7, 3, 0, "2020-11-15 12:34:00");
		assert x.newGathering(7, 5, 3, 0, "2020-11-15 12:34:00");
		assert x.newGathering(6, 5, 3, 0, "2020-11-15 12:34:00");
//		System.out.println(x.getGatherings());
		assert x.getGatherings().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:34:00")
					&& gathering.getTrackings().contains(5L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 4;
//		System.out.println();
		assert x.newGathering(3, 6, 3, 0, "2020-11-15 12:45:00");
		assert x.newGathering(6, 3, 3, 0, "2020-11-15 12:45:00");
		assert x.newGathering(7, 3, 3, 0, "2020-11-15 12:45:00");
		assert x.newGathering(6, 7, 3, 0, "2020-11-15 12:45:00");
		assert x.newGathering(7, 6, 3, 0, "2020-11-15 12:46:00");
		assert x.newGathering(3, 7, 3, 0, "2020-11-15 12:46:00");
//		System.out.println(x.getGatherings());
		assert x.getGatherings().stream().filter(gathering -> {
			if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 11:34:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 11:35:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(2L)) {
				return true;
			} else if (gathering.getPlaceId() == 2
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:07:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:07:00")
					&& gathering.getTrackings().contains(1L)
					&& gathering.getTrackings().contains(4L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:34:00")
					&& gathering.getTrackings().contains(5L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else if (gathering.getPlaceId() == 1
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:32:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:33:00")
					&& gathering.getTrackings().contains(2L)
					&& gathering.getTrackings().contains(3L)) {
				return true;
			} else if (gathering.getPlaceId() == 3
					&& sdformat.format(gathering.getStartDate()).equals("2020-11-15 12:45:00")
					&& sdformat.format(gathering.getEndDate()).equals("2020-11-15 12:45:00")
					&& gathering.getTrackings().contains(3L)
					&& gathering.getTrackings().contains(6L)
					&& gathering.getTrackings().contains(7L)) {
				return true;
			} else {
				return false;
			}
		}).count() == 5;
//		System.out.println();
	}

}

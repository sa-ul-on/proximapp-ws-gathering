package com.proximapp.gathering.repo.impl;

import com.proximapp.gathering.entity.Tracking;
import com.proximapp.gathering.repo.ITrackingRepo;

import java.util.HashMap;
import java.util.Map;

public class PlainTrackingRepo implements ITrackingRepo {

	private final Map<Long, Tracking> trackings = new HashMap<>();

	public PlainTrackingRepo() {
		Tracking ul_tracking = new Tracking();
		ul_tracking.setId(1);
		ul_tracking.setNome("Umberto");
		ul_tracking.setCognome("Loria");
		ul_tracking.setIndirizzo("Via Guglielmo Marconi 51");
		ul_tracking.setTesseraSanitaria("xxxxx");
		ul_tracking.setTelefono("+393343480234");
		trackings.put(ul_tracking.getId(), ul_tracking);

		Tracking on_tracking = new Tracking();
		on_tracking.setId(2);
		on_tracking.setNome("Orlando");
		on_tracking.setCognome("Napoli");
		on_tracking.setIndirizzo("Via .....");
		on_tracking.setTesseraSanitaria("yyyyy");
		on_tracking.setTelefono(".....");
		trackings.put(on_tracking.getId(), on_tracking);

		Tracking c = new Tracking();
		c.setId(3);
		trackings.put(c.getId(), c);

		Tracking d = new Tracking();
		d.setId(4);
		trackings.put(d.getId(), d);

		Tracking e = new Tracking();
		e.setId(5);
		trackings.put(e.getId(), e);

		Tracking f = new Tracking();
		f.setId(6);
		trackings.put(f.getId(), f);

		Tracking g = new Tracking();
		g.setId(7);
		trackings.put(g.getId(), g);
	}

	@Override
	public Tracking findTrackingById(long trackingId) {
		return trackings.getOrDefault(trackingId, null);
	}

}

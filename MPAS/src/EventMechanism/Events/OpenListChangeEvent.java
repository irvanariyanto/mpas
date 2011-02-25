package EventMechanism.Events;

import java.util.Vector;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;
import algorithms.myPoint;


public class OpenListChangeEvent extends ApplicationEvent {
	private Vector<myPoint> _points;
	public OpenListChangeEvent(ApplicationEventSource source,Vector<myPoint> points) {
		super(source);
		this._points = points;
	}

	@Override
	public String getDescription() {
		return "OpenListChangeEvent";
	}

	public Vector<myPoint> get_points() {
		return _points;
	}

}

package Events;

import java.util.Vector;

import algorithms.myPoint;

public class ClosedListChangeEvent extends ApplicationEvent {
	private Vector<myPoint> _points;
	public ClosedListChangeEvent(ApplicationEventSource source,Vector<myPoint> points) {
		super(source);
		this._points = points;
	}

	@Override
	public String getDescription() {
		return "ClosedListChangeEvent";
	}

	public Vector<myPoint> get_points() {
		return _points;
	}

}

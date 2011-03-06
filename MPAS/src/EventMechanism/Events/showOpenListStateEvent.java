package EventMechanism.Events;

import java.util.Vector;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class showOpenListStateEvent<E> extends ApplicationEvent {
	private Vector<E> _coordinates;
	
	public showOpenListStateEvent(ApplicationEventSource source,Vector<E> coordinates){
		super(source);
		this._coordinates = coordinates;
	}
	@Override
	public String getDescription() {
		return "show OpenList State Event";
	}
	public Vector<E> getCoordinates(){
		return this._coordinates;
	}
}

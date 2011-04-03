package EventMechanism.Events;

import java.util.Vector;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class showStepEvent<E> extends ApplicationEvent {
	private Vector<E> _coordinates;
	
	public showStepEvent(ApplicationEventSource source,Vector<E> coordinates){
		super(source);
		this._coordinates = coordinates;
	}
	@Override
	public String getDescription() {
		return "show step Event";
	}
	public Vector<E> getCoordinates(){
		return this._coordinates;
	}
}

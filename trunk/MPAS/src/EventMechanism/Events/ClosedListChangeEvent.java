package EventMechanism.Events;

import java.util.Vector;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;
import algorithms.myPoint;
import algorithms.Interfaces.StateInterface;

public class ClosedListChangeEvent<E> extends ApplicationEvent {
	private StateInterface<E> _state;
	public ClosedListChangeEvent(ApplicationEventSource source,StateInterface<E> current) {
		super(source);
		this._state = current;
	}

	@Override
	public String getDescription() {
		return "ClosedListChangeEvent";
	}
	
	public StateInterface<E> getState(){
		
		return this._state;
	}


}

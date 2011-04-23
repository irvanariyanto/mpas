package EventMechanism.Events;

import algorithms.Interfaces.StateInterface;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class removeFromOpenListEvent<E> extends ApplicationEvent  {

	private StateInterface<E> _state;
	public removeFromOpenListEvent(ApplicationEventSource source,StateInterface<E> current) {
		super(source);
		this._state = current;
	}

	@Override
	public String getDescription() {
		return "OpenListChangeEvent";
	}
	
	public StateInterface<E> getState(){
		
		return this._state;
	}

}

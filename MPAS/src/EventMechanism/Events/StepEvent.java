package EventMechanism.Events;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;
import algorithms.Interfaces.StateInterface;


public class StepEvent<E> extends ApplicationEvent {
	private StateInterface<E> _state;
	public StepEvent(ApplicationEventSource source,StateInterface<E> current) {
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

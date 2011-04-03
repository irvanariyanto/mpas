package EventMechanism.Events;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class finalPathEvent extends ApplicationEvent {
	private float _cost;
	public finalPathEvent(ApplicationEventSource source,float cost) {
		super(source);
		this._cost = cost;
	}

	@Override
	public String getDescription() {
		return "finalPathEvent";
	}
	public float getCost(){
		return this._cost;
	}

}

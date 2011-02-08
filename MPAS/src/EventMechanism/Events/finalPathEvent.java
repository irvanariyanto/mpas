package EventMechanism.Events;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class finalPathEvent extends ApplicationEvent {

	public finalPathEvent(ApplicationEventSource source) {
		super(source);
	}

	@Override
	public String getDescription() {
		return "finalPathEvent";
	}

}

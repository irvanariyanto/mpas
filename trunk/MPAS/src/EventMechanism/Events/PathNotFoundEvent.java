package EventMechanism.Events;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class PathNotFoundEvent extends ApplicationEvent {

	public PathNotFoundEvent(ApplicationEventSource source) {
		super(source);
	}

	@Override
	public String getDescription() {
		return "Path not found event";
	}

}

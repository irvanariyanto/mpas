package Events;

public class finalPathEvent extends ApplicationEvent {

	public finalPathEvent(ApplicationEventSource source) {
		super(source);
	}

	@Override
	public String getDescription() {
		return "finalPathEvent";
	}

}

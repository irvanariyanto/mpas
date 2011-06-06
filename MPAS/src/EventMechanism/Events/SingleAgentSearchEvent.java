package EventMechanism.Events;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class SingleAgentSearchEvent extends ApplicationEvent {
	private int _agentNum;
	public SingleAgentSearchEvent(ApplicationEventSource source,int agentNum) {
		super(source);
		this._agentNum = agentNum;
	}

	@Override
	public String getDescription() {
		return "Single Agent Search Event";
	}
	public int getAgentNum(){
		return this._agentNum;
	}

}

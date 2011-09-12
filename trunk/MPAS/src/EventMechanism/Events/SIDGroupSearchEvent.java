package EventMechanism.Events;

import java.util.Vector;

import algorithms.myPoint;
import algorithms.AstarID.Agent;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class SIDGroupSearchEvent extends ApplicationEvent {
	public SIDGroupSearchEvent(ApplicationEventSource source,Vector<Agent<myPoint>> agents) {
		super(source);
		this._agents = new Vector<Integer>();
		for (Agent<myPoint> tAgent: agents){
			this._agents.add(tAgent.getId());
		}
	}
	private Vector<Integer> _agents;
	@Override
	public String getDescription() {
		return "SID Group Search Event";
	}
	public Vector<Integer> getAgents(){
		return this._agents;
	}
}

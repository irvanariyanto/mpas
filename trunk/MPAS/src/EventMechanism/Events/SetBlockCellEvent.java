package EventMechanism.Events;

import algorithms.myPoint;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class SetBlockCellEvent extends ApplicationEvent{

	private myPoint _p;
	
	public SetBlockCellEvent(ApplicationEventSource source, myPoint p) {
		super(source);
		this._p = p;
	}
	@Override
	public String getDescription() {
		return "BlockCellEvent";
	}
	
	public myPoint getPosition(){
		return this._p;
	}

}

package EventMechanism.Events;

import java.util.HashMap;

import algorithms.TableKeyInterface;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventSource;

public class ReservationTableUpdateEvent<E> extends ApplicationEvent {
	private HashMap<TableKeyInterface<E>,Integer> _reservTable;
	public ReservationTableUpdateEvent(ApplicationEventSource source,HashMap<TableKeyInterface<E>,Integer> reserv) {
		super(source);
		this._reservTable = reserv;
	}

	@Override
	public String getDescription() {
		return "Reservation table updated event";
	}
	
	public HashMap<TableKeyInterface<E>,Integer> getReservationTable(){
		return this._reservTable;
	}
}

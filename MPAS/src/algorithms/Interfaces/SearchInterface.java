package algorithms.Interfaces;

import java.util.Vector;

import EventMechanism.ApplicationEventListener;

public interface SearchInterface<E> {
	
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,StateInterface<E> goal);
	public void setPause(boolean shouldPause);
	public void resume();
	public void addListener(ApplicationEventListener listener);
}

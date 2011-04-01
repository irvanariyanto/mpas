package algorithms.Interfaces;

import heuristics.HeuristicInterface;

import java.util.Vector;

import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;

public abstract class PausableSearchAlgorithm<E> implements Pausable, SearchInterface<E>,ApplicationEventSource {
	protected ApplicationEventListenerCollection _listeners;
	protected boolean _pause;
	protected HeuristicInterface<StateInterface<E>> _heuristic;
	public PausableSearchAlgorithm(){
		this._listeners = new ApplicationEventListenerCollection();
	}
	@Override
	public void addListener(ApplicationEventListener listener) {
		this._listeners.add(listener);
		
	}
	@Override
	public void removeListener(ApplicationEventListener listener) {
		this._listeners.remove(listener);
		
	}
	@Override
	public void clearListeners() {
		this._listeners.clear();
		
	}
	@Override
	public synchronized void pause() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public synchronized void resume() {
		this.notifyAll();
		
	}
	@Override
	public void setPause(boolean shouldPause) {
		this._pause = shouldPause;		
	}
	@Override
	public abstract Vector<StateInterface<E>> findPath(StateInterface<E> start,StateInterface<E> goal);

	@Override
	public boolean getPause() {
		return this._pause;
	}
}

package algorithms;

import java.util.Vector;

public interface SearchInterface<E> {
	
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,StateInterface<E> goal);
	public void setPause(boolean shouldPause);
	public void resume();
}

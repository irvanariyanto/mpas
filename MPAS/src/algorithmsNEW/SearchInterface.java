package algorithmsNEW;

import java.util.Vector;

public interface SearchInterface<E> {
	
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,StateInterface<E> goal);

}

package algorithms;

import java.util.Vector;

public interface SearchInterface {
	
	public Vector<StateInterface> findPath(StateInterface start,StateInterface goal);

}

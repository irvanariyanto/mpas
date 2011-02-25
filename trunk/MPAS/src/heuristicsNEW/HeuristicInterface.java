package heuristicsNEW;

import algorithmsNEW.StateInterface;

public interface HeuristicInterface<T> {
	public float calcHeuristic(T current,T goal);
}

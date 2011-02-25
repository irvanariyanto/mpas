package heuristics;

import algorithms.StateInterface;

public interface HeuristicInterface<T> {
	public float calcHeuristic(T current,T goal);
}

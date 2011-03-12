package heuristics;

import algorithms.Interfaces.StateInterface;

public interface HeuristicInterface<T> {
	public float calcHeuristic(T current,T goal);
}

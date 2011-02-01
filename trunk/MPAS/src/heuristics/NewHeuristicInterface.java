package heuristics;

import algorithms.StateInterface;

public interface NewHeuristicInterface {
	public float calcHeuristic(StateInterface state,StateInterface goal);
}

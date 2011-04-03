package heuristics;

public interface HeuristicInterface<T> {
	public float calcHeuristic(T current,T goal);
}

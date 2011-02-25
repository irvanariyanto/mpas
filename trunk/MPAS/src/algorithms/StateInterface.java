package algorithms;

import java.util.Vector;

public interface StateInterface<E> {
	public float get_f();
	public Vector<StateInterface<E>> expand();
	public void set_cost(float cost);
	public void set_heuristic(float _heuristic);
	public void set_parent(StateInterface<E> parent);
	public float get_cost();
	public float calcDistance(StateInterface<E> neighbor);
	public StateInterface<E> get_parent();
	public Vector<E> get_Coordinates();
}

package algorithms;

import java.util.Vector;

public interface StateInterface {
	public float get_f();
	public Vector<StateInterface> expand();
	public void set_cost(float cost);
	public void set_heuristic(float _heuristic);
	public void set_parent(StateInterface parent);
	public float get_cost();
	public float calcDistance(StateInterface neighbor);
	public StateInterface get_parent();
}

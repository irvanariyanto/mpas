package algorithms.Interfaces;

import java.util.Vector;

import maps.MapInterface;
import algorithms.myPoint;

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
	public float get_heuristic();
	public MapInterface<E> getMap();
	public StateInterface<E> Convert2SingleAgent(int i);
	public StateInterface<E> CombineStates(Vector<E> Coordinates);
}

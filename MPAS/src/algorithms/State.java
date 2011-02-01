package algorithms;

import java.util.Vector;

import maps.MapInterface;

/**
 * a state in the search tree
 * 
 * @author amit ofer & liron katav
 * 
 */
public class State implements Comparable<State> {

	private Vector<myPoint> _Coordinates;
	private State _parent;
	private float _cost;
	private float _heuristic;

	public State(Vector<myPoint> coordinates) {
		this._Coordinates = coordinates;
		this._cost = 0;
		this._heuristic = 0;
		this._parent = null;
	}

	public State(State state) {
		this._Coordinates = (Vector<myPoint>) state.get_Coordinates().clone();
		this._cost = state.get_cost();
		this._heuristic = state.get_heuristic();
		this._parent = state.get_parent();
	}

	public void set_state(Vector<myPoint> _state) {
		this._Coordinates = _state;
	}

	public Vector<myPoint> get_Coordinates() {
		return _Coordinates;
	}

	public void set_parent(State _parent) {
		this._parent = _parent;
	}

	public State get_parent() {
		return _parent;
	}

	public void set_cost(float _cost) {
		this._cost = _cost;
	}

	public float get_cost() {
		return _cost;
	}

	public void set_heuristic(float _heuristic) {
		this._heuristic = _heuristic;
	}

	public float get_heuristic() {
		return _heuristic;
	}

	public float get_f() {
		return this._heuristic + this._cost;
	}

	@Override
	public int compareTo(State other) {
		float f = this.get_f();
		float of = other.get_f();
		if (f < of) {
			return -1;
		} else if (f > of) {
			return 1;
		} else {
			return 0;
		}
	}
	@Override
	public int hashCode(){
		int ans = 0;
		for (int i = 0 ; i < this._Coordinates.size();i++){
			myPoint p = this._Coordinates.elementAt(i);
			ans += p.getX() + p.getY();
		}
		return ans;
	}
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof State)) {
			return false;
		} else {
			boolean ans = true;
			State s = (State)other;
			for (int i = 0; i < this._Coordinates.size(); i++) {
				if (!this._Coordinates.elementAt(i).equals(
						s.get_Coordinates().elementAt(i))) {
					ans = false;
					break;
				}
			}
			return ans;
		}
	}

	public boolean equals(State other) {
		boolean ans = true;
		for (int i = 0; i < this._Coordinates.size(); i++) {
			if (!this._Coordinates.elementAt(i).equals(
					other.get_Coordinates().elementAt(i))) {
				ans = false;
				break;
			}
		}
		return ans;
	}

	public String toString() {
		String ans = "";
		ans += "Current position: <";
		for (myPoint p : this._Coordinates) {
			ans += p.toString();
		}
		ans += ">\nParent: ";
		if (this._parent == null) {
			ans += "null";
		} else {
			ans += this._parent.toString();
		}
		ans += "\nCost: " + this._cost;
		ans += "\nHeuristic: " + this._heuristic;
		return ans;
	}

}

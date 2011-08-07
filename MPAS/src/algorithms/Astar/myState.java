package algorithms.Astar;

import java.util.Vector;
import java.util.logging.Logger;

import Utils.MyLogger;
import algorithms.myPoint;
import algorithms.Interfaces.StateInterface;



import maps.MapInterface;

public class myState implements Comparable<myState>,StateInterface<myPoint> {
	private Vector<myPoint> _Coordinates;
	private StateInterface<myPoint> _parent;
	private float _cost;
	private float _heuristic;
	private MapInterface<myPoint> _map;
	private int _hashCode;
	
	public myState(Vector<myPoint> coordinates,MapInterface<myPoint> map) {
		this._Coordinates = coordinates;
		this._cost = 0;
		this._heuristic = 0;
		this._parent = null;
		this._map = map;
		this._hashCode = hashCode();
	}
	@Override
	public int hashCode() {
		int x = 1;
		int y = 1;
		for (myPoint p : this._Coordinates){
			x *= p.getX();
			y *= p.getY();
		}
		return x + y;
	}

	public myState(myState state) {
		this._Coordinates = (Vector<myPoint>) state.get_Coordinates().clone();
		this._cost = state.get_cost();
		this._heuristic = state.get_heuristic();
		this._parent = state.get_parent();
		this._map = state.getMap();
		this._hashCode = state.getHashCode();
	}

	private int getHashCode() {
		return this._hashCode;
	}

	public MapInterface<myPoint> getMap() {
		return this._map;
	}

	public void set_state(Vector<myPoint> _state) {
		this._Coordinates = _state;
	}

	public Vector<myPoint> get_Coordinates() {
		return _Coordinates;
	}

	public void set_parent(StateInterface<myPoint> _parent) {
		this._parent = _parent;
	}

	public StateInterface<myPoint> get_parent() {
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
	public int compareTo(myState other) {
		float f = this.get_f();
		float of = other.get_f();
		if (f < of) {
			return -1;
		} else if (f > of) {
			return 1;
		} else {
			if (_heuristic < other.get_heuristic()){
				return -1;
			}
			else{
				return 1;
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof myState)) {
			return false;
		} else {
			boolean ans = true;
			myState s = (myState)other;
			if (this._hashCode != s.getHashCode()){
				ans = false;
			}
			else{
				for (int i = 0; i < this._Coordinates.size(); i++) {
					if (!this._Coordinates.elementAt(i).equals(
							s.get_Coordinates().elementAt(i))) {
						ans = false;
						break;
					}
				}
			}
			return ans;
		}
	}

	public boolean equals(myState other) {
		boolean ans = true;
		if (this._hashCode != other.getHashCode()){
			ans = false;
		}
		else{
			for (int i = 0; i < this._Coordinates.size(); i++) {
				if (!this._Coordinates.elementAt(i).equals(
						other.get_Coordinates().elementAt(i))) {
					ans = false;
					break;
				}
			}
		}
		return ans;
	}

	public String toString() {
		String ans = "";
		ans += "Cost: " + this._cost;
		ans += "\tHeuristic: " + this._heuristic;
		ans += "\tPosition: <";
		for (myPoint p : this._Coordinates) {
			ans += p.toString();
		}
		ans += ">";
		return ans;
	}

	/**
	 * finds all valid neighbours of state. calculates all the permutations of
	 * each of the agents' possible moves
	 * 
	 * @param state
	 * @return
	 */
	@Override
	public Vector<StateInterface<myPoint>> expand() {
		if (this.get_Coordinates().size() == 1) {
			Vector<myPoint> moves = this._map.getAllMoves(this.get_Coordinates().elementAt(0));
			Vector<StateInterface<myPoint>> res = new Vector<StateInterface<myPoint>>();
			for (myPoint p : moves) {
				Vector<myPoint> tCoordinates = new Vector<myPoint>();
				tCoordinates.add(p);
				res.add(new myState(tCoordinates,this._map));
			}
			return res;
		} else {
			Vector<StateInterface<myPoint>> res = new Vector<StateInterface<myPoint>>();
			myState tState = new myState(this);
			myPoint tPoint = tState.get_Coordinates().remove(0);
			Vector<myPoint> moves = this._map.getAllMoves(tPoint);
			Vector<StateInterface<myPoint>> tStates = tState.expand();
			for (myPoint p : moves) {
				for (StateInterface<myPoint> s : tStates) {
					myState tmyState = (myState)s;
					if (checkIfLegal(this, p, tmyState)) {
						Vector<myPoint> tCoordinates = new Vector<myPoint>(tmyState.get_Coordinates());
						tCoordinates.add(0,p);
						myState resState = new myState(tCoordinates,this._map);
						res.add(resState);
					}
				}
			}
			return res;
		}
	}

	private boolean checkIfLegal(myState current, myPoint p, myState s) {
		boolean ans = true;
		Vector<myPoint> tCurrentPoints = current.get_Coordinates();
		Vector<myPoint> tCoordinates = s.get_Coordinates();
		int seifaIndex = tCurrentPoints.size() - tCoordinates.size();
		for (int i = 0; i < tCoordinates.size(); i++) {
			if ( p.equals(tCoordinates.elementAt(i)) || 
					(p.equals(tCurrentPoints.elementAt(seifaIndex + i))) &&
					tCoordinates.elementAt(i).equals(tCurrentPoints.elementAt(seifaIndex - 1))) {
				ans = false;
				break;
			}
		}
		return ans;
	}

	@Override
	public float calcDistance(StateInterface<myPoint> neighbor) {
		float ans = 0;
		myState s = (myState)neighbor;
		for (int i = 0; i < s.get_Coordinates().size();i++){
			ans += this._map.calcDistance(this._Coordinates.elementAt(i), s.get_Coordinates().elementAt(i));
		}
		return ans;
		
	}

	@Override
	public StateInterface<myPoint> Convert2SingleAgent(int i) {
		myPoint myLocation = this._Coordinates.elementAt(i);
		Vector<myPoint> newVector = new Vector<myPoint>();
		newVector.add(myLocation);
		
		return new myState(newVector, this.getMap());
	}

	@Override
	public StateInterface<myPoint> CombineStates(Vector<myPoint> Coordinates,float pathCost,float heuristic) {
		return null;
	}







}


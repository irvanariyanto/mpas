package algorithms.CooperativeAstar;

import java.util.HashMap;
import java.util.Vector;


import maps.MapInterface;
import maps.TileBasedMap;

import algorithms.myPoint;
import algorithms.Interfaces.StateInterface;

public class CAStarState implements CAstarStateInterface<myPoint>,Comparable<CAStarState> {
	private Vector<myPoint> _Coordinates;
	private StateInterface<myPoint> _parent;
	private float _cost;
	private float _heuristic;
	private MapInterface<myPoint> _map;
	private int _t;
	private HashMap<TableKeyInterface<myPoint>,Integer> _reservationTable;
	
	public CAStarState(Vector<myPoint> coordinates,MapInterface<myPoint> mapInterface){
		this._map = mapInterface;
		this._Coordinates = coordinates;
		this._heuristic =  0 ;
		this._cost = 0;
		this._parent = null;
		this._reservationTable = null;
		_t = 0;
	}
	public CAStarState(Vector<myPoint> coordinates, TileBasedMap map,
			HashMap<TableKeyInterface<myPoint>, Integer> reservationTable) {
		this._map = map;
		this._Coordinates = coordinates;
		this._heuristic =  0 ;
		this._cost = 0;
		this._parent = null;
		this._reservationTable = reservationTable;
		_t = 0;
	}

	public CAStarState(Vector<myPoint> coordinates,MapInterface<myPoint> mapInterface,HashMap<TableKeyInterface<myPoint>, Integer> reservTable){
		this._map = mapInterface;
		this._Coordinates = coordinates;
		this._heuristic =  0 ;
		this._cost = 0;
		this._parent = null;
		this._reservationTable = reservTable;
		_t = 0;
	}
	public CAStarState(Vector<myPoint> coordinates, MapInterface<myPoint> map,
			HashMap<TableKeyInterface<myPoint>, Integer> reservTable, int t) {
		this._map = map;
		this._Coordinates = coordinates;
		this._heuristic =  0 ;
		this._cost = 0;
		this._parent = null;
		this._reservationTable = reservTable;
		_t = t;
	}
	public CAStarState(Vector<myPoint> tCombinedCoords,int heuristic,int cost) {
		this._Coordinates = tCombinedCoords;
		this._map = null;
		this._reservationTable = null;
		this._heuristic = heuristic;
		this._cost = cost;
	}


	public CAStarState(Vector<myPoint> coordinates) {
		this._Coordinates = coordinates;
		this._heuristic = 0;
		this._cost = 0; 
		this._map = null;
		this._reservationTable = null;
	}
	public void setReservationTable(HashMap<TableKeyInterface<myPoint>,Integer> map){
		this._reservationTable = map;
	}
	/*
	public static CAStarState convertState(StateInterface<myPoint> start, int i,HashMap<TableKey, Integer> reservTable){
		Vector<myPoint> myCoordinate = new Vector<myPoint>();
		myCoordinate.add(start.get_Coordinates().elementAt(i));
		return new CAStarState(myCoordinate, start.getMap(), reservTable,0);
	}*/
	public int getT(){
		return this._t;
	}
	@Override
	public Vector<StateInterface<myPoint>> expand(){
		Vector<StateInterface<myPoint>> res = new Vector<StateInterface<myPoint>>();
		myPoint myPosition = this.get_Coordinates().elementAt(0);
		Vector<myPoint> allmoves = getMap().getAllMoves(myPosition);
		for (int i = 0; i < allmoves.size();i++){
			myPoint move = allmoves.elementAt(i);
			if (IsLegal(myPosition,move)){
				Vector<myPoint> tCoordinates = new Vector<myPoint>();
				tCoordinates.add(move);
				StateInterface<myPoint> tState = new CAStarState(tCoordinates, this.getMap(), this._reservationTable,this._t + 1);
				res.add(tState);
			}
		}
		return res;
		
	}
	private boolean IsLegal(myPoint myPosition,myPoint move) {
		TableKey spotafter = new TableKey(move.getX(), move.getY(), this._t + 1);
		TableKey spotbefore = new TableKey(move.getX(),move.getY(),this._t);
		TableKey mySpotafter = new TableKey(myPosition.getX(), myPosition.getY(), this._t + 1);
		return !_reservationTable.containsKey(spotafter) &&
				!_reservationTable.containsKey(spotbefore) ||  
							(!_reservationTable.containsKey(mySpotafter) || _reservationTable.get(mySpotafter) != _reservationTable.get(spotbefore));
		
		
	}
	@Override
	public float get_f() {
		return this._heuristic + this._cost;
	}
	@Override
	public void set_cost(float cost) {
		this._cost = cost;
		
	}
	@Override
	public void set_heuristic(float heuristic) {
		this._heuristic = heuristic;
		
	}
	@Override
	public void set_parent(StateInterface<myPoint> parent) {
		this._parent = parent;
	}
	@Override
	public float get_cost() {
		return this._cost;
	}
	@Override
	public float calcDistance(StateInterface<myPoint> neighbor) {
		float ans = 0;
		for (int i = 0; i < neighbor.get_Coordinates().size();i++){
			ans += this._map.calcDistance(this._Coordinates.elementAt(i), neighbor.get_Coordinates().elementAt(i));
		}
		return ans;
	}
	@Override
	public StateInterface<myPoint> get_parent() {
		return this._parent;
	}
	@Override
	public Vector<myPoint> get_Coordinates() {
		return this._Coordinates;
	}
	@Override
	public float get_heuristic() {
		return this._heuristic;
	}
	@Override
	public MapInterface<myPoint> getMap() {
		return this._map;
	}
	@Override
	public int compareTo(CAStarState other) {
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
			else if(_heuristic > other.get_heuristic()){
				return 1;
			}
			else{
				return 0;
			}
		}
	}
	
	public String toString() {
		String ans = "";
		ans += "Position: <";
		for (myPoint p : this._Coordinates) {
			ans += p.toString();
		}
		ans += ">";
		ans += "\nCost: " + this._cost;
		ans += "\nHeuristic: " + this._heuristic;
		ans += "\nTime: " + this._t;
		return ans;
	}
	
	@Override
	public StateInterface<myPoint> Convert2SingleAgent(int i) {
			myPoint myLocation = this._Coordinates.elementAt(i);
			Vector<myPoint> newVector = new Vector<myPoint>();
			newVector.add(myLocation);
			return new CAStarState(newVector, this.getMap(),_reservationTable,_t);
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
		if (!(other instanceof CAStarState)) {
			return false;
		} else {
			boolean ans = true;
			CAStarState s = (CAStarState)other;
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
	@Override
	public StateInterface<myPoint> CombineStates(Vector<myPoint> Coordinates,float pathCost) {
		StateInterface<myPoint> res = new CAStarState(Coordinates);
		res.set_cost(pathCost);
		return res;
	}
}



package maps;

import java.util.Vector;

import algorithms.myPoint;

public class Scenario {
	
	private TileBasedMap _map;
	private Vector<myPoint> _starts;
	private Vector<myPoint> _goals;
	
	public Scenario(TileBasedMap map, Vector<myPoint> starts,Vector<myPoint> goals){
		this._map = map;
		this._starts = starts;
		this._goals = goals;
	}
	
	public TileBasedMap getMap(){
		return this._map;
	}
	public Vector<myPoint> getStartLocations(){
		return this._starts;
	}
	public Vector<myPoint> getGoalLocations(){
		return this._goals;
	}
}

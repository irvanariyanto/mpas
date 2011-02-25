package Controller;

import heuristics.HeuristicInterface;

import java.util.Vector;

import maps.MapInterface;
import maps.TileBasedMap;
import maps.TileStatus;
import maps.TiledMapImpl;

import algorithms.SearchInterface;
import algorithms.StateInterface;
import algorithms.myPoint;
import algorithms.myState;

public class GridController implements ControllerInterFace<myPoint>{
	
	private HeuristicInterface<myPoint> _heuristic;
	private SearchInterface<myPoint> _pathFinder;
	private TileBasedMap _map;
	private int numOfAgents;
	private boolean _diagonal;
	private Vector<StateInterface<myPoint>> _finalPath;
	
	@Override
	public void findPath(Vector<myPoint> starts,
			Vector<myPoint> endPoints) {
		
		StateInterface<myPoint> start = new myState(starts, this._map);
		StateInterface<myPoint> goal = new myState(endPoints,this._map);
		this._pathFinder.findPath(start , goal);
		
		
	}


	public void setMap(int length){
		this._map = new TiledMapImpl(length,length,this._diagonal);
	}
	
	public void setTile(Vector<myPoint> blockedTiles) {
		for (myPoint p : blockedTiles) {
			this._map.setTile(p.getX(), p.getY(), TileStatus.blocked);
		}
	}


	@Override
	public Vector<StateInterface<myPoint>> getPath() {
		return this._finalPath;
	}
}

package Controller;

import heuristics.HeuristicInterface;
import heuristics.ManhattanHeuristic;

import java.io.File;
import java.util.Vector;


import maps.GridMapUtility;
import maps.TileBasedMap;
import maps.TileStatus;
import maps.TiledMapImpl;

import algorithms.AStarSearch;
import algorithms.SearchInterface;
import algorithms.StateInterface;
import algorithms.myPoint;
import algorithms.myState;

public class GridController implements ControllerInterFace<myPoint>{
	
	private HeuristicInterface<StateInterface<myPoint>> _heuristic;
	private SearchInterface<myPoint> _pathFinder;
	private TileBasedMap _map;
	private int _numOfAgents;
	private boolean _diagonal;
	private Vector<StateInterface<myPoint>> _finalPath;
	
	public GridController() {
	//	this._numOfAgents = 2;
		this._heuristic = new ManhattanHeuristic();
		this._pathFinder = new AStarSearch<myPoint>(this._heuristic);	
		this._diagonal = false;
		
		//this._map = new TiledMapImpl(10, 10, this._diagonal);
	}
	@Override
	public void findPath(Vector<myPoint> starts,
			Vector<myPoint> endPoints) {		
		StateInterface<myPoint> start = new myState(starts, this._map);
		StateInterface<myPoint> goal = new myState(endPoints,this._map);
		PathFinderThread t = new PathFinderThread(start, goal);
		t.run();	
	}


	public void setMap(int length){
		this._map = new TiledMapImpl(length,length,this._diagonal);
	}
	
	public void setMap(TileBasedMap map){
		this._map = map;
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
	
	public void setAlgorithm(String chosen) {
		// TODO Auto-generated method stub	
	}

	public void setHeuristic(String chosen) {
		// TODO Auto-generated method stub
		
	}
	public void setDirection(boolean chosen) {
		this._diagonal = chosen;
		
	}
	public void setNumberOfAgents(int num) {
		this._numOfAgents = num;
		
	}
	public void setMapSize(int size) {
		setMap(size);
	}

	
	private class PathFinderThread implements Runnable {
		
		private StateInterface<myPoint> _start;
		private StateInterface<myPoint> _goal;
		
		public PathFinderThread(StateInterface<myPoint> start,StateInterface<myPoint> goal){
			this._start = start;
			this._goal = goal;
		}
		@Override
		public void run() {
			Vector<StateInterface<myPoint>> path = GridController.this._pathFinder.findPath(_start, _goal);
			GridController.this._finalPath = path;
		}
		
	}


	public TileBasedMap getMap() {
		return this._map;
	}



}

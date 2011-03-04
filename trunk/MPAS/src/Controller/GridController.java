package Controller;

import heuristics.HeuristicInterface;
import heuristics.ManhattanHeuristic;

import java.io.File;
import java.util.Vector;


import maps.GridMapUtility;
import maps.TileBasedMap;
import maps.TileStatus;
import maps.TiledMapImpl;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.finalPathEvent;
import algorithms.AStarSearch;
import algorithms.SearchInterface;
import algorithms.StateInterface;
import algorithms.myPoint;
import algorithms.myState;

public class GridController implements ControllerInterFace<myPoint>,ApplicationEventListener,ApplicationEventSource{
	
	private HeuristicInterface<StateInterface<myPoint>> _heuristic;
	private SearchInterface<myPoint> _pathFinder;
	private TileBasedMap _map;
	private int _numOfAgents;
	private boolean _diagonal;
	private Vector<StateInterface<myPoint>> _finalPath;
	private ApplicationEventListenerCollection _listeners;
	public GridController() {
		this._listeners = new ApplicationEventListenerCollection();
	//	this._numOfAgents = 2;
		this._heuristic = new ManhattanHeuristic();
		this._pathFinder = new AStarSearch<myPoint>(this._heuristic);
		((AStarSearch<myPoint>)this._pathFinder).addListener(this); //TODO remove that ugly casting later
		this._diagonal = false;		
		//this._map = new TiledMapImpl(20, 20, this._diagonal);
	}
	@Override
	public void findPath(Vector<myPoint> starts,
			Vector<myPoint> endPoints) {		
		StateInterface<myPoint> start = new myState(starts, this._map);
		StateInterface<myPoint> goal = new myState(endPoints,this._map);
		PathFinderThread t = new PathFinderThread(start, goal);
		t.start();	
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

	//Liron addition
	public void setTile(myPoint blockedTile) {
			this._map.setTile(blockedTile.getX(), blockedTile.getY(), TileStatus.blocked);
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

	
	private class PathFinderThread extends Thread {
		
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
			GridController.this._listeners.fireEvent(new finalPathEvent(GridController.this));
		}
		
	}


	public TileBasedMap getMap() {
		return this._map;
	}
	
	@Override
	public void handle(ApplicationEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addListener(ApplicationEventListener listener) {
		this._listeners.add(listener);
		
	}
	@Override
	public void removeListener(ApplicationEventListener listener) {
		this._listeners.remove(listener);
		
	}
	@Override
	public void clearListeners() {
		this._listeners.clear();
		
	}
	/**
	 * Converts the final path from vector of states to Vector of point vectors
	 * @param path
	 * @return
	 */
	public Vector<Vector<myPoint>> convertPath(Vector<StateInterface<myPoint>> path){
		Vector<Vector<myPoint>> res = new Vector<Vector<myPoint>>();
		int numOfAgents = path.elementAt(0).get_Coordinates().size();
		for (int j = 0; j < numOfAgents ; j++){
			Vector<myPoint> tRoute = new Vector<myPoint>();
			res.add(tRoute);
		}
		for (int i = 0; i < path.size();i++){
			Vector<myPoint> tCoordinates = path.elementAt(i).get_Coordinates();
			for (int j = 0 ; j < tCoordinates.size();j++){
				res.elementAt(j).add(tCoordinates.elementAt(j));
			}
		}
		
		return res;
	}
	public Vector<Vector<myPoint>> getFinalPath(){
		if (this._finalPath!= null){
			return convertPath(_finalPath);
		}
		else
			return null;
	}


}

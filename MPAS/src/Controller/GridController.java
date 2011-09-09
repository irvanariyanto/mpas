package Controller;

import heuristics.DiagonalDistanceHeuristic;
import heuristics.HeuristicInterface;
import heuristics.ManhattanHeuristic;


import java.util.HashMap;
import java.util.Vector;


import maps.Scenario;
import maps.TileBasedMap;
import maps.TileStatus;
import maps.TiledMapImpl;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.ClosedListChangeEvent;
import EventMechanism.Events.OpenListChangeEvent;
import EventMechanism.Events.PathNotFoundEvent;
import EventMechanism.Events.ReservationTableUpdateEvent;
import EventMechanism.Events.SingleAgentSearchEvent;
import EventMechanism.Events.StepEvent;
import EventMechanism.Events.finalPathEvent;
import EventMechanism.Events.removeFromOpenListEvent;
import EventMechanism.Events.showStepEvent;
import algorithms.PointKeyFactory;
import algorithms.TableKeyInterface;
import algorithms.myPoint;
import algorithms.CooperativeAstar.CAStarSearch;
import algorithms.CooperativeAstar.CAStarState;
import algorithms.Interfaces.SearchInterface;
import algorithms.Interfaces.StateInterface;
import algorithms.Astar.AStarSearch;
import algorithms.Astar.myState;
import algorithms.AstarID.AstarSIDSearch;
import algorithms.AstarID.PointGroupsManagerFactory;

public class GridController implements ControllerInterFace<myPoint>,ApplicationEventListener,ApplicationEventSource{
	
	private HeuristicInterface<StateInterface<myPoint>> _heuristic;
	private SearchInterface<myPoint> _pathFinder;
	private TileBasedMap _map;
	private int _numOfAgents;
	private Scenario _scenario;
	private boolean _diagonal;
	private PathFinderThread _pathfinderThread = null;
	private Vector<StateInterface<myPoint>> _finalPath;
	private ApplicationEventListenerCollection _listeners;
	private String _algorithm;
	public GridController() {
		this._listeners = new ApplicationEventListenerCollection();
	//	this._numOfAgents = 2;
	//	this._heuristic = new ManhattanHeuristic();
		this._heuristic = new DiagonalDistanceHeuristic();
		this._diagonal = false;	
		this._scenario = null;
		this._algorithm = "AStar";
		//this._map = new TiledMapImpl(20, 20, this._diagonal);
	}

	public void setScenario(Scenario s){
		this._scenario = s;
		
	}
	public void resumeAlgorithm(){
		this._pathFinder.resume();
	}
	public PathFinderThread getAlgorithmThread(){
		return this._pathfinderThread;
	}
	@Override
	public void findPath(Vector<myPoint> starts,
			Vector<myPoint> endPoints) {	
		StateInterface<myPoint> start;
		StateInterface<myPoint> goal; 
		if (_algorithm.equals("Cooperative A*")){
			PointKeyFactory factory = new PointKeyFactory();
			HashMap<TableKeyInterface<myPoint>, Integer> table = new HashMap<TableKeyInterface<myPoint>, Integer>();
			this._pathFinder = new CAStarSearch<myPoint>(_heuristic,factory,table);
			start = new CAStarState(starts, _map,table);
			goal = new CAStarState(endPoints,_map,table);
			this._pathFinder.addListener(this);
		}
		else if (_algorithm.equals("AStarSID")){
			PointGroupsManagerFactory factory = new PointGroupsManagerFactory();
			this._pathFinder = new AstarSIDSearch<myPoint>(_heuristic, factory);
			start = new myState(starts, this._map);
			goal = new myState(endPoints,this._map);
			this._pathFinder.addListener(this);
		}
		else{
			start = new myState(starts, this._map);
			goal = new myState(endPoints,this._map);
			this._pathFinder = new AStarSearch<myPoint>(this._heuristic);
			this._pathFinder.addListener(this);
		}

		_pathfinderThread = new PathFinderThread(start, goal);
		_pathfinderThread.start();	
	}
	public void runAlgorithmWithPause(Vector<myPoint> starts,Vector<myPoint> endPoints){
		StateInterface<myPoint> start;
		StateInterface<myPoint> goal; 
		if (_algorithm.equals("Cooperative A*")){
			PointKeyFactory factory = new PointKeyFactory();
			HashMap<TableKeyInterface<myPoint>, Integer> table = new HashMap<TableKeyInterface<myPoint>, Integer>();
			this._pathFinder = new CAStarSearch<myPoint>(_heuristic,factory,table);
			start = new CAStarState(starts, _map,table);
			goal = new CAStarState(endPoints,_map,table);
			this._pathFinder.addListener(this);
		}
		else if (_algorithm.equals("AStarSID")){
			PointGroupsManagerFactory factory = new PointGroupsManagerFactory();
			this._pathFinder = new AstarSIDSearch<myPoint>(_heuristic, factory);
			start = new myState(starts, this._map);
			goal = new myState(endPoints,this._map);
			this._pathFinder.addListener(this);
		}
		else{
			start = new myState(starts, this._map);
			goal = new myState(endPoints,this._map);
			this._pathFinder = new AStarSearch<myPoint>(this._heuristic);
			this._pathFinder.addListener(this);
		}
		this._pathFinder.setPause(true);
		_pathfinderThread = new PathFinderThread(start, goal);
		_pathfinderThread.start();
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
	
	//Liron addition
	public void setTile(myPoint blockedTile, TileStatus status) {
			this._map.setTile(blockedTile.getX(), blockedTile.getY(), status);
	}
	
	
	@Override
	public Vector<StateInterface<myPoint>> getPath() {
		return this._finalPath;
	}
	
	public void setAlgorithm(String chosen) {
		this._algorithm = chosen;
	}

	public void setHeuristic(String chosen) {
		if (chosen.equals("Manhattan")){
			this._heuristic = new ManhattanHeuristic();
		}
		else if(chosen.equals("DiagonalDistance")){
			this._heuristic = new DiagonalDistanceHeuristic();
		}
		else{
			this._heuristic = new ManhattanHeuristic();
		}
	}
	public void setDirection(boolean chosen) {
		this._diagonal = chosen;
		if (this._map!=null){
			this._map.setDiagonal(true);
		}
		
	}
	public void setNumberOfAgents(int num) {
		this._numOfAgents = num;
		
	}
	public void setMapSize(int size) {
		setMap(size);
	}

	
	public class PathFinderThread extends Thread {
		
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
			if (path!=null){
				float pathCost = path.elementAt(0).get_cost();
				GridController.this._listeners.fireEvent(new finalPathEvent(GridController.this,pathCost));
			}
			else{
				GridController.this._listeners.fireEvent(new PathNotFoundEvent(GridController.this));
			}
		}
		
	}


	public TileBasedMap getMap() {
		return this._map;
	}
	
	@Override
	public void handle(ApplicationEvent event) {
		if (event instanceof StepEvent){
			StepEvent<myPoint> e = (StepEvent<myPoint>)event;
			this._listeners.fireEvent(new showStepEvent<myPoint>(this,e.getState().get_Coordinates()));
		}
		else if (event instanceof OpenListChangeEvent){
			this._listeners.fireEvent(event);
		}
		else if (event instanceof ClosedListChangeEvent){
			this._listeners.fireEvent(event);
		}
		else if (event instanceof removeFromOpenListEvent){
			this._listeners.fireEvent(event);
		}
		else if (event instanceof SingleAgentSearchEvent){
			this._listeners.fireEvent(event);
		}
		else if (event instanceof ReservationTableUpdateEvent<?>){
			this._listeners.fireEvent(event);
		}
		else{
			this._listeners.fireEvent(event);
		}
		
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

package Controller;

import heuristics.HeuristicInterface;
import heuristics.ManhattanHeuristic;

import java.util.Vector;

import test.moverStub;

import maps.Mover;
import maps.TileBasedMap;
import maps.TileStatus;
import maps.TiledMapImpl;
import Events.ApplicationEvent;
import Events.ApplicationEventListener;
import Events.ApplicationEventListenerCollection;
import Events.ApplicationEventSource;
import Events.ClosedListChangeEvent;
import Events.OpenListChangeEvent;
import Events.finalPathEvent;
import GUI.mainFrame;
import algorithms.AStarPathFinder;
import algorithms.myPoint;

public class Controller implements ControllerInterFace,ApplicationEventSource{
	private AStarPathFinder _pathFinder;
	private TileBasedMap _map;
	private HeuristicInterface _heuristic;
	private mainFrame _mainFrame;
	private ApplicationEventListenerCollection _listeners;
	private pathFinderThread _thread;
	
	public Controller(mainFrame main) {
		this._map = new TiledMapImpl(20, 20, false);
		this._heuristic = new ManhattanHeuristic();
		this._pathFinder = new AStarPathFinder(this._map, this._heuristic);
		this._mainFrame = main;
		this._pathFinder.addListener(new ControllerListener());
		this._listeners = new ApplicationEventListenerCollection();
		this._thread = null;
	}

	public void findPath(myPoint[] startPoints, myPoint[] endPoints,
			Vector<myPoint> _blocked) {
		final Vector<myPoint> starts = arrayToVector(startPoints);
		final Vector<myPoint> ends = arrayToVector(endPoints);
		final Vector<Mover> movers = new Vector<Mover>();
		for (int i = 0; i < startPoints.length; i++) {
			Mover m = new moverStub();
			movers.add(m);
		}
		this._thread = new pathFinderThread(starts,ends,movers);
		this._thread.start();

	}

	public void setTile(Vector<myPoint> blockedTiles) {
		for (myPoint p : blockedTiles) {
			this._map.setTile(p.getX(), p.getY(), TileStatus.blocked);
		}
	}

	public Vector<myPoint> arrayToVector(myPoint[] arr) {
		Vector<myPoint> res = new Vector<myPoint>();
		for (int i = 0; i < arr.length; i++) {
			res.add(arr[i]);
		}
		return res;
	}

	protected class ControllerListener implements ApplicationEventListener {

		@Override
		public void handle(ApplicationEvent event) {
			if (event instanceof finalPathEvent) {
				Controller.this._listeners.fireEvent(event);
			}
			else if (event instanceof OpenListChangeEvent){
				Controller.this._listeners.fireEvent(event);
				//Controller.this._mainFrame.get_grid().setOpenListCell(((OpenListChangeEvent)event).get_points());
			}
			else if(event instanceof ClosedListChangeEvent){
				Controller.this._listeners.fireEvent(event);
				//Controller.this._mainFrame.get_grid().setClosedListCell(((ClosedListChangeEvent)event).get_points());
			}

		}

	}

	@Override
	public void addListener(ApplicationEventListener listener) {
		this._listeners.add(listener);
		
	}

	@Override
	public void clearListeners() {
		this._listeners.clear();
		
	}

	@Override
	public void removeListener(ApplicationEventListener listener) {
		this._listeners.remove(listener);
		
	}
	public pathFinderThread getThread(){
		return this._thread;
	}
	public class pathFinderThread extends Thread{
		private Vector<myPoint> _starts;
		private Vector<myPoint> _ends;
		private Vector<Mover> _movers;
		public pathFinderThread(Vector<myPoint> starts,Vector<myPoint> ends,Vector<Mover> movers){
			this._starts = starts;
			this._ends = ends;
			this._movers = movers;
		}
		public void run(){
			Controller.this._pathFinder.findPath(this._movers, this._starts, this._ends);
		}
	}

	public void ClearPathFinder() {
		this._pathFinder = new AStarPathFinder(this._map,new ManhattanHeuristic());
		
	}
}

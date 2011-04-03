package algorithms.Astar;


import heuristics.HeuristicInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Vector;

import Utils.MyLogger;
import algorithms.Interfaces.Pausable;
import algorithms.Interfaces.PausableSearchAlgorithm;
import algorithms.Interfaces.SearchInterface;
import algorithms.Interfaces.StateInterface;

import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.ClosedListChangeEvent;
import EventMechanism.Events.OpenListChangeEvent;

public class AStarSearch<E> extends PausableSearchAlgorithm<E> {

	private PriorityQueue<StateInterface<E>> _openList;
	private HashSet<StateInterface<E>> _closedList;
	private HashMap<StateInterface<E>,StateInterface<E>> _expaned;
	
	public AStarSearch(HeuristicInterface<StateInterface<E>> heuristic){
		super();
		this._heuristic = heuristic;
		this._openList = new PriorityQueue<StateInterface<E>>();
		this._closedList = new HashSet<StateInterface<E>>();
		this._expaned = new HashMap<StateInterface<E>, StateInterface<E>>();
		this._pause = false;
	}
	public AStarSearch(HeuristicInterface<StateInterface<E>> heuristic,boolean pause){
		super();
		this._heuristic = heuristic;
		this._openList = new PriorityQueue<StateInterface<E>>();
		this._closedList = new HashSet<StateInterface<E>>();
		this._expaned = new HashMap<StateInterface<E>, StateInterface<E>>();
		this._pause = pause;
	}
	@Override
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,StateInterface<E> goal) {
		//init
		start.set_cost(0);
		start.set_heuristic(0);
		start.set_parent(null);
		this._openList.add(start);
		while (!this._openList.isEmpty()){
			boolean tentativeIsBetter = false;
			StateInterface<E> current = this._openList.poll();
			MyLogger.getInstance().info(current.toString());
			if (this._pause && current != start){ // in case running in debug mode
				//System.out.println("node is: " + current.toString());				
				this._listeners.fireEvent(new OpenListChangeEvent<E>(this, current));
				pause();
			}
			if (current.equals(goal)){
				return reconstructPath(start,current);
			}
			this._closedList.add(current);
			this._listeners.fireEvent(new ClosedListChangeEvent<E>(this,current)); // notifying the Controller
			Vector<StateInterface<E>> neighbors = current.expand();
			for (StateInterface<E> neighbor : neighbors){
				if (this._closedList.contains(neighbor))
					continue;
				float tentativeCost = current.get_cost() + current.calcDistance(neighbor);
				neighbor.set_cost(tentativeCost);
				neighbor.set_heuristic(_heuristic.calcHeuristic(neighbor, goal));
				neighbor.set_parent(current);
				if (!this._openList.contains(neighbor)){
					this._openList.add(neighbor);
			//		this._listeners.fireEvent(new OpenListChangeEvent<E>(this,neighbor)); // notifying the Controller
					tentativeIsBetter = true;
				}
				else if(tentativeCost < this._expaned.get(neighbor).get_cost()){
					tentativeIsBetter = true;
				}
				if (tentativeIsBetter){
					this._expaned.put(neighbor, neighbor);	
				}		
			}
			
		}
		return null;
	}
	private Vector<StateInterface<E>> reconstructPath(StateInterface<E> initialState,StateInterface<E> current) {
		Vector<StateInterface<E>> path = new Vector<StateInterface<E>>();
		if (current != null ) {
			while (!current.equals(initialState)) {
				path.add(current);
				current = current.get_parent();
			}

		}
		return path;
	}


}

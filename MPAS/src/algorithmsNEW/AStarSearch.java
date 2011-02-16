package algorithmsNEW;


import heuristicsNEW.NewHeuristicInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Vector;

public class AStarSearch<E> implements SearchInterface<E> {

	private PriorityQueue<StateInterface<E>> _openList;
	private HashSet<StateInterface<E>> _closedList;
	private NewHeuristicInterface _heuristic;
	private HashMap<StateInterface<E>,StateInterface<E>> _expaned;
	public AStarSearch(NewHeuristicInterface heuristic){
		this._heuristic = heuristic;
		this._openList = new PriorityQueue<StateInterface<E>>();
		this._closedList = new HashSet<StateInterface<E>>();
		this._expaned = new HashMap<StateInterface<E>, StateInterface<E>>();
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
			if (current.equals(goal)){
				return reconstructPath(start,current);
			}
			this._closedList.add(current);
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

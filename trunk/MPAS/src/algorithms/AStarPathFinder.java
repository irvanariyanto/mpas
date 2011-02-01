package algorithms;

import heuristics.HeuristicInterface;

import java.util.HashSet;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Vector;

import Events.ApplicationEvent;
import Events.ApplicationEventListener;
import Events.ApplicationEventListenerCollection;
import Events.ApplicationEventSource;
import Events.ClosedListChangeEvent;
import Events.OpenListChangeEvent;
import Events.finalPathEvent;


import maps.Mover;
import maps.TileBasedMap;

/**
 * implements the path finder interface uses a multi-agent A* to calculate the
 * paths for all moving objects in the simulation
 * 
 * @author amit
 * 
 */
public class AStarPathFinder implements PathFinderInterface,ApplicationEventSource{

	private TileBasedMap _map;
	//private Vector<State> _closedList;
	private HashSet<State> _closedList;
	private PriorityQueue<State> _openList;
	private HeuristicInterface _heuristic;
	private ApplicationEventListenerCollection _listeners;
	private Vector<Vector<myPoint>> _finalPath;
	/**
	 * constructor
	 * 
	 * @param map
	 * @param heuristic
	 */
	public AStarPathFinder(TileBasedMap map, HeuristicInterface heuristic) {
		this._map = map;
		this._heuristic = heuristic;
	//	this._closedList = new Vector<State>();
		this._closedList = new HashSet<State>();
		this._openList = new PriorityQueue<State>();
		this._finalPath = null;
		this._listeners = new ApplicationEventListenerCollection();
	}

	@Override
	/**
	 * calculates the paths for all moving objects in the search according
	 * to the A* algorithm
	 */
	public Vector<Vector<myPoint>> findPath(Vector<Mover> movers,
			Vector<myPoint> starts, Vector<myPoint> ends) {
		State initialState = new State(starts);
		initialState.set_heuristic(this._heuristic.getCost(_map, movers,
				starts, ends));
		State finalState = new State(ends);
		State current = null;
		this._openList.add(initialState);
		this._listeners.fireEvent(new OpenListChangeEvent(this,initialState.get_Coordinates()));
		while (this._openList.size() != 0) {
			current = this._openList.poll();
		//	System.out.println(current.toString());
			if (current.equals(finalState)) {
				break;
			}
			this._closedList.add(current);
			this._listeners.fireEvent(new ClosedListChangeEvent(this,current.get_Coordinates()));
			Vector<State> neighbours = getNeighbours(current, movers);
			for (State neighbour : neighbours) {
				float nextStepCost = current.get_cost()
						+ getMovementCost(movers, current, neighbour);
				if (neighbour.get_cost() != 0){
					System.out.println("NOT ZERO!!!!");
				}
				if (nextStepCost < neighbour.get_cost()) {
					if (this._openList.contains(neighbour)) { 
						this._openList.remove(neighbour); 
					}
					if (this._closedList.contains(neighbour)) {
						this._closedList.remove(neighbour);
					}
				}
				if (!this._openList.contains(neighbour)
						&& !this._closedList.contains(neighbour)) { 
																	
					neighbour.set_cost(nextStepCost);
					neighbour.set_heuristic(getHeuristicCost(movers,
							neighbour.get_Coordinates(), ends));
					neighbour.set_parent(current);
					this._openList.add(neighbour);
					this._listeners.fireEvent(new OpenListChangeEvent(this,neighbour.get_Coordinates()));
				}

			}
		}
		Vector<Vector<myPoint>> path = new Vector<Vector<myPoint>>();
		if (current != null && current.equals(finalState)) {
			while (!current.equals(initialState)) {
				path.add(current.get_Coordinates());
				current = current.get_parent();
			}

		}
		this._finalPath = path;
		this._listeners.fireEvent(new finalPathEvent(this));
		this._closedList.clear();
		this._openList.clear();
		return path;
	}

	

	

	/**
	 * returns the heuristic cost from the state with start positions start to
	 * the end points in ends
	 * 
	 * @param movers
	 *            - the type of moving object
	 * @param starts
	 *            - the vector containing the current positions of all agents
	 * @param ends
	 *            - the vector containing the goal positions of all agents
	 * @return
	 */
	private float getHeuristicCost(Vector<Mover> movers,
			Vector<myPoint> starts, Vector<myPoint> ends) {
		return this._heuristic.getCost(this._map, movers, starts, ends);
	}

	/**
	 * calculate the cost of the start positions of all agents to the positions
	 * saved in state
	 * 
	 * @param movers
	 *            - the moving objects
	 * @param current
	 *            - the current positions of all agents in the search
	 * @param st
	 *            - the goal positions of all agents in the search
	 * @return the sum of the costs for all agents
	 */
	private float getMovementCost(Vector<Mover> movers, State current, State st) {
		float ans = 0;
		for (int i = 0; i < current.get_Coordinates().size(); i++) {
			int sx = current.get_Coordinates().elementAt(i).getX();
			int sy = current.get_Coordinates().elementAt(i).getY();
			int tx = st.get_Coordinates().elementAt(i).getX();
			int ty = st.get_Coordinates().elementAt(i).getY();
			ans += this._map.getCost(movers.elementAt(i), sx, sy, tx, ty);
		}
		return ans;
	}

	/**
	 * finds all valid neighbours of state. calculates all the permutations of
	 * each of the agents' possible moves
	 * 
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Vector<State> getNeighbours(State state, Vector<Mover> movers) {
		if (state.get_Coordinates().size() == 1) {
			Vector<myPoint> moves = this._map.getAllMoves(movers.elementAt(0),
					state.get_Coordinates().elementAt(0));
			Vector<State> res = new Vector<State>();
			for (myPoint p : moves) {
				Vector<myPoint> tCoordinates = new Vector<myPoint>();
				tCoordinates.add(p);
				res.add(new State(tCoordinates));
			}
			return res;
		} else {
			Vector<State> res = new Vector<State>();
			State tState = new State(state);
		//	myPoint tPoint = tState.get_Coordinates().remove(tState.get_Coordinates().size() - 1);
			myPoint tPoint = tState.get_Coordinates().remove(0);
			// Mover mover = movers.remove(movers.size() - 1);
		//	Mover mover = movers.get(movers.size() - 1);
			Mover mover = movers.get(0);
			Vector<Mover> tMovers = (Vector<Mover>) movers.clone(); // clone method is not type safe
			Vector<myPoint> moves = this._map.getAllMoves(mover, tPoint);
			Vector<State> tStates = getNeighbours(tState, tMovers);
			for (myPoint p : moves) {
				for (State s : tStates) {
					if (checkIfLegal(state, p, s)) {
						Vector<myPoint> tCoordinates = new Vector<myPoint>(
								s.get_Coordinates());
						tCoordinates.add(0,p);
						State resState = new State(tCoordinates);
						res.add(resState);
					}
				}
			}
			return res;
		}
	}

	private boolean checkIfLegal(State current, myPoint p, State s) {
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

	
	/**
	 * returns the path for a specific agent
	 */
	@Override
	public Vector<myPoint> getPathForAgent(
			Vector<Vector<myPoint>> allAgentsPath, int index) {
		Vector<myPoint> path = new Vector<myPoint>();
		for (int i = 0; i < allAgentsPath.size(); i++) {
			Vector<myPoint> tVector = allAgentsPath.elementAt(i);
			myPoint tPoint = tVector.elementAt(index);
			path.add(tPoint);
		}
		return path;
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


	public Vector<Vector<myPoint>> get_finalPath() {
		return _finalPath;
	}
}

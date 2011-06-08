package algorithms.CooperativeAstar;

import heuristics.HeuristicInterface;

import java.util.HashMap;
import java.util.Vector;

import EventMechanism.ApplicationEventListener;
import EventMechanism.Events.SingleAgentSearchEvent;
import Utils.MyLogger;
import algorithms.Interfaces.PausableSearchAlgorithm;
import algorithms.Interfaces.StateInterface;
import algorithms.Astar.AStarSearch;

public class CAStarSearch<E> extends PausableSearchAlgorithm<E>{

	private HashMap<TableKeyInterface<E>,Integer> _reservTable;
	private AbstractKeyFactory<E> _factory;
	private AStarSearch<E> _AStarSearch;
	
	public CAStarSearch(HeuristicInterface<StateInterface<E>> heuristic,AbstractKeyFactory<E> factory){
		this._heuristic = heuristic;
		this._factory = factory;
		this._reservTable = new HashMap<TableKeyInterface<E>, Integer>();
		}
	public CAStarSearch(HeuristicInterface<StateInterface<E>> heuristic,AbstractKeyFactory<E> factory,
			HashMap<TableKeyInterface<E>, Integer> reservationTable){
		this._heuristic = heuristic;
		this._factory = factory;
		this._reservTable = reservationTable;
		}
	@Override
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,
			StateInterface<E> goal) {
		Vector<StateInterface<E>> finalPath = null;
		boolean pathExists = true;
		int numberOfAgents = start.get_Coordinates().size();
		Vector<Vector<StateInterface<E>>> allpaths = new Vector<Vector<StateInterface<E>>>();
		for (int i = 0 ; i < numberOfAgents; i++){
			E agentLocation = start.get_Coordinates().elementAt(i);
			Vector<E> agentStartVector = new Vector<E>();
			agentStartVector.add(agentLocation);
			StateInterface<E> agentStart = start.Convert2SingleAgent(i);//Create start state for agent i
			StateInterface<E> agentGoal = goal.Convert2SingleAgent(i);//create goal state for agent i
			_AStarSearch = new AStarSearch<E>(_heuristic, this._pause);
			for (ApplicationEventListener listener : this._listeners){
				_AStarSearch.addListener(listener);
			}
			this._listeners.fireEvent(new SingleAgentSearchEvent(this,i+1));
			Vector<StateInterface<E>> singlePath = _AStarSearch.findPath(agentStart, agentGoal);//run A* for agent i and return path
			if (this._pause){
				this.pause();
			}
			if (singlePath == null){
				MyLogger.getInstance().severe("One of the Agents could not find a path!!");
				pathExists = false;
				//break;
			}
			else{
				updateTable(singlePath,i);
				allpaths.add(singlePath);
			}
			
			//update reservation table with path
			
		}
		
		//unite paths
		if (pathExists){
			finalPath = combinePaths(allpaths);
		}
		return finalPath;
	}
	private Vector<StateInterface<E>> combinePaths(
			Vector<Vector<StateInterface<E>>> allpaths) {
		Vector<StateInterface<E>> res = new Vector<StateInterface<E>>();
		float pathCost = 0;
		for (int i = 0 ; i < allpaths.size();i++){
			pathCost += allpaths.elementAt(i).elementAt(0).get_cost();
		}
		int  pathLength = getLongestPath(allpaths);
		for (int step = 0; step < pathLength ;step++){
			StateInterface<E> tState;
			Vector<E> tCombinedCoords = new Vector<E>();
			int tHeuristic = 0;
			int tCost = 0;
			for (int agentNum = 0 ; agentNum < allpaths.size() ; agentNum++){
				Vector<StateInterface<E>> singlePath = allpaths.elementAt(agentNum);
				int diff = pathLength - singlePath.size();
				if (step < diff){
					StateInterface<E> tStepState = singlePath.elementAt(0);
					Vector<E> tCoordinates = tStepState.get_Coordinates();
					E tCoordinate = tCoordinates.elementAt(0);
					tHeuristic += tStepState.get_heuristic();
					tCost+= tStepState.get_cost();
					tCombinedCoords.add(tCoordinate);
				}
				else{
					StateInterface<E> tStepState = singlePath.elementAt(step - diff); 
					Vector<E> tCoordinates = tStepState.get_Coordinates();
					E tCoordinate = tCoordinates.elementAt(0);
					tHeuristic += tStepState.get_heuristic();
					tCost+= tStepState.get_cost();
					tCombinedCoords.add(tCoordinate);
				}
			}
			tState = allpaths.elementAt(0).elementAt(0).CombineStates(tCombinedCoords,tCost,tHeuristic); //TODO ugly solution
			res.add(tState);
		}

		return res;
	}
	/**
	 * returns the length of the longest single path of one of the agents
	 * @param allpaths
	 * @return
	 */
	private int getLongestPath(Vector<Vector<StateInterface<E>>> allpaths) {
		int temp = 0;
		for (int i = 0 ; i < allpaths.size();i++){
			Vector<StateInterface<E>> singlePath = allpaths.elementAt(i);
			if (singlePath.size() > temp){
				temp = singlePath.size();
			}
		}
		return temp;
	}
	private void updateTable(Vector<StateInterface<E>> singlePath,int agentNum) {
	//	this._reservTable.put(key, value) // put initial zero time state
		int step = 0;
		for (int i = singlePath.size() - 1; i >= 0 ;i--){
			StateInterface<E> tStep = singlePath.elementAt(i);
			E data = tStep.get_Coordinates().elementAt(0);
			_reservTable.put(_factory.createKey(data, step+1),agentNum );
			step++;
		}
		
	}
	@Override
	public void resume(){
		if (_AStarSearch!= null){
			_AStarSearch.resume();
		}
		super.resume();
	}
}

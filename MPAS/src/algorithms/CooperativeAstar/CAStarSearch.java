package algorithms.CooperativeAstar;

import heuristics.HeuristicInterface;

import java.util.HashMap;
import java.util.Vector;

import algorithms.Astar.AStarSearch;
import algorithms.Interfaces.PausableSearchAlgorithm;
import algorithms.Interfaces.StateInterface;

public class CAStarSearch<E> extends PausableSearchAlgorithm<E>{

	private HashMap<TableKeyInterface<E>,Integer> _reservTable;
	private HeuristicInterface<StateInterface<E>> _heuristic;
	private AbstractKeyFactory<E> _factory;
	
	public CAStarSearch(HeuristicInterface<E> heuristic){
		
		
	}
	@Override
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,
			StateInterface<E> goal) {
		int numberOfAgents = start.get_Coordinates().size();
		Vector<Vector<StateInterface<E>>> allpaths = new Vector<Vector<StateInterface<E>>>();
		for (int i = 0 ; i < numberOfAgents; i++){
			E agentLocation = start.get_Coordinates().elementAt(i);
			Vector<E> agentStartVector = new Vector<E>();
			agentStartVector.add(agentLocation);
			StateInterface<E> agentStart = start.Convert2SingleAgent(i);//Create start state for agent i
			StateInterface<E> agentGoal = goal.Convert2SingleAgent(i);//create goal state for agent i
			AStarSearch<E> aStar = new AStarSearch<E>(_heuristic, this._pause);
			Vector<StateInterface<E>> singlePath = aStar.findPath(agentStart, agentGoal);//run A* for agent i and return path
			updateTable(singlePath,i);
			allpaths.add(singlePath);
			
			
			//update reservation table with path
			
		}
		
		//unite paths
		return combinePaths(allpaths);
	}
	private Vector<StateInterface<E>> combinePaths(
			Vector<Vector<StateInterface<E>>> allpaths) {
		// TODO Auto-generated method stub
		return null;
	}
	private void updateTable(Vector<StateInterface<E>> singlePath,int agentNum) {
	//	this._reservTable.put(key, value) // put initial zero time state
		for (int i = 0; i <  singlePath.size();i++){
			StateInterface<E> tStep = singlePath.elementAt(i);
			E data = tStep.get_Coordinates().elementAt(0);
			_reservTable.put(_factory.createKey(data, i+1),agentNum );
		}
		
	}

}

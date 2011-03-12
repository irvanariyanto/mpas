package algorithms.CooperativeAstar;

import heuristics.HeuristicInterface;

import java.util.HashMap;
import java.util.Vector;

import algorithms.Astar.AStarSearch;
import algorithms.Interfaces.PausableSearchAlgorithm;
import algorithms.Interfaces.StateInterface;

public class CAStarSearch<E> extends PausableSearchAlgorithm<E>{

	private HashMap<TableKey,Integer> _reservTable;
	private HeuristicInterface<StateInterface<E>> _heuristic;
	@Override
	public Vector<StateInterface<E>> findPath(StateInterface<E> start,
			StateInterface<E> goal) {
		int numberOfAgents = start.get_Coordinates().size();
		for (int i = 0 ; i < numberOfAgents; i++){
			E agentLocation = start.get_Coordinates().elementAt(i);
			Vector<E> agentStartVector = new Vector<E>();
			agentStartVector.add(agentLocation);
			StateInterface<E> agentStart = start.Convert2SingleAgent(i);
			StateInterface<E> agentGoal = goal.Convert2SingleAgent(i);
			AStarSearch<E> aStar = new AStarSearch<E>(_heuristic, this._pause);
			Vector<StateInterface<E>> singlePath = aStar.findPath(agentStart, agentGoal);
			updateTable(singlePath);
			//Create start state for agent i
			//create goal state for agent i
			//run A* for agent i and return path
			//update reservation table with path
			
		}
		
		//unite paths
		return null;
	}
	private void updateTable(Vector<StateInterface<E>> singlePath) {
		// TODO Auto-generated method stub
		
	}

}

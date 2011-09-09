package algorithms.AstarID;

import heuristics.HeuristicInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import algorithms.AbstractKeyFactory;
import algorithms.TableKey;
import algorithms.TableKeyInterface;
import algorithms.myPoint;
import algorithms.Astar.AStarSearch;
import algorithms.Interfaces.PausableSearchAlgorithm;
import algorithms.Interfaces.StateInterface;

public class AstarSIDSearch<E> extends PausableSearchAlgorithm<E> {
	
	
	private AStarSearch<E> _AStarSearch;
	private AbstractGroupsManagerFactory<E> _managerFactory;
	
	public AstarSIDSearch(HeuristicInterface<StateInterface<E>> heuristic,AbstractGroupsManagerFactory<E> factory){
		super();
		this._heuristic = heuristic;
		this._managerFactory = factory;
		this._AStarSearch = new AStarSearch<E>(heuristic);
		}
	
	@Override
	public Vector<StateInterface<E>> findPath(StateInterface<E> start, StateInterface<E> goal) {

		//1+2
		GroupsManagerInterface<StateInterface<E>> groupsManager = this._managerFactory.createManager(start,goal,_AStarSearch);
		//3
		boolean isConflicted = true;
		while (isConflicted){
			isConflicted = false;
			int[] conflicted = groupsManager.simulatePath();
			if (conflicted == null){
				return null;
			}
			else{
				if (conflicted[0] != -1){
					groupsManager.mergeGroups(conflicted[0], conflicted[1]);
					isConflicted = true;
				}
			}
		}
		return groupsManager.combineAllPaths();
		//TODO combine all paths back together
		
		//1. Assign each agent a singleton group
		//2.plan path for all groups
		//3.repeat:
		//	4.simulate all paths until a conflict occurs.
		//	5.merge conflicting groups
		//	6.Cooperatively plan new group
		//7.until there are no conflicts
		//8.combine paths of all groups.
		
	}
	


}

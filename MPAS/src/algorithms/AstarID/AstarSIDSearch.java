package algorithms.AstarID;

import heuristics.HeuristicInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import Utils.MyLogger;
import algorithms.AbstractKeyFactory;
import algorithms.TableKey;
import algorithms.TableKeyInterface;
import algorithms.myPoint;
import algorithms.Astar.AStarSearch;
import algorithms.Interfaces.PausableSearchAlgorithm;
import algorithms.Interfaces.StateInterface;

public class AstarSIDSearch<E> extends PausableSearchAlgorithm<E> implements ApplicationEventListener {
	
	
	private AStarSearch<E> _AStarSearch;
	private AbstractGroupsManagerFactory<E> _managerFactory;
	
	public AstarSIDSearch(HeuristicInterface<StateInterface<E>> heuristic,AbstractGroupsManagerFactory<E> factory){
		super();
		this._heuristic = heuristic;
		this._managerFactory = factory;
		this._AStarSearch = new AStarSearch<E>(heuristic);
		this._AStarSearch.addListener(this);
		}
	
	@Override
	public Vector<StateInterface<E>> findPath(StateInterface<E> start, StateInterface<E> goal) {

		//1+2
		MyLogger.getInstance().info("Initializing groups manager");
		GroupsManagerInterface<StateInterface<E>> groupsManager = this._managerFactory.createManager(start,goal,_AStarSearch,this);
		//3
		boolean isConflicted = true;
		while (isConflicted){
			isConflicted = false;
			MyLogger.getInstance().info("Simulating result");
			int[] conflicted = groupsManager.simulatePath();
			if (conflicted == null){
				MyLogger.getInstance().info("Path not found!");
				return null;
			}
			else{
				if (conflicted[0] != -1){
					MyLogger.getInstance().info("Conflict was found");
					groupsManager.mergeGroups(conflicted[0], conflicted[1]);
					isConflicted = true;
				}
			}
		}
		return groupsManager.combineAllPaths();
	}

	@Override
	public void handle(ApplicationEvent event) {
		this._listeners.fireEvent(event);
		
	}
	


}

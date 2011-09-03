package algorithms.AstarID;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import algorithms.myPoint;
import algorithms.Astar.AStarSearch;
import algorithms.Astar.myState;
import algorithms.Interfaces.StateInterface;

public class Group<E> {
	private int _id;
	private Vector<StateInterface<E>> _path;
	private Map<Integer,Agent<E>> _agents;
	//private AStarSearch<E> _pathFinder;
	private GroupsManager _manager;
	
	public Group(int groupID){
		this._id = groupID;
		this._agents = new HashMap<Integer, Agent<E>>();
	//	this._pathFinder = pathFinder;
	}
	
	public Group(int groupID, Agent<E> agent) {
		this(groupID);
		this._agents.put(agent.getId(), agent);
	//	this._path = initializePath();
	}
	/*
	private Vector<StateInterface<E>> initializePath() {
		int i = 0;
		Vector<E> startCoordinates = new Vector<E>();
		Vector<E> goalCoordinates = new Vector<E>();
		for (Integer agentID : this._agents.keySet()){
			Agent<E> tAgent = this._agents.get(agentID);
			tAgent.setPlaceInVector(i);
			E tStart = tAgent.getStart();
			E tGoal = tAgent.getGoal();
			startCoordinates.add(tStart);
			goalCoordinates.add(tGoal);
			i++;
		}
		StateInterface<E> startState = new myState(startCoordinates);
		StateInterface<E> goalState = new myState(goalCoordinates);
		return _pathFinder.findPath(startState, goalState);
	}
*/
	public void addAgent(Agent<E> agent){
		this._agents.put(agent.getId(), agent);
		agent.setgroupID(this._id);
	}

	
	public int getId() {
		return _id;
	}
	public Vector<StateInterface<E>> getPath() {
		return _path;
	}
	public void setPath(Vector<StateInterface<E>> path) {
		this._path = path;
	}
	public Map<Integer,Agent<E>> getAgents() {
		return _agents;
	}
	public void setAgents(Map<Integer,Agent<E>> agents) {
		this._agents = agents;
	}
	
	
}

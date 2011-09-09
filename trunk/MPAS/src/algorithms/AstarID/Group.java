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
	
	public Group(int groupID){
		this._id = groupID;
		this._agents = new HashMap<Integer, Agent<E>>();
	}
	
	public Group(int groupID, Agent<E> agent) {
		this(groupID);
		this._agents.put(agent.getId(), agent);
	}

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

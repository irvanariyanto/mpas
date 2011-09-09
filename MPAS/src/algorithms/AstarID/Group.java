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
	//private Map<Integer,Agent<E>> _agents;
	private Vector<Agent<E>> _agents;
	
	public Group(int groupID){
		this._id = groupID;
		//this._agents = new HashMap<Integer, Agent<E>>();
		this._agents = new Vector<Agent<E>>();
	}
	
	public Group(int groupID, Agent<E> agent) {
		this(groupID);
		//this._agents.put(agent.getId(), agent);
		this._agents.add(agent);
	}

	public void addAgent(Agent<E> agent){
		//this._agents.put(agent.getId(), agent);
		this._agents.add(agent);
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
	}/*
	public Map<Integer,Agent<E>> getAgents() {
		return _agents;
	}
	public void setAgents(Map<Integer,Agent<E>> agents) {
		this._agents = agents;
	}
	*/
	public Vector<Agent<E>> getAgents(){
		return _agents;
	}
	public void setAgents(Vector<Agent<E>> agents){
		this._agents = agents;
	}
	@Override
	public String toString(){
		String res =  "Group ID: " + this._id + "\n"
				+ "Agents: " ;
		for (Agent tAgent: getAgents()){
			res+= tAgent.getId() + " ";
		}
		res+= "\n" + 
				"Group path: \n" ;
		if (this._path != null){
			for (int step = _path.size() - 1;step >= 0;step--){
				res += _path.elementAt(step).toString();
			}
		}
		else{
			res += "There is no path for this group \n";
		}
		return res;
		
	}
}

package algorithms.AstarID;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import maps.MapInterface;

import sun.management.resources.agent;

import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.SIDGroupSearchEvent;
import Utils.MyLogger;
import algorithms.TableKey;
import algorithms.TableKeyInterface;
import algorithms.myPoint;
import algorithms.Astar.AStarSearch;
import algorithms.Astar.myState;
import algorithms.Interfaces.StateInterface;

public class GroupsManager implements  GroupsManagerInterface<StateInterface<myPoint>>{
	private Vector<Agent<myPoint>> agents;
	private Map<Integer,Group<myPoint>> groups;
	private AStarSearch<myPoint> pathfinder;
	private MapInterface<myPoint> map;
	protected ApplicationEventListenerCollection _listeners;
	public GroupsManager(StateInterface<myPoint> start,StateInterface<myPoint> goal,AStarSearch<myPoint> pathFinder,ApplicationEventListener listener){
		this.agents = new Vector<Agent<myPoint>>();
		this.map = start.getMap();
		this.groups = new HashMap<Integer, Group<myPoint>>();
		this.pathfinder = pathFinder;
		this._listeners = new ApplicationEventListenerCollection();
		this.addListener(listener);
		int numOfAgents = start.get_Coordinates().size();
		for (int i = 0; i < numOfAgents; i++){
			myPoint sPosition = start.get_Coordinates().get(i);
			myPoint gPosition = goal.get_Coordinates().get(i);
			int groupID = IDGenerator.getInstance().NextID();
			Agent<myPoint> tAgent = new Agent<myPoint>(i, sPosition, gPosition, groupID, 0);
			Group<myPoint> tGroup = new Group<myPoint>(groupID,tAgent);
			this.agents.add(tAgent);
			this.groups.put(new Integer(groupID), tGroup);
			initializePath(tGroup);
		}

	}

	private void initializePath(Group<myPoint> group) {
		int i = 0;
		Vector<myPoint> startCoordinates = new Vector<myPoint>();
		Vector<myPoint> goalCoordinates = new Vector<myPoint>();
		for (Agent<myPoint> tAgent : group.getAgents()){
//			Agent<myPoint> tAgent = group.getAgents().get(agentID);
			tAgent.setPlaceInVector(i);
			myPoint tStart = tAgent.getStart();
			myPoint tGoal = tAgent.getGoal();
			startCoordinates.add(tStart);
			goalCoordinates.add(tGoal);
			i++;
		}
		myState startState = new myState(startCoordinates,this.map); 
		myState goalState = new myState(goalCoordinates,this.map);
		this.pathfinder.reset();
		this._listeners.fireEvent(new SIDGroupSearchEvent(this,group.getAgents()));
		group.setPath(this.pathfinder.findPath(startState, goalState));
		MyLogger.getInstance().info("initialized new group:\n" + group.toString());
		
	}

	@Override
	public int[] simulatePath() {
		int[] res = new int[2];
		res[0] = -1;
		res[1] = -1;
		Map<TableKeyInterface<myPoint>,Integer> conflictsTable = new HashMap<TableKeyInterface<myPoint>,Integer>();
		for (Integer groupID : this.groups.keySet()){
			MyLogger.getInstance().info("Adding group " + groupID + " to the conflicts table");
			Vector<StateInterface<myPoint>> path = this.groups.get(groupID).getPath();
			if (path == null){
				return null;
			}
			else{
				for (int timeStep = path.size() - 1; timeStep >= 0 ; timeStep--){
					StateInterface<myPoint> tState = path.get(timeStep);
					int realTimeStep = path.size() - timeStep - 1;
					Vector<myPoint> coordinates = tState.get_Coordinates();
					for (int agentNum = 0; agentNum < coordinates.size();agentNum++){
						int agentID =  this.groups.get(groupID).getAgents().elementAt(agentNum).getId();
						myPoint point = coordinates.get(agentNum);
						if (timeStep == path.size() - 1){
							TableKeyInterface<myPoint> tableKey = new TableKey(point.getX(),point.getY(),realTimeStep);
							conflictsTable.put(tableKey, agentID);
						}
						else{
							myPoint oldSpot =  path.get(timeStep + 1).get_Coordinates().get(agentNum);
							TableKeyInterface<myPoint> tableKey = new TableKey(point.getX(),point.getY(),realTimeStep);
							int conflict = isLegal(oldSpot,point,realTimeStep,conflictsTable);
							if (conflict == -1){
								conflictsTable.put(tableKey, agentID);
							}
							else{								
								res[0] = conflict;
								res[1] = agentID;
								MyLogger.getInstance().info("Conflict was found between agent " + res[0] + " and agent " + res[1]);
								return res;
							}
						}
					}
				}
			}
			MyLogger.getInstance().info("Conflicts table after group " + groupID + "\n" + printConflictsTable(conflictsTable));
		}
		return res;
	}

	@Override
	public void mergeGroups(int firstIndex, int secondIndex) {
		int firstID = agents.get(firstIndex).getgroupID();
		int secondID = agents.get(secondIndex).getgroupID();
		MyLogger.getInstance().info("Merging groups " + firstID + " and " + secondID);
		Group<myPoint> first = groups.get(firstID);
		Group<myPoint> second = groups.get(secondID);
		int groupId = IDGenerator.getInstance().NextID();
		Group<myPoint> newGroup = new Group<myPoint>(groupId);
		for (Agent<myPoint> tAgent : first.getAgents()){
			newGroup.addAgent(tAgent);
			
		}
		for (Agent<myPoint> tAgent : second.getAgents()){
			newGroup.addAgent(tAgent);
		}
		this.groups.remove(firstID);
		this.groups.remove(secondID);
		this.groups.put(groupId, newGroup);
		initializePath(newGroup);
		
	}

	@Override
	public int getAgentGroup(int agentID) {
		return agents.get(agentID).getgroupID();
	}
	
	private int isLegal(myPoint myPosition,myPoint move,int timeStep,Map<TableKeyInterface<myPoint>,Integer> table) {
		TableKey spotafter = new TableKey(move.getX(), move.getY(), timeStep + 1); // where this agent will be if it takes the move after the move
		TableKey spotbefore = new TableKey(move.getX(),move.getY(),timeStep); // the place where the agent is going before it takes the move
		TableKey mySpotafter = new TableKey(myPosition.getX(), myPosition.getY(), timeStep + 1); // my old position after i moved
		//MyLogger.getInstance().info("Check if move from " + toString() + " to " + move.toString() )
		if (table.containsKey(spotafter)){
			return table.get(spotafter);
		}
		else{
			return isCollide(spotbefore,mySpotafter,table);
		}
		
		
	}
	public int isCollide(TableKey spotbefore,TableKey mySpotafter,Map<TableKeyInterface<myPoint>,Integer> table){
		if (!table.containsKey(spotbefore))
			return -1;
		else if (table.containsKey(spotbefore) && table.containsKey(mySpotafter) && table.get(spotbefore).intValue() == table.get(mySpotafter).intValue())
			return table.get(spotbefore).intValue();
		else
			return -1;
	}



	@Override
	public Vector<StateInterface<myPoint>> combineAllPaths() {
		int numOfAgents = agents.size();
		Vector<StateInterface<myPoint>> res = new Vector<StateInterface<myPoint>>();
		int longestPath = 0;
		for (Group<myPoint> tGroup : groups.values()){
			int tSize = tGroup.getPath().size();
			longestPath = longestPath < tSize ? tSize : longestPath;
		}
		for (int step = 0; step < longestPath;step++){

			float tHeuristic = 0;
			float tCost = 0;
			Vector<myPoint> tCoordinates = new Vector<myPoint>(numOfAgents);
			for (int j = 0; j < numOfAgents;j++){
				tCoordinates.add(null);
			}
			for (Group<myPoint> tGroup : groups.values()){
				int diff = longestPath - tGroup.getPath().size();
				StateInterface<myPoint> tState;
				if (step < diff){
					tState= tGroup.getPath().elementAt(0);

				}
				else{
					tState= tGroup.getPath().elementAt(step - diff);
				}
				tHeuristic += tState.get_heuristic();
				tCost += tState.get_cost();
				for (int i = 0; i < tState.get_Coordinates().size();i++){
					myPoint tPoint = tState.get_Coordinates().elementAt(i);
					int tAgentID = tGroup.getAgents().elementAt(i).getId();
					tCoordinates.setElementAt(tPoint,tAgentID);
					
				}

			}
			StateInterface<myPoint> newState = new myState(tCoordinates,null);
			newState.set_heuristic(tHeuristic);
			newState.set_cost(tCost);
			res.add(newState);
		}
		return res;
	}
	public String printConflictsTable(Map<TableKeyInterface<myPoint>,Integer> ctable){
		String res = "";
		for (TableKeyInterface<myPoint> key : ctable.keySet()){
			res += "X: " + key.getCoordinates().getX() + " Y: " + key.getCoordinates().getY() + " T: " + key.getT() + " Agent: " + ctable.get(key) + "\n";
		}
		return res;
	}

	@Override
	public void addListener(ApplicationEventListener listener) {
		this._listeners.add(listener);
		
	}
	@Override
	public void removeListener(ApplicationEventListener listener) {
		this._listeners.remove(listener);
		
	}
	@Override
	public void clearListeners() {
		this._listeners.clear();
		
	}

}

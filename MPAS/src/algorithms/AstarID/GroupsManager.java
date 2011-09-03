package algorithms.AstarID;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import maps.MapInterface;

import sun.management.resources.agent;

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
	public GroupsManager(StateInterface<myPoint> start,StateInterface<myPoint> goal,AStarSearch<myPoint> pathFinder){
		this.agents = new Vector<Agent<myPoint>>();
		this.map = start.getMap();
		this.groups = new HashMap<Integer, Group<myPoint>>();
		this.pathfinder = pathFinder;
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
		for (Integer agentID : group.getAgents().keySet()){
			Agent<myPoint> tAgent = group.getAgents().get(agentID);
			tAgent.setPlaceInVector(i);
			myPoint tStart = tAgent.getStart();
			myPoint tGoal = tAgent.getGoal();
			startCoordinates.add(tStart);
			goalCoordinates.add(tGoal);
			i++;
		}
		myState startState = new myState(startCoordinates,this.map); 
		myState goalState = new myState(goalCoordinates,this.map);
		group.setPath(this.pathfinder.findPath(startState, goalState));
		
	}

	@Override
	public int[] simulatePath() {
		int[] res = new int[2];
		res[0] = -1;
		res[1] = -1;
		Map<TableKeyInterface<myPoint>,Integer> conflictsTable = new HashMap<TableKeyInterface<myPoint>,Integer>();
		for (Integer groupID : this.groups.keySet()){
			Vector<StateInterface<myPoint>> path = this.groups.get(groupID).getPath();
			for (int timeStep = 0; timeStep < path.size(); timeStep++){
				StateInterface<myPoint> tState = path.get(timeStep);
				Vector<myPoint> coordinates = tState.get_Coordinates();
				for (int agentNum = 0; agentNum < coordinates.size();agentNum++){
					myPoint point = coordinates.get(agentNum);
					myPoint oldSpot =  timeStep == 0 ? agents.get(agentNum).getStart() : path.get(timeStep - 1).get_Coordinates().get(agentNum);
					TableKeyInterface<myPoint> tableKey = new TableKey(point.getX(),point.getY(),timeStep);
					int conflict = isLegal(oldSpot,point,timeStep,conflictsTable);
					if (conflict == -1){
						conflictsTable.put(tableKey, agentNum);
					}
					else{
						res[0] = conflict;
						res[1] = agentNum;
						return res;
					}
				}
				
			}
			
		}
		return res;
	}

	@Override
	public void mergeGroups(int firstIndex, int secondIndex) {
		Group<myPoint> first = groups.get(agents.get(firstIndex).getgroupID());
		Group<myPoint> second = groups.get(agents.get(secondIndex).getgroupID());
		int groupId = IDGenerator.getInstance().NextID();
		Group<myPoint> newGroup = new Group<myPoint>(groupId);
		for (Agent<myPoint> agentID : first.getAgents().values()){
			newGroup.addAgent(agentID);
			
		}
		for (Agent<myPoint> agentID : second.getAgents().values()){
			newGroup.addAgent(agentID);
		}
		this.groups.remove(first);
		this.groups.remove(second);
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

	public Vector<StateInterface<myPoint>> mergeAllPaths(){
		
		
		return null;
	}
}

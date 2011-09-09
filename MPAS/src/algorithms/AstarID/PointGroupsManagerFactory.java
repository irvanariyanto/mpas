package algorithms.AstarID;

import maps.MapInterface;
import EventMechanism.ApplicationEventListener;
import algorithms.myPoint;
import algorithms.Astar.AStarSearch;
import algorithms.Interfaces.StateInterface;

public class PointGroupsManagerFactory extends AbstractGroupsManagerFactory<myPoint>		 {


	@Override
	public GroupsManagerInterface<StateInterface<myPoint>> createManager(
			StateInterface<myPoint> start, StateInterface<myPoint> goal,
			AStarSearch<myPoint> pathfinder,ApplicationEventListener listener) {
		// TODO Auto-generated method stub
		return new GroupsManager(start, goal, pathfinder,listener);
	}



}

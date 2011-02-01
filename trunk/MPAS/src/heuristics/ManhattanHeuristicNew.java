package heuristics;

import java.util.Vector;

import algorithms.StateInterface;
import algorithms.myPoint;
import algorithms.myState;

public class ManhattanHeuristicNew implements NewHeuristicInterface {

	@Override
	public float calcHeuristic(StateInterface state, StateInterface goal) {
		myState tState = (myState)state;
		myState tGoal = (myState)goal;
		Vector<myPoint> start = tState.get_Coordinates();
		Vector<myPoint> ends = tGoal.get_Coordinates(); 
		float res = 0;
		for (int i = 0;i < start.size();i++){
			myPoint s = start.elementAt(i);
			myPoint e = ends.elementAt(i);
			res += (Math.abs(s.getX()-e.getX()) + Math.abs(s.getY()-e.getY()));
		}
		return res;
	}

}

package heuristics;

import heuristics.HeuristicInterface;

import java.util.Vector;


import algorithms.myPoint;
import algorithms.myState;

public class ManhattanHeuristic implements HeuristicInterface<myState> {

	@Override
	public float calcHeuristic(myState current, myState goal) {
		Vector<myPoint> start = current.get_Coordinates();
		Vector<myPoint> ends = goal.get_Coordinates(); 
		float res = 0;
		for (int i = 0;i < start.size();i++){
			myPoint s = start.elementAt(i);
			myPoint e = ends.elementAt(i);
			res += (Math.abs(s.getX()-e.getX()) + Math.abs(s.getY()-e.getY()));
		}
		return res;
	}

}

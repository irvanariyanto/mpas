package heuristics;

import heuristics.HeuristicInterface;

import java.util.Vector;


import algorithms.myPoint;
import algorithms.Interfaces.StateInterface;

public class ManhattanHeuristic implements HeuristicInterface<StateInterface<myPoint>> {
/*
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
*/
	@Override
	public float calcHeuristic(StateInterface<myPoint> current,	StateInterface<myPoint> goal) {
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

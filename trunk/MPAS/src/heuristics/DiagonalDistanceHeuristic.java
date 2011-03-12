package heuristics;

import java.util.Vector;

import algorithms.myPoint;
import algorithms.Interfaces.StateInterface;

public class DiagonalDistanceHeuristic implements HeuristicInterface<StateInterface<myPoint>> {

	@Override
	public float calcHeuristic(StateInterface<myPoint> current,
			StateInterface<myPoint> goal) {
		float res = 0;
		Vector<myPoint> startCords = current.get_Coordinates();
		Vector<myPoint> goalCords = goal.get_Coordinates();
		for (int i = 0 ; i < startCords.size();i++){
			myPoint s = startCords.elementAt(i);
			myPoint g = goalCords.elementAt(i);
			float hDiagonal = Math.min(Math.abs(s.getX() - g.getX()), Math.abs(s.getY() - g.getY()));
			float hStraight = Math.abs(s.getX() - g.getX()) + Math.abs(s.getY() - g.getY());
			res+= Math.sqrt(2) * hDiagonal + 1 * (hStraight - 2*hDiagonal);
		}
		return res;
	}

}

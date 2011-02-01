package heuristics;

import java.util.Vector;

import maps.Mover;
import maps.TileBasedMap;
import algorithms.myPoint;

public class ManhattanHeuristic implements HeuristicInterface {
	/**
	 * the sum of all Manhattan values for all agents in the search
	 */
	@Override
	public float getCost(TileBasedMap map, Vector<Mover> movers,
			Vector<myPoint> start, Vector<myPoint> ends) {
		float res = 0;
		for (int i = 0;i < start.size();i++){
			myPoint s = start.elementAt(i);
			myPoint e = ends.elementAt(i);
			res += (Math.abs(s.getX()-e.getX()) + Math.abs(s.getY()-e.getY()));
		}
		return res;
	}

}

package heuristics;

import java.util.Vector;
import algorithms.myPoint;
import maps.Mover;
import maps.TileBasedMap;
/**
 * the interface for the multi-agent heuristics functions
 * @author amit ofer & liron katav
 *
 */
public interface HeuristicInterface {
	
	public float getCost(TileBasedMap map, Vector<Mover> movers,Vector<myPoint> start,Vector<myPoint> ends);
}

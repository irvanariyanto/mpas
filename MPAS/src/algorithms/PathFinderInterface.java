package algorithms;

import java.util.Vector;

import maps.Mover;
/**
 * interface for all multi-agent search algorithms
 * @author amit ofer & liron katav
 *
 */
public interface PathFinderInterface {
	
	public Vector<Vector<myPoint>> findPath(Vector<Mover> movers,Vector<myPoint> starts,Vector<myPoint> ends);
	
	public Vector<myPoint> getPathForAgent(Vector<Vector<myPoint>> allAgentsPath,int index);
	
}

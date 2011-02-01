package maps;

import java.util.Vector;
import algorithms.myPoint;

public interface MapInterface {

	public Vector<myPoint> getAllMoves(myPoint position);
	public double calcDistance(myPoint from,myPoint to);
}

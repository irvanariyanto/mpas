package maps;

import java.util.Vector;

import algorithmsNEW.myPoint;

public interface MapInterface {

	public Vector<myPoint> getAllMoves(myPoint position);
	public double calcDistance(myPoint from,myPoint to);
}

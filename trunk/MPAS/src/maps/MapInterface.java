package maps;

import java.util.Vector;

import algorithms.myPoint;

public interface MapInterface<T> {

	public Vector<T> getAllMoves(T position);
	public double calcDistance(T from,T to);
}

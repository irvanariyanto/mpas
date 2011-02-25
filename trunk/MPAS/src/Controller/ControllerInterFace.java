package Controller;

import java.util.Vector;

import algorithms.StateInterface;
import algorithms.myPoint;

public interface ControllerInterFace<E> {
	

	public void findPath(Vector<E>starts ,Vector<E> endPoints);
	
	public Vector<StateInterface<E>> getPath();
}

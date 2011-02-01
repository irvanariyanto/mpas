package Controller;

import java.util.Vector;

import algorithms.myPoint;

public interface ControllerInterFace {
	
	/**
	 * returns the final Path
	 * @param _startPoints
	 * @param _endPoints
	 * @param _blocked
	 * @return
	 */
	public void findPath(myPoint[] startPoints,
									myPoint[] endPoints, 
									Vector<myPoint> _blocked);

}

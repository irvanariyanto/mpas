package tests;

import java.util.PriorityQueue;
import java.util.Vector;

import algorithms.myPoint;
import algorithms.Astar.myState;


public class PriorityQueueTest {
	public static void main (String[] args){
		myPoint p1 = new myPoint(1,1);
		Vector<myPoint> v1 = new Vector<myPoint>();
		v1.add(p1);
		myState s1 = new myState(v1,null);
		s1.set_cost(0);
		s1.set_heuristic(5);
		myState s2 = new myState(v1,null);
		s2.set_cost(1);
		s2.set_heuristic(4);
		myState s3 = new myState(v1,null);
		s3.set_cost(1);
		s3.set_heuristic(4);
		myState s4 = new myState(v1,null);
		s4.set_cost(1);
		s4.set_heuristic(4);
		myState s5 = new myState(v1,null);
		s5.set_cost(0);
		s5.set_heuristic(5);
		PriorityQueue<myState> openList = new PriorityQueue<myState>();
		openList.add(s1);
		openList.add(s2);
		openList.add(s3);
	//	openList.add(s4);
	//	openList.add(s5);
		openList.poll();
		openList.poll();
		openList.poll();
	//	openList.poll();
	//	openList.poll();
		System.out.println("Done!");
	}
}

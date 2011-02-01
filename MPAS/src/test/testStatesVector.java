package test;

import java.util.PriorityQueue;
import java.util.Vector;

import algorithms.myPoint;
import algorithms.State;

public class testStatesVector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		myPoint p1 = new myPoint(1,2);
		myPoint p2 =new myPoint (1,2);
		myPoint p3 = new myPoint (2,1);
		myPoint p4 = new myPoint (2,1);
		Vector<myPoint> v1 = new Vector<myPoint>();
		v1.add(p1);
		v1.add(p3);
		Vector<myPoint> v2 = new Vector<myPoint>();
		v2.add(p2);
		v2.add(p4);
		State s1 = new State(v1);
		State s2 = new State(v2);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println("\n\n\n");
		System.out.println(s2.equals(s1));
		PriorityQueue<State> states1 = new PriorityQueue<State>();
		states1.add(s1);
		
		System.out.println(states1.contains(s1));
		System.out.println(states1.contains(s2));
		
	}

}

package test;

import heuristics.ManhattanHeuristic;

import java.util.Vector;

import algorithms.AStarPathFinder;
import algorithms.myPoint;
import algorithms.State;
import maps.Mover;
import maps.TiledMapImpl;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TiledMapImpl map = new TiledMapImpl(5,5,false);
		/*
		map.setTile(2, 4, TileStatus.blocked);
		map.setTile(2, 2, TileStatus.blocked);
		map.setTile(2, 1, TileStatus.blocked);
		map.setTile(0, 3, TileStatus.blocked);
		map.setTile(1, 2, TileStatus.blocked);
		map.setTile(3, 2, TileStatus.blocked);
		map.setTile(4, 0, TileStatus.blocked);
		*/
		Mover mover = new moverStub();
		Mover mover2 = new moverStub();
	//	Mover mover3 = new moverStub();
		AStarPathFinder pathfinder = new AStarPathFinder(map, new ManhattanHeuristic());
		myPoint p = new myPoint(1,2);
		myPoint p2 = new myPoint(2,2);
	//	System.out.println("Start point is: " + p.toString());
	//	Vector<Point> moves = map.getAllMoves(mover, p);
	//	System.out.println("All possible moves are:");
	//	for (Point tPoint : moves){
	//		System.out.println(tPoint.toString());
	//	}
		Vector<myPoint> coordinates = new Vector<myPoint>();
		coordinates.add(p);
		coordinates.add(p2);

		//coordinates.add(p3);
		State s = new State(coordinates);
		Vector<Mover> movers = new Vector<Mover>();
		movers.add(mover);
		movers.add(mover2);
		//movers.add(mover3);
	//	Vector<State> res = pathfinder.getNeighbours(s,movers);
	//	System.out.println("\nNext states are: (" + res.size() + ")\n");
		//for (State st : res){
	//		System.out.println(st.toString());
	//	}
		myPoint p4 = new myPoint(3,2);
		myPoint p5 = new myPoint(0,2);
		Vector<myPoint> ends = new Vector<myPoint>();
		ends.add(p4);
		ends.add(p5);

		Vector<Vector<myPoint>> path = pathfinder.findPath(movers, coordinates, ends);
		
		System.out.println("Path size is: " + path.size() +"\n");
		for (Vector<myPoint> tVector : path){
			for (myPoint tPoint : tVector)
			System.out.print(tPoint);
			System.out.println();
		}

	}

}

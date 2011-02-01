package test;

import heuristics.ManhattanHeuristic;

import java.util.Vector;

import algorithms.AStarPathFinder;
import algorithms.myPoint;
import maps.Mover;
import maps.TileStatus;
import maps.TiledMapImpl;

public class test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TiledMapImpl map = new TiledMapImpl(5,5,false);
		map.setTile(2, 4, TileStatus.blocked);
		map.setTile(2, 2, TileStatus.blocked);
		map.setTile(2, 1, TileStatus.blocked);
		map.setTile(0, 3, TileStatus.blocked);
		map.setTile(1, 2, TileStatus.blocked);
		map.setTile(3, 2, TileStatus.blocked);
		map.setTile(4, 0, TileStatus.blocked);

		Mover mover = new moverStub();
		AStarPathFinder pathfinder = new AStarPathFinder(map, new ManhattanHeuristic());
		myPoint p = new myPoint(4,4);
		Vector<myPoint> coordinates = new Vector<myPoint>();
		coordinates.add(p);
		System.out.println("Start point is: " + p.toString());
		Vector<myPoint> moves = map.getAllMoves(mover, p);
		System.out.println("All possible moves are:");
		for (myPoint tPoint : moves){
			System.out.println(tPoint.toString());
		}
		Vector<Mover> movers = new Vector<Mover>();
		movers.add(mover);
		myPoint p4 = new myPoint(0,0);
		Vector<myPoint> ends = new Vector<myPoint>();
		ends.add(p4);
		System.out.println("trying the A star:\n");
		Vector<Vector<myPoint>> path = pathfinder.findPath(movers, coordinates, ends);
		
		System.out.println("Path size is: " + path.size() +"\n");
		for (Vector<myPoint> tVector : path){
			for (myPoint tPoint : tVector)
			System.out.print(tPoint);
			System.out.println();
		}

	}

}

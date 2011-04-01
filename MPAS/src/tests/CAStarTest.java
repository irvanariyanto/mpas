package tests;

import heuristics.HeuristicInterface;
import heuristics.ManhattanHeuristic;

import java.util.HashMap;
import java.util.Vector;

import algorithms.myPoint;
import algorithms.CooperativeAstar.CAStarSearch;
import algorithms.CooperativeAstar.CAStarState;
import algorithms.CooperativeAstar.PointKeyFactory;
import algorithms.CooperativeAstar.TableKey;
import algorithms.CooperativeAstar.TableKeyInterface;
import algorithms.Interfaces.SearchInterface;
import algorithms.Interfaces.StateInterface;
import maps.TileBasedMap;
import maps.TileStatus;
import maps.TiledMapImpl;

public class CAStarTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TileBasedMap map = new TiledMapImpl(5, 5, false);
		map.setTile(1, 2, TileStatus.blocked);
		map.setTile(2, 1, TileStatus.blocked);
		map.setTile(2, 2, TileStatus.blocked);
		map.setTile(2, 4, TileStatus.blocked);
		map.setTile(4, 0, TileStatus.blocked);
		map.setTile(3, 1, TileStatus.blocked);
		map.setTile(4, 1, TileStatus.blocked);
		myPoint s1 = new myPoint(4,4);
		myPoint s2 = new myPoint(3,0);
		myPoint g1 = new myPoint(1,1);
		myPoint g2 = new myPoint(0,4);
		Vector<myPoint> vStart = new Vector<myPoint>();
		Vector<myPoint> vFinish = new Vector<myPoint>();
		vStart.add(s1);
		vStart.add(s2);
		vFinish.add(g1);
		vFinish.add(g2);
		HashMap<TableKeyInterface<myPoint>,Integer> reservationTable = new HashMap<TableKeyInterface<myPoint>, Integer>();
		StateInterface<myPoint> start = new CAStarState(vStart, map,reservationTable);
		StateInterface<myPoint> goal = new CAStarState(vFinish,map,reservationTable);
		HeuristicInterface<StateInterface<myPoint>> h = new ManhattanHeuristic();
		PointKeyFactory factory = new PointKeyFactory();
		SearchInterface<myPoint> search = new CAStarSearch<myPoint>(h,factory,reservationTable);
		Vector<StateInterface<myPoint>> path = search.findPath(start, goal);
		}

}

package tests;
/*
import heuristicsNEW.ManhattanHeuristic;
import heuristicsNEW.HeuristicInterface;

import java.util.Vector;
import junit.framework.TestCase;
import maps.TileStatus;
import maps.TiledMapImpl;
import org.junit.Before;
import algorithmsNEW.AStarSearch;
import algorithmsNEW.SearchInterface;
import algorithmsNEW.StateInterface;
import algorithmsNEW.myPoint;
import algorithmsNEW.myState;


public class testAStarSearch extends TestCase {
	private TiledMapImpl _map;
	@Before
	public void setUp() throws Exception {
		this._map = new TiledMapImpl(5, 5, false);
		this._map.setTile(1, 2, TileStatus.blocked);
		this._map.setTile(2, 1, TileStatus.blocked);
		this._map.setTile(2, 2, TileStatus.blocked);
		this._map.setTile(2, 4, TileStatus.blocked);
		this._map.setTile(4, 0, TileStatus.blocked);
		this._map.setTile(3, 1, TileStatus.blocked);
		this._map.setTile(4, 1, TileStatus.blocked);
		
	}

	public void testMoves(){
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
		StateInterface start = new myState(vStart, _map);
		StateInterface goal = new myState(vFinish,_map);
		HeuristicInterface h = new ManhattanHeuristic();
		SearchInterface search = new AStarSearch(h);
		Vector<StateInterface> path = search.findPath(start, goal);
		for (int i = 0; i < path.size();i++){
			System.out.println(path.elementAt(i).toString());
		}

		
	}
	public boolean AssertEquals(Vector<myPoint> expected,Vector<myPoint> actual){
		boolean ans = true;
		for (int i = 0; i < expected.size();i++){
			if (!expected.elementAt(i).equals(actual.elementAt(i))){
				ans = false;
				break;
			}
		}
		return ans;
	}
	public void main(){
		
	}
}
*/
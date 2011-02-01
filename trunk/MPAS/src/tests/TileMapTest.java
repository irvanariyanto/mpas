package tests;

import java.util.Vector;

import org.junit.Before;

import junit.framework.TestCase;
import maps.Mover;
import maps.TileStatus;
import maps.TiledMapImpl;
import test.moverStub;
import algorithms.myPoint;


public class TileMapTest extends TestCase {
	
	private TiledMapImpl _map;
	@Before
	public void setUp() throws Exception {
		this._map = new TiledMapImpl(5, 5, false);
		this._map.setTile(1, 2, TileStatus.blocked);
		this._map.setTile(2, 1, TileStatus.blocked);
		this._map.setTile(2, 2, TileStatus.blocked);
		this._map.setTile(2, 4, TileStatus.blocked);
		this._map.setTile(4, 0, TileStatus.blocked);
		
	}
	
	public void testMoves(){
		Mover mover1 = new moverStub();
		myPoint p1 = new myPoint(4,1);
		myPoint res1 = new myPoint(3,1);
		myPoint res2 = new myPoint(4,2);
		Vector<myPoint> moves = this._map.getAllMoves(mover1, p1);
		Vector<myPoint> result = new Vector<myPoint>();
		result.add(res1);
		result.add(p1);
		result.add(res2);
		AssertEquals(result,moves);
		myPoint p2 = new myPoint (1,1);
		myPoint res3 = new myPoint(1,0);
		myPoint res4 = new myPoint(0,1);
		Vector<myPoint> result2 = new Vector<myPoint>();
		result2.add(res3);
		result2.add(p2);
		result2.add(res4);
		AssertEquals(result2, this._map.getAllMoves(mover1, p2));
		
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
}

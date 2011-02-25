package test;

import java.util.Vector;

import maps.TileBasedMap;
import maps.TileStatus;

import algorithmsNEW.myPoint;

public class mapStub implements TileBasedMap {

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean blocked(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getCost(int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}


	@Override
	public Vector<myPoint> getAllMoves(myPoint position) {
		myPoint p1 = new myPoint(position.getX() - 1,position.getY());
		myPoint p2 = new myPoint(position.getX() + 1,position.getY());
		Vector<myPoint> res = new Vector<myPoint>();
		res.add(p1);
		res.add(p2);
		
		return res;
	}
	@Override
	public void setTile(int x, int y, TileStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTile(int x, int y, TileStatus status, float cost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double calcDistance(myPoint from, myPoint to) {
		// TODO Auto-generated method stub
		return 1;
	}


}

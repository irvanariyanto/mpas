package maps;

import java.util.Vector;


import algorithms.myPoint;

/**
 * an implementation of the tiled based map interface
 * @author amit
 *
 */
public class TiledMapImpl implements TileBasedMap {
	
	private int _width;
	private int _height;
	private Tile[][] _tiles;
	private boolean _diagonal;
	
	public TiledMapImpl(int width,int height,boolean diagonal){
		this._width =  width;
		this._height = height;
		this._tiles = new Tile[_width][_height];
		for (int i = 0;i < this._tiles.length;i++){
			for (int j = 0; j < this._tiles[i].length;j++){
				this._tiles[i][j] = new Tile();
			}
		}
		this._diagonal = diagonal;
	}
	@Override
	public int getWidthInTiles() {
		return this._width;
	}

	@Override
	public int getHeightInTiles() {
		return this._height;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean blocked(int x, int y) {	
		return !(this._tiles[x][y].get_status() == TileStatus.free);
	}

	@Override
	public float getCost(int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Vector<myPoint> getAllMoves(myPoint p) {
		Vector<myPoint> res = new Vector<myPoint>();
		for (int i = p.getX() - 1; i <= p.getX() + 1;i++){
			for (int j = p.getY()- 1; j <= p.getY() + 1;j++){
				if (i == p.getX() || j == p.getY() || this._diagonal == true){
					if (inMap(i,j) && !blocked(i,j)){
						myPoint tPoint = new myPoint(i,j);
						//for debugging canceled the stay in one place
				//		if (!(i == p.getX() && j == p.getY()))
							res.add(tPoint);
						
					}
				}
			}
		}
		return res;
	}
	private boolean inMap(int i, int j) {
		return  i >= 0 && i <this._width && j >=0 && j < this._height;
	}
	public void setTile(int x,int y,TileStatus status,float cost){
		Tile tTile = this._tiles[x][y];
		tTile.set_status(status);
		tTile.set_cost(cost);
	}
	public void setTile(int x, int y,TileStatus status){
		Tile tTile = this._tiles[x][y];
		tTile.set_status(status);
	}
	@Override
	public double calcDistance(myPoint from, myPoint to) {
		int diff = Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());
		if (diff > 1)
			return Math.sqrt(2);
		else
			return 1;
	}
	@Override
	public String toString(){
		String res ="";
		for (int i = 0; i < this._height; i++){
			for (int j = 0 ; j < this._width ; j++){
				res+= "x:" + i + " y:" + j + " " + this.blocked(i, j) + "\t";
			}
			res += "\n";
		}
		return res;
	}
	@Override
	public Tile[][] getCells() {
		return this._tiles;
	}
	@Override
	public Tile getCell(int row, int column) {
		return this._tiles[row][column];
	}
}

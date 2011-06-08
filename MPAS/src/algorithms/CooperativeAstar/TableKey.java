package algorithms.CooperativeAstar;

import algorithms.myPoint;

public class TableKey implements TableKeyInterface<myPoint> {
	private int _x;
	private int _y;
	private int _t;
	
	public TableKey(int x, int y, int t){
		this._x = x;
		this._y = y;
		this._t = t;
	}
	@Override
	public int hashCode(){
		return this._x + this._y + this._t;
	}
	@Override
	public boolean equals(Object o){
		if (! (o instanceof TableKey)){
			return false;
		}
		else{
			TableKey other = (TableKey)o;
			return (this._x == other.getX() && this._y == other.getY() && this._t == other.getT());
		}
	}
	public int getY() {
		return this._y;
	}
	public int getT() {
		return this._t;
	}
	public int getX() {
		return this._x;
	}
	@Override
	public myPoint getCoordinates() {
		return new myPoint(_x,_y);
	}
	
}

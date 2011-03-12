package algorithms.CooperativeAstar;

public class TableKey {
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
	private int getY() {
		return this._y;
	}
	private int getT() {
		return this._t;
	}
	private int getX() {
		return this._x;
	}
	
}

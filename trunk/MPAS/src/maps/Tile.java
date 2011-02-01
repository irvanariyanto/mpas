package maps;

/**
 * represents a single tile in the tiled map
 * @author amit
 *
 */


public class Tile {
	private float _cost;
	private TileStatus _status;
	
	public Tile(float cost, TileStatus status){
		this.set_cost(cost);
		this.set_status(status);
	}
	public Tile(){
		this.set_cost(0);
		this.set_status(TileStatus.free);
	}
	public void set_status(TileStatus _status) {
		this._status = _status;
	}
	public TileStatus get_status() {
		return _status;
	}
	public void set_cost(float _cost) {
		this._cost = _cost;
	}
	public float get_cost() {
		return _cost;
	}
}

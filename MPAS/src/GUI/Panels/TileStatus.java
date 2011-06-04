package GUI.Panels;

import Defaults.Enums.Direction;
import Defaults.Enums.Status;

public class TileStatus {
	
	private int _agnetNum;
	private Status _status;
	private Direction _direction;
	
	/**
	 * Constructor
	 * @param i
	 * @param empty
	 */
	public TileStatus(int agnetNum, Status status,Direction dircection) {
		_agnetNum = agnetNum;
		_status = status;
		_direction = dircection;
	}
	
	/**
	 * Constructor
	 * @param agnetNum
	 * @param status
	 */
	public TileStatus(int agnetNum, Status status) {
		_agnetNum = agnetNum;
		_status = status;
		_direction = null;
	}
	
	/**
	 * Constructor
	 * @param status
	 */
	public TileStatus(Status status) {
		_agnetNum = 0;
		_status = status;
		_direction = null;
	}
	
	/**
	 * @param _agnetNum the _agnetNum to set
	 */
	public void setAgnetNum(int _agnetNum) {
		this._agnetNum = _agnetNum;
	}
	
	/**
	 * @return the _agnetNum
	 */
	public int getAgnetNum() {
		return _agnetNum;
	}
	
	/**
	 * @param _status the _status to set
	 */
	public void setStatus(Status _status) {
		this._status = _status;
	}
	
	/**
	 * @return the _status
	 */
	public Status getStatus() {
		return _status;
	}

	/**
	 * @param _directions the _directions to set
	 */
	void setDirection(Direction _directions) {
		this._direction = _directions;
	}

	/**
	 * @return the _directions
	 */
	Direction getDirection() {
		return _direction;
	}

}

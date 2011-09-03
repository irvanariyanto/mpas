package algorithms.AstarID;


public class Agent<E> {
	
	private int _id;
	private E _start;
	private E _goal;
	private int _groupID;
	private int _PlaceInVector;
	
	public Agent(int id,E start,E goal,int groupID,int placeInVector){
		this._id = id;
		this._start = start;
		this._goal = goal;
		this._groupID = groupID;
		this._PlaceInVector = placeInVector;
	}

	public int getPlaceInVector() {
		return _PlaceInVector;
	}
	public void setPlaceInVector(int _PlaceInVector) {
		this._PlaceInVector = _PlaceInVector;
	}
	public int getgroupID() {
		return _groupID;
	}
	public void setgroupID(int _groupID) {
		this._groupID = _groupID;
	}
	public E getGoal() {
		return _goal;
	}
	public void setGoal(E _goal) {
		this._goal = _goal;
	}
	public E getStart() {
		return _start;
	}
	public void setStart(E _start) {
		this._start = _start;
	}
	public int getId() {
		return _id;
	}
	public void setId(int _id) {
		this._id = _id;
	}

}

package algorithms;
/**
 * container class for the agents start,end and current position
 * @author amit
 *
 */
public class Agent {
	
	private myPoint _start;
	private myPoint _end;
	private myPoint _current;
	
	public void set_start(myPoint _start) {
		this._start = _start;
	}
	public myPoint get_start() {
		return _start;
	}
	public void set_end(myPoint _end) {
		this._end = _end;
	}
	public myPoint get_end() {
		return _end;
	}
	public void set_current(myPoint _current) {
		this._current = _current;
	}
	public myPoint get_current() {
		return _current;
	}
}

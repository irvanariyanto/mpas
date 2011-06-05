package GUI.Utils;

import org.pushingpixels.trident.Timeline;

public class TimeLinePool {
	private final static int MAX_GRID_WIDTH = 100;
	private final static int MAX_GRID_HEIGHT = 100;
	private static final TimeLinePool INSTANCE = new TimeLinePool();
	private Timeline[][] _pool;
	
	private TimeLinePool(){
		_pool = new Timeline[MAX_GRID_HEIGHT][MAX_GRID_WIDTH];
		for (int i = 0 ; i <MAX_GRID_HEIGHT ; i++){
			for (int j = 0; j < MAX_GRID_WIDTH ;j++){
				this._pool[i][j] = new Timeline();
			}
		}
	}
	
	public static TimeLinePool getInstance() {
		return INSTANCE;
	}
	public Timeline getTimeLine(int row,int coloumn){
		return this._pool[row][coloumn];
	}
	public Timeline[][] getPool(){
		return this._pool;
	}
}

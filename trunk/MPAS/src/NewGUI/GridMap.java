package NewGUI;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;

import GUI.Cell;
import algorithms.myPoint;

public class GridMap extends JPanel {
	
	public final static int LENGHT = 20;
	public final static int NUM_OF_AGENT = 2;
	// fields
	private int _width = LENGHT;
	private int _height = LENGHT;
	private NewCell _grid[][] = new NewCell[get_width()][get_height()];
	private myPoint[] _starts = new myPoint[NUM_OF_AGENT];
	private myPoint[] _ends = new myPoint[NUM_OF_AGENT];
	private Vector<myPoint> _blockList;
	private Vector<myPoint> _closedList;
	private Vector<myPoint> _openList;
	
	
	public GridMap(mainF mainF, int size) {
		super();
		setLayout(new GridLayout(get_width(), get_height()));
		for (int i = 0; i < get_height(); i++) {
			for (int j = 0; j < get_width(); j++) {
				this._grid[i][j] = new NewCell(new myPoint(i, j));
				add(this._grid[i][j]);
			}
		}
		this._blockList = new Vector<myPoint>();
		this._closedList = new Vector<myPoint>();
		this._openList = new Vector<myPoint>();
		
	}

	public int get_width() {
		return _width;
	}
	
	public int get_height() {
		return _height;
	}

	public void addToStarts(myPoint p, int agent) {
		// TODO Auto-generated method stub
		
	}

	public void addToEnds(myPoint p, int agent) {
		// TODO Auto-generated method stub
		
	}

	public void addToBlock(myPoint p) {
		// TODO Auto-generated method stub
		
	}
	

}

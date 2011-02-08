package NewGUI.Panels;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;

import GUI.Cell;
import algorithms.myPoint;

public class GridPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	// // Variables declaration 
	private int _width;
	private int _height;
	private NewCell _grid[][];
	// End of variables declaration
	
	
	public GridPanel( int size) {		
		super();
		_width= size;
		_height = size;
		_grid = new NewCell[get_width()][get_height()];
		setLayout(new GridLayout(get_width(), get_height()));
		for (int i = 0; i < get_height(); i++) {
			for (int j = 0; j < get_width(); j++) {
				this._grid[i][j] = new NewCell(new myPoint(i, j));
				add(this._grid[i][j]);
			}
		}
	}

	public int get_width() {
		return _width;
	}
	
	public int get_height() {
		return _height;
	}

	
	

}

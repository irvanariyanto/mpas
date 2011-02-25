package NewGUI.Panels;

import java.awt.Event;
import java.awt.GridLayout;


import javax.swing.JPanel;


import algorithms.myPoint;

public class GridPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	// // Variables declaration 
	private int _width;
	private int _height;
	private NewCell _grid[][];
	// End of variables declaration
	
	/**
	 * Constructor
	 */
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

	/**
	 * getter
	 * @return grid's width
	 */
	public int get_width() {
		return _width;
	}
	
	/**
	 * getter
	 * @return grid's height
	 */
	public int get_height() {
		return _height;
	}
	
	/**
	 * set the cell in x,y to be starting point
	 * 
	 * @param x
	 * @param y
	 */
	public void setStartCell(int x, int y,int agentNumber) {
		this._grid[x][y].set_status(NewGUI.Panels.NewCell.Status.Start,agentNumber);
		repaint();
	}

	public void setEndCell(int x, int y, int agentNumber) {
		this._grid[x][y].set_status(NewGUI.Panels.NewCell.Status.Finish,agentNumber);
		repaint();
	}
}

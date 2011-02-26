package NewGUI.Panels;

import java.awt.Event;
import java.awt.GridLayout;


import javax.swing.JPanel;



import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;

import algorithms.myPoint;

public class GridPanel extends JPanel implements ApplicationEventSource{
	

	private static final long serialVersionUID = 1L;
	// // Variables declaration 
	private int _width;
	private int _height;
	private NewCell _grid[][];
	private ApplicationEventListenerCollection _listeners;
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
				this._grid[i][j].addListener(new GridListener());
				add(this._grid[i][j]);
			}
		}
		this._listeners = new ApplicationEventListenerCollection();
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
	
	public void set_editMode (int editMode){
		NewCell._editMode = editMode;		
	}
	
	public void setAgentNumber(int selected) {
		NewCell._agentSelected = selected;
		
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

	public void setFinishCell(int x, int y, int agentNumber) {
		this._grid[x][y].set_status(NewGUI.Panels.NewCell.Status.Finish,agentNumber);
		repaint();
	}
	
	public void setBlockCell(int x, int y) {
		this._grid[x][y].set_status(NewGUI.Panels.NewCell.Status.Blocked);
		repaint();
	}

	@Override
	public void addListener(ApplicationEventListener listener) {
		this._listeners.add(listener);
		
	}

	@Override
	public void clearListeners() {
		this._listeners.clear();
		
	}

	@Override
	public void removeListener(ApplicationEventListener listener) {
		this._listeners.remove(listener);
		
	}
	
	protected class GridListener implements ApplicationEventListener {
		@Override
		public void handle(ApplicationEvent event) {
			GridPanel.this._listeners.fireEvent(event);
			
		}

	}

	

}

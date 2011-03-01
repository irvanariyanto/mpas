package NewGUI.Panels;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;



import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;

import algorithms.myPoint;

public class GridPanel extends JPanel implements ApplicationEventSource {
	

	private static final long serialVersionUID = 1L;
	public static int NUM_OF_AGENT = 2;
	// // Variables declaration 
	private int _width;
	private int _height;
	private NewCell _grid[][];
	private ApplicationEventListenerCollection _listeners;
	private myPoint[] _starts = new myPoint[NUM_OF_AGENT];
	private myPoint[] _finishes = new myPoint[NUM_OF_AGENT];
	private Vector<myPoint> _startsList;
	private Vector<myPoint> _FinishList;
	private Vector<myPoint> _blockList;
	// End of variables declaration
	
	/**
	 * Constructor
	 */
	public GridPanel( int size) {		
		super();
		_width= size;
		_height = size;
		_grid = new NewCell[get_width()][get_height()];
		_startsList = new Vector<myPoint>();
		_FinishList = new Vector<myPoint>();
		_blockList = new Vector<myPoint>();
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
	
	public Vector<myPoint> get_startsList(){
        for (int i=0; i< this._starts.length; i++){
        	if (this._starts[i] != null){
        		this._startsList.add(_starts[i]);
        	}
        }
		return this._startsList;
	}
	
	public Vector<myPoint> get_FinishList(){
		for (int i=0; i< this._finishes.length; i++){
        	if (this._finishes[i] != null){
        		this._FinishList.add(_finishes[i]);
        	}
        }
		return this._FinishList;
	}
	
	public Vector<myPoint> get_blockList(){
		return this._blockList;
	}
	
	public void set_editMode (int editMode){
		NewCell._editMode = editMode;		
	}
	
	public void setAgentNumber(int selected) {
		NewCell._agentSelected = selected;
		
	}
	
	public void setNUM_OF_AGENT (int numberOfAgents){
		GridPanel.NUM_OF_AGENT = numberOfAgents;		
	}
	
	/**
	 * set the cell in x,y to be starting point
	 * 
	 * @param x
	 * @param y
	 */
	public void setStartCell(myPoint p,int agentNumber) {
		if (this._starts[agentNumber-1] == null) {
			this._starts[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Start,agentNumber);
		} else {
			this._grid[this._starts[agentNumber-1].getX()][this._starts[agentNumber-1].getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);
			this._starts[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Start,agentNumber);
		}
		repaint();
	}

	

	public void setFinishCell(myPoint p, int agentNumber) {
		if (this._finishes[agentNumber-1] == null) {
			this._finishes[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Finish,agentNumber);
		} else {
			this._grid[this._finishes[agentNumber-1].getX()][this._finishes[agentNumber-1].getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);
			this._finishes[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Finish,agentNumber);
		}
		repaint();
	}
	
	public void setBlockCell(myPoint p) {
		if (this.get_blockList().contains(p)) {
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);			
			this.get_blockList().remove(p);
		} else {
			this.get_blockList().add(p);
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Blocked);			
		}
		repaint();		
	}
	
	public boolean checkArguments() {
		boolean ans = true;
		for (int i = 0; i < NUM_OF_AGENT; i++) {
			if (this._starts[i] == null | this._finishes[i] == null) {
				ans = false;
				break;
			}
		}
		return ans;
	}

	public void clearPositions() {
		Vector<myPoint> startList = this.get_startsList();
		Vector<myPoint> finishList = this.get_FinishList();
		for(myPoint p: startList){
			this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);
		}
		this._startsList.removeAllElements();
		for(myPoint p: finishList){
			 this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);
		}
		this._startsList.removeAllElements();
		repaint();
	}

	public void clearBlocks() {
		Vector<myPoint> Block = this.get_blockList();
		for(myPoint p: Block){
			if(p==null) continue;
			else this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);
		}
		this._blockList.removeAllElements();		
		repaint();
	}
	
	public void GeneratePositions() {
		clearPositions();
		if ((_height*_width - _blockList.size())/2 >  NUM_OF_AGENT){
			//generate starting points
			for(int i=1; i<=NUM_OF_AGENT; i++){
				int randomX =(int) ((Math.random()*_width));  
				int randomY =(int) ((Math.random()*_height));
				myPoint p = new myPoint(randomX, randomY); 
				if(this.get_blockList().contains(p)){
					i--;
					continue;
				}
				if(this.get_startsList().contains(p)){
					i--;
					continue;
				}
				if(this.get_FinishList().contains(p)){
					i--;
					continue;
				}
				else{
					setStartCell(p,i );
				}
			}
			//generate finishing points
			for(int i=1; i<=NUM_OF_AGENT; i++){
				int randomX =(int) ((Math.random()*_width));  
				int randomY =(int) ((Math.random()*_height));
				myPoint p = new myPoint(randomX, randomY); 
				if(this.get_blockList().contains(p)){
					i--;
					continue;
				}
				if(this.get_startsList().contains(p)){
					i--;
					continue;
				}
				if(this.get_FinishList().contains(p)){
					i--;
					continue;
				}
				else{
					setFinishCell(p,i );
				}
			}
			repaint();
		}
		else{
			//TO-DO add error message
		}
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


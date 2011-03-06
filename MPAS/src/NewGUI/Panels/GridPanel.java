package NewGUI.Panels;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.JPanel;


import maps.Scenario;
import maps.TileBasedMap;
import maps.TileStatus;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import NewGUI.Panels.NewCell.Status;

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
	private Vector<Vector<myPoint>> _finalPaths;
	// End of variables declaration
	
	public void setStarts(Vector<myPoint> starts){
		this._startsList = starts;
	}
	public void setFinishes(Vector<myPoint> finishes){
		this._FinishList = finishes;
	}
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
        		if(!this._startsList.contains(_starts[i])){
        			this._startsList.add(_starts[i]);
        		}      		
        	}
        }
		return this._startsList;
	}
	
	public Vector<myPoint> get_FinishList(){
		for (int i=0; i< this._finishes.length; i++){
        	if (this._finishes[i] != null){
        		if(!this._FinishList.contains(_finishes[i])){
        		this._FinishList.add(_finishes[i]);
        		}
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
		_starts = new myPoint[NUM_OF_AGENT];
		_finishes = new myPoint[NUM_OF_AGENT];
	}
	//amit's addon
	public void  drawMap(TileBasedMap map){
		for (int i = 0; i < map.getWidthInTiles();i++){
			for (int j = 0; j  < map.getHeightInTiles();j++){
				if (map.blocked(i, j)){
					setBlockedCell(i, j);
				}
			}
		}
	}
	//TODO maybe needs more stuff
	public void drawScenario(Scenario s){
		this._startsList = s.getStartLocations();
		this._FinishList = s.getGoalLocations();
		drawMap(s.getMap());
		int numOfAgents = s.getStartLocations().size();
		setNUM_OF_AGENT(numOfAgents);
		for (int i = 0; i < numOfAgents ; i++){
			setStartCell(s.getStartLocations().elementAt(i), i + 1);
			setFinishCell(s.getGoalLocations().elementAt(i),i + 1);
		}
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
	//amit
	public void setBlockedCell(int row,int column){
		setBlockCell(new myPoint(row,column));
	
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
		_starts = new myPoint[NUM_OF_AGENT];
		_finishes = new myPoint[NUM_OF_AGENT];
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
	
	public void clearAll() {
		clearBlocks();
		clearPositions();
		clearFinalPath();
	}
	
	public void clearFinalPath() {
		for(Vector<myPoint> state: _finalPaths){
			for(myPoint p: state){
				this._grid[p.getX()][p.getY()].set_status(NewGUI.Panels.NewCell.Status.Empty);
			}
		}
		this._finalPaths.removeAllElements();		
		repaint();		
	}
	
	public void LoadPositions(){
	for (int i = 0; i < NUM_OF_AGENT ; i++){
			setStartCell(this.get_startsList().elementAt(i), i + 1);
			setFinishCell(this.get_FinishList().elementAt(i),i + 1);
		}
	}
	
	public void createRandomBlocks(int percent, TileBasedMap map) {
		clearBlocks();
		int totalCells = this._height * this._width;
		int numOfBlocks = (totalCells * percent) /100;
		int counter = 0;
		while (counter < numOfBlocks){
			int randomX =(int) ((Math.random()*_width));  
			int randomY =(int) ((Math.random()*_height));
			myPoint p = new myPoint(randomX, randomY); 
			if(this.get_blockList().contains(p)){
				continue;
			}
			else{
				setBlockCell(p);
				map.setTile(p.getX(), p.getY(), TileStatus.blocked);
				counter++;
			}
		}
		
		
	}
	
	public void GeneratePositions() {
		clearPositions();
		if ((_height*_width - _blockList.size())/2 >  NUM_OF_AGENT){
			//generate starting points
			int agentConter = 1;
			while(agentConter<=NUM_OF_AGENT){
				int randomX =(int) ((Math.random()*_width));  
				int randomY =(int) ((Math.random()*_height));
				myPoint p = new myPoint(randomX, randomY); 
				if(this.get_blockList().contains(p)){
					continue;
				}
				if(this.get_startsList().contains(p)){
					continue;
				}
				if(this.get_FinishList().contains(p)){
					continue;
				}
				else{
					setStartCell(p,agentConter );
					agentConter++;
				}
			}
			//generate finishing points
			agentConter = 1;
			while(agentConter<=NUM_OF_AGENT){
				int randomX =(int) ((Math.random()*_width));  
				int randomY =(int) ((Math.random()*_height));
				myPoint p = new myPoint(randomX, randomY); 
				if(this.get_blockList().contains(p)){
					continue;
				}
				if(this.get_startsList().contains(p)){
					continue;
				}
				if(this.get_FinishList().contains(p)){
					continue;
				}
				else{
					setFinishCell(p,agentConter );
					agentConter++;
				}
			}
			this.get_startsList();
			this.get_FinishList();
			repaint();
		}
		else{
			//TO-DO add error message
		}
	}
	

	public void drawFinalPathCell(int x, int y) {
		this._grid[x][y].set_status(Status.Path);
		repaint();
	}

	public void drawFinalPathCell(int x, int y, int agent) {
		this._grid[x][y].set_status(Status.Path);
		repaint();
	}
	
	
	public void drawFinalPaths(Vector<Vector<myPoint>> finalPath) {
		_finalPaths = finalPath;
		for (int i = 0; i < finalPath.size(); i++) {
			Vector<myPoint> tStep = finalPath.elementAt(i);
			for (int j=0; j< tStep.size(); j++){
				myPoint p = tStep.elementAt(j); 
				drawFinalPathCell(p.getX(), p.getY(),j+1);
			
			}
		}
	}
	
	public void drawOneFinalStep(Vector<myPoint> tStep) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			drawFinalPathCell(p.getX(), p.getY(),j+1);	
		}
	}
	public void drawOneStep(Vector<myPoint> tStep) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			drawOpenListCell(p.getX(), p.getY());	
		}
	}

	
	public void setEmptyStep(Vector<myPoint> tStep) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			this._grid[p.getX()][p.getY()].set_status(Status.Empty);	
		}
		
	}
	
	public void drawOpenListCell(int x, int y) {
		this._grid[x][y].set_status(Status.inOpenList);
		repaint();
	}

	public void drawOpenList(Vector<myPoint> points) {
		for (myPoint p : points) {
			this.drawOpenListCell(p.getX(), p.getY());
		}
	}
	
	public void drawClosedListCell(int x, int y) {
		this._grid[x][y].set_status(Status.inClosedList);
		repaint();
	}

	public void drawClosedList(Vector<myPoint> points) {
		for (myPoint p : points) {
			this.drawClosedListCell(p.getX(), p.getY());
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


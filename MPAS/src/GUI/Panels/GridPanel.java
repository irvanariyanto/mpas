package GUI.Panels;



import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JPanel;

import org.pushingpixels.trident.Timeline.RepeatBehavior;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;


import maps.Scenario;
import maps.TileBasedMap;
import maps.TileStatus;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import GUI.Panels.Cell.Direction;
import GUI.Panels.Cell.Status;
import GUI.Utils.ColorManager;

import algorithms.myPoint;

public class GridPanel extends JPanel implements ApplicationEventSource {
	

	private static final long serialVersionUID = 1L;
	public static int NUM_OF_AGENT = 2;
	// // Variables declaration 
	private int _width;
	private int _height;
	private Cell _grid[][];
	private ApplicationEventListenerCollection _listeners;
	private myPoint[] _starts = new myPoint[NUM_OF_AGENT];
	private myPoint[] _finishes = new myPoint[NUM_OF_AGENT];
	private Vector<myPoint> _startsList;
	private Vector<myPoint> _FinishList;
	private Vector<myPoint> _blockList;
	private Vector<Vector<myPoint>> _finalPaths;
	// End of variables declaration
	
	//animation addition
	SwingRepaintTimeline _repaintTimeline;
	
	public void setStarts(Vector<myPoint> starts){
		this._startsList = starts;
	}
	public void setFinishes(Vector<myPoint> finishes){
		this._FinishList = finishes;
	}
	/**
	 * Constructor
	 * @param controller 
	 */
	public GridPanel( int size) {		
		super();
		_repaintTimeline = new SwingRepaintTimeline(this);
		_repaintTimeline.setAutoRepaintMode(false);

		_width= size;
		_height = size;
		_grid = new Cell[get_width()][get_height()];
		_startsList = new Vector<myPoint>();
		_FinishList = new Vector<myPoint>();
		_blockList = new Vector<myPoint>();
		setLayout(new GridLayout(get_width(), get_height()));
		for (int i = 0; i < get_height(); i++) {
			for (int j = 0; j < get_width(); j++) {
				this._grid[i][j] = new Cell(new myPoint(i, j),_repaintTimeline); //TODO initialize the repaintTimeLine
				this._grid[i][j].addListener(new GridListener());
				add(this._grid[i][j]);
			}
		}
		_repaintTimeline.playLoop(RepeatBehavior.LOOP);
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
		Cell._editMode = editMode;		
	}
	
	public void setAgentNumber(int selected) {
		Cell._agentSelected = selected;
		
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
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Start,agentNumber);
		} else {
			this._grid[this._starts[agentNumber-1].getX()][this._starts[agentNumber-1].getY()].set_status(GUI.Panels.Cell.Status.Empty);
			this._starts[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Start,agentNumber);
		}
		repaint();
	}

	

	public void setFinishCell(myPoint p, int agentNumber) {
		if (this._finishes[agentNumber-1] == null) {
			this._finishes[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Finish,agentNumber);
		} else {
			this._grid[this._finishes[agentNumber-1].getX()][this._finishes[agentNumber-1].getY()].set_status(GUI.Panels.Cell.Status.Empty);
			this._finishes[agentNumber-1] = p;
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Finish,agentNumber);
		}
		repaint();
	}
	
	public void setBlockCell(myPoint p) {
		if (this.get_blockList().contains(p)) {
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Empty);			
			this.get_blockList().remove(p);
		} else {
			this.get_blockList().add(p);
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Blocked);			
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
			this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Empty);
		}
		this._startsList.removeAllElements();
		for(myPoint p: finishList){
			 this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Empty);
		}
		this._FinishList.removeAllElements();
		_starts = new myPoint[NUM_OF_AGENT];
		_finishes = new myPoint[NUM_OF_AGENT];
		repaint();
	}

	public void clearBlocks( TileBasedMap map) {
		if(this.get_blockList() != null){
			Vector<myPoint> Block = this.get_blockList();
			for(myPoint p: Block){
				if(p==null) continue;
				else this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Empty);
				map.setTile(p.getX(), p.getY(), TileStatus.free);
			}
			this._blockList.removeAllElements();
			}		
		repaint();
	}
	
	public void clearAll(TileBasedMap map) {
		clearBlocks(map);
		clearPositions();
		clearFinalPath();
		clearOpenList();
	}
	
	public void clearFinalPath() {
		if (_finalPaths != null){
			for(Vector<myPoint> state: _finalPaths){
				for(myPoint p: state){
					if (this._grid[p.getX()][p.getY()].get_status() != Status.Start &&
						this._grid[p.getX()][p.getY()].get_status() != Status.Finish){
						this._grid[p.getX()][p.getY()].set_status(GUI.Panels.Cell.Status.Empty);
					}
					else{
						this._grid[p.getX()][p.getY()].clearDirections();
					}
												
				}
			}
			this._finalPaths.removeAllElements();	
		}
		repaint();		
	}
	
	public void clearOpenList() {
		for(int i=0; i<this._height; i++){
			for(int j=0; j<this._width; j++){
				if(this._grid[i][j].get_status()==Status.inOpenList){
					this._grid[i][j].set_status(Status.Empty);
				}
			}
		}
		
	}
	
	public void LoadPositions(){
	for (int i = 0; i < NUM_OF_AGENT ; i++){
			setStartCell(this.get_startsList().elementAt(i), i + 1);
			setFinishCell(this.get_FinishList().elementAt(i),i + 1);
		}
	}
	
	public void createRandomBlocks(int percent, TileBasedMap map) {
		clearBlocks(map);
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
			if(this.get_startsList().contains(p)){
				continue;
			}
			if(this.get_FinishList().contains(p)){
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
		if ((_height*_width - _blockList.size())/2 >=  NUM_OF_AGENT){
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
		if(this._grid[x][y].get_status() != Status.Start && this._grid[x][y].get_status() != Status.Finish ){
			this._grid[x][y].set_status(Status.Path);
		}
		repaint();
	}

	public void drawFinalPathCell(int x, int y, int agent) {
		if(this._grid[x][y].get_status() != Status.Start && this._grid[x][y].get_status() != Status.Finish ){
			this._grid[x][y].set_status(Status.Path);
		}
		this._grid[x][y].set_agent(agent);
		repaint();
	}
	
	public void drawFinalPathCell(int x, int y, int agent, Direction direction) {
		if(this._grid[x][y].get_status() != Status.Start && this._grid[x][y].get_status() != Status.Finish ){
			this._grid[x][y].set_status(Status.Path);
		}
		this._grid[x][y].set_agent(agent);
		this._grid[x][y].add_Direction(direction);
		repaint();
	}
	
	public void drawFinalPaths(Vector<Vector<myPoint>> finalPath, boolean withLines) {
		 _finalPaths = finalPath;
		 if (withLines){
			 drawFinalPathsWithLines(_finalPaths);			
		}
		else{
			drawFinalPaths(_finalPaths);
		}
		 repaint();
	}
	
	public void drawFinalPaths(Vector<Vector<myPoint>> finalPath) {
        for (int i = 0; i < finalPath.size(); i++) {
                Vector<myPoint> tPath = finalPath.elementAt(i);
                for (int j=0; j< tPath.size(); j++){
                        myPoint p = tPath.elementAt(j); 
                        drawFinalPathCell(p.getX(), p.getY(),i+1);               
                }
        }
        repaint();
	}
	
	
	public void drawOneFinalStep(Vector<myPoint> tStep, boolean withLines) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			if (withLines){
				drawFinalPathCell(p.getX(), p.getY(),j+1,Direction.DOWN_LEFT);
			}
			else{
				drawFinalPathCell(p.getX(), p.getY(),j+1);
			}
		}
	}
	
	public void drawFinalPathsWithLines(Vector<Vector<myPoint>> finalPath){
		for (int i = 0; i < finalPath.size(); i++) {
			Vector<myPoint> tPath = finalPath.elementAt(i);
			Collections.reverse(tPath);
			myPoint prePoint,currentPoint,nextPoint;
            for (int j=0; j < tPath.size(); j++){  
            	if(j==0){
            		prePoint = tPath.elementAt(0);  
            	}
            	else{
            		prePoint = tPath.elementAt(j-1);	
            	}            	  
            	currentPoint = tPath.elementAt(j);
            	if(j==tPath.size()-1)
            	nextPoint = tPath.elementAt(j); 
            	else{
            		nextPoint = tPath.elementAt(j+1);
            	}
            	Direction dir = calcDirection(prePoint,currentPoint,nextPoint);
                drawFinalPathCell(currentPoint.getX(), currentPoint.getY(),i+1,dir);               
            }
		}		
	}
	
	
	public void drawOneFinalStepWithLines(Vector<myPoint> tStep, boolean withLines) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			if (withLines){
				drawFinalPathCell(p.getX(), p.getY(),j+1,Direction.DOWN_LEFT);
			}
			else{
				drawFinalPathCell(p.getX(), p.getY(),j+1,Direction.DOWN_LEFT);
			}
		}
	}
	
	private Direction calcDirection(myPoint p, myPoint q, myPoint m) {
		Direction res = null;
		float preDeltaX = q.getX()-p.getX();
		float preDeltaY = q.getY()-p.getY();
		float postDeltaX = q.getX()-m.getX();
		float postDeltaY = q.getY()-m.getY();
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.LEFT_RIGHT;
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.RIGHT_LEFT;
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.DOWN_TOP;
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.TOP_DOWN;
		if(preDeltaX==1 && preDeltaY==1 && postDeltaX==-1 && postDeltaY==-1 )
			res= Direction.TOPLEFT_DOWNRIGHT;
		if(preDeltaX==-1 && preDeltaY==-1 && postDeltaX==1 && postDeltaY==1 )
			res= Direction.DOWNRIGHT_TOPLEFT;
		if(preDeltaX==-1 && preDeltaY==1 && postDeltaX==1 && postDeltaY==-1 )
			res= Direction.DOWNLEFT_TOPRIGHT;
		if(preDeltaX==1 && preDeltaY==-1 && postDeltaX==-1 && postDeltaY==1 )
			res= Direction.TOPRIGHT_DOWNLEFT;
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.LEFT_TOP;
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.TOP_LEFT;
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.LEFT_DOWN;
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.DOWN_LEFT;
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.TOP_RIGHT;
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.RIGHT_TOP;
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.RIGHT_DOWN;
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.DOWN_RIGHT;

		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.CENTER_TOP;		
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.TOP_CENTER;		
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.CENTER_DOWN;
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.DOWN_CENTER;		
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.CENTER_RIGHT;
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.RIGHT_CENTER;		
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.CENTER_LEFT;
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.LEFT_CENTER;			
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==1 && postDeltaY==-1 )
			res= Direction.CENTER_TOPRIGHT;
		if(preDeltaX==1 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.TOPRIGHT_CENTER;			
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==1 && postDeltaY==1 )			
			res= Direction.CENTER_TOPLEFT;
		if(preDeltaX==1 && preDeltaY==1 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.TOPLEFT_CENTER;			
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==-1 )
			res= Direction.CENTER_DOWNRIGHT;
		if(preDeltaX==-1 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.DOWNRIGHT_CENTER;			
		if(preDeltaX==0 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==1 )
			res= Direction.CENTER_DOWNLEFT;
		if(preDeltaX==-1 && preDeltaY==1 && postDeltaX==0 && postDeltaY==0 )
			res= Direction.DOWNLEFT_CENTER;
		
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==1 && postDeltaY==1 )
			res= Direction.TOP_TOPLEFT;
		if(preDeltaX==1 && preDeltaY==1 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.TOPLEFT_TOP;	
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==1 )
			res= Direction.TOP_DOWNLEFT;	
		if(preDeltaX==-1 && preDeltaY==1 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.DOWNLEFT_TOP;			
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==-1 )
			res= Direction.TOP_DOWNRIGHT;
		if(preDeltaX==-1 && preDeltaY==-1 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.DOWNRIGHT_TOP;	
		if(preDeltaX==1 && preDeltaY==0 && postDeltaX==1 && postDeltaY==-1 )
			res= Direction.TOP_TOPRIGHT;
		if(preDeltaX==1 && preDeltaY==-1 && postDeltaX==1 && postDeltaY==0 )
			res= Direction.TOPRIGHT_TOP;
		
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==1 )
			res= Direction.DOWN_DOWNLEFT;
		if(preDeltaX==-1 && preDeltaY==1 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.DOWNLEFT_DOWN;		
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==1 && postDeltaY==1 )
			res= Direction.DOWN_TOPLEFT;
		if(preDeltaX==1 && preDeltaY==1 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.TOPLEFT_DOWN;		
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==1 && postDeltaY==-1 )
			res= Direction.DOWN_TOPRIGHT;
		if(preDeltaX==1 && preDeltaY==-1 && postDeltaX==-1 && postDeltaY==0 )
			res= Direction.TOPRIGHT_DOWN;		
		if(preDeltaX==-1 && preDeltaY==0 && postDeltaX==-1 && postDeltaY==-1 )
			res= Direction.DOWN_DOWNRIGHT;
		if(preDeltaX==-1 && preDeltaY==-1 && postDeltaX==-1 && postDeltaY==0)
			res= Direction.DOWNRIGHT_DOWN;
		
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==1 && postDeltaY==1 )
			res= Direction.LEFT_TOPLEFT;
		if(preDeltaX==1 && preDeltaY==1 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.TOPLEFT_LEFT;		
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==1 && postDeltaY==-1 )
			res= Direction.LEFT_TOPRIGHT;	
		if(preDeltaX==1 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.TOPRIGHT_LEFT;			
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==-1 && postDeltaY==-1 )
			res= Direction.LEFT_DOWNRIGHT;	
		if(preDeltaX==-1 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.DOWNRIGHT_LEFT;	
		if(preDeltaX==0 && preDeltaY==1 && postDeltaX==-1 && postDeltaY==1 )
			res= Direction.LEFT_DOWNLEFT;
		if(preDeltaX==-1 && preDeltaY==1 && postDeltaX==0 && postDeltaY==1 )
			res= Direction.DOWNLEFT_LEFT;
		
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==1 && postDeltaY==-1 )
			res= Direction.RIGHT_TOPRIGHT;
		if(preDeltaX==1 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.TOPRIGHT_RIGHT;
		
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==1 && postDeltaY==1 )
			res= Direction.RIGHT_TOPLEFT;
		if(preDeltaX==1 && preDeltaY==1 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.TOPLEFT_RIGHT;
		
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==-1 && postDeltaY==1 )
			res= Direction.RIGHT_DOWNLEFT;
		if(preDeltaX==-1 && preDeltaY==1 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.DOWNLEFT_RIGHT;	
		
		if(preDeltaX==0 && preDeltaY==-1 && postDeltaX==-1 && postDeltaY==-1 )
			res= Direction.RIGHT_DOWNRIGHT;
		if(preDeltaX==-1 && preDeltaY==-1 && postDeltaX==0 && postDeltaY==-1 )
			res= Direction.DOWNRIGHT_RIGHT;
		
		return res;
	}

	
	
	public void drawOneStep(Vector<myPoint> tStep) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			Color tColor = ColorManager.getColor("agent" + (j + 1));
			drawOpenListCell(p.getX(), p.getY(), j+1);	
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
	
	public void drawOpenListCell(int x, int y, int agent) {
		this._grid[x][y].set_status(Status.inOpenList);
		this._grid[x][y].set_agent(agent);
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
	
	public void setWithGridLines(boolean b) {
		for (int i = 0; i < get_height(); i++) {
			for (int j = 0; j < get_width(); j++) {
				this._grid[i][j].setWithGridLine(b);
			}
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

	//animation addon
	public void animateCell(int row,int column){
		this._grid[row][column].doAnimation(true);
		this._grid[row][column].doAnimation(false);
	}
	

	

	

}


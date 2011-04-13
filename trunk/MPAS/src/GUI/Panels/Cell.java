/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Panels;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;

import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.SetBlockCellEvent;
import EventMechanism.Events.SetFinishCellEvent;
import EventMechanism.Events.SetStartCellEvent;
import algorithms.myPoint;

/**
 * 
 * @author Liron Katav
 */
public class Cell extends Component  implements ApplicationEventSource{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SET_BLOCKS=0,SET_START=1,SET_FINISH=2;
	public static enum Status {
		Empty, Start, Finish, Blocked, inOpenList, inClosedList,Path;
	}
	public static enum Direction {
		LEFT_RIGHT, RIGHT_LEFT ,DOWN_TOP,TOP_DOWN,
		TOPLEFT_DOWNRIGHT,DOWNRIGHT_TOPLEFT,DOWNLEFT_TOPRIGHT,TOPRIGHT_DOWNLEFT,
		LEFT_TOP,TOP_LEFT,
		LEFT_DOWN,DOWN_LEFT,
		TOP_RIGHT,RIGHT_TOP,
		RIGHT_DOWN,DOWN_RIGHT,
		CENTER_TOP,CENTER_DOWN,CENTER_LEFT	,CENTER_RIGHT,
		TOP_CENTER,DOWN_CENTER,LEFT_CENTER	,RIGHT_CENTER,
		CENTER_TOPLEFT,CENTER_TOPRIGHT,CENTER_DOWNRIGHT, CENTER_DOWNLEFT,
		TOPLEFT_CENTER,TOPRIGHT_CENTER,DOWNRIGHT_CENTER, DOWNLEFT_CENTER,
		TOP_TOPLEFT,TOP_DOWNLEFT,TOP_DOWNRIGHT,TOP_TOPRIGHT,
		TOPLEFT_TOP,DOWNLEFT_TOP,DOWNRIGHT_TOP,TOPRIGHT_TOP,
		DOWN_DOWNLEFT,DOWN_TOPLEFT,DOWN_TOPRIGHT,DOWN_DOWNRIGHT,
		DOWNLEFT_DOWN,TOPLEFT_DOWN,TOPRIGHT_DOWN,DOWNRIGHT_DOWN,
		LEFT_TOPLEFT,LEFT_TOPRIGHT,LEFT_DOWNRIGHT,LEFT_DOWNLEFT,
		TOPLEFT_LEFT,TOPRIGHT_LEFT,DOWNRIGHT_LEFT,DOWNLEFT_LEFT,
		RIGHT_TOPRIGHT,RIGHT_TOPLEFT,RIGHT_DOWNLEFT,RIGHT_DOWNRIGHT,
		TOPRIGHT_RIGHT,TOPLEFT_RIGHT,DOWNLEFT_RIGHT,DOWNRIGHT_RIGHT;
	}

	// fields
	private myPoint _position;
	private Status _status;
	private Vector<Direction> _directions;
	private int _agnetNum;
	static int _editMode ;
	public static int _agentSelected;
	private ApplicationEventListenerCollection _listeners;
	private boolean _withGridLine = false;
	
	//openlist+closed list animation
	private Timeline _timeline;
	private SwingRepaintTimeline _repaintTimeline;
	public static final int TIMER = 2500;
	public Color backgroundColor;
	private boolean isAnimating;
	
	public void setBackgroundColor(Color color){
		this.backgroundColor = color;
		this._repaintTimeline.forceRepaintOnNextPulse();
	}
	public Color getBackgroundColor(){
		return this.backgroundColor;
	}
	
	// Constructor
	public Cell(myPoint point,SwingRepaintTimeline repaintTimeline) {
		this._position = point;	
		this.set_status(Status.Empty);
		this._agnetNum=0;
		_editMode = SET_BLOCKS;
		this._directions = new Vector<Direction>();	
		// Action and mouse listener support
		this._listeners = new ApplicationEventListenerCollection();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		
		this.backgroundColor = Color.white;
		this._timeline = new Timeline(this);
		this._timeline.setDuration(TIMER);
		this._timeline.addPropertyToInterpolate("backgroundColor",Color.blue,Color.white);
		this._repaintTimeline = repaintTimeline;
		this.isAnimating = false;
	}

	// Constructor
	public Cell(myPoint p, boolean block) {
		this._position = p;	
		this.set_status(Status.Blocked);
		this._agnetNum=0;
		_editMode = SET_BLOCKS;
		this._directions = new Vector<Direction>();
		
		// Action and mouse listener support
		this._listeners = new ApplicationEventListenerCollection();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}
	
	
	//animation addon
	public void doAnimation(boolean isAnimating){
		if (this.isAnimating == isAnimating){
			return;
		}
		this.isAnimating = isAnimating;
		if (this.isAnimating){
			this._timeline.replay();
		}
	}

	/**
	 * @return the _position
	 */
	public myPoint getPosition() {
		return _position;
	}

	/**
	 * @param position the _position to set
	 */
	public void setPosition(myPoint position) {
		this._position = position;
	}

	/**
	 * set the status and the agentNmber
	 * @param status
	 * @param agentNumber
	 */
	public void set_status(Status status, int agentNumber) {
		this._status = status;
		this._agnetNum = agentNumber;
	}

	/**
	 * set the status
	 * @param status
	 */
	public void set_status(Status status) {
		if(status == Status.Empty){
			this._agnetNum=0;
			if(this._directions!=null)
				this._directions.removeAllElements();
		}
		this._status = status;
	}

	/**
	 * @return the _status
	 */
	public Status get_status() {
		return _status;
	}
	
	/**
	 * set the status and the agentNmber
	 * @param status
	 */
	public void add_Direction(Direction dir) {
		this._directions.add(dir);
	}
	/**
	 * set the status and the agentNmber
	 * @param status
	 */
	public void add_Direction(Direction dir, int agentNum) {
		this._directions.add(dir);
		this._agnetNum = agentNum;
	}
	/**
	 * @return the _editMode
	 */
	public int get_editMode() {
		return _editMode;
		
	}
	/**
	 * set the editMode
	 * @param editMode
	 */
	public void set_editMode (int editMode){
		Cell._editMode = editMode;
		
	}
	/**
	 * @return the _agnetNum
	 */
	public int get_agent() {
		return _agnetNum;
		
	}
	/**
	 * set the editMode
	 * @param _agnetNum
	 */
	public void set_agent(int agentNum) {
		this._agnetNum = agentNum;
		
	}
	
	public boolean getWithGridLine(){
		return this._withGridLine;
	}
	
	public void setWithGridLine(boolean bool){
		this._withGridLine = bool;
		repaint();
	}

	 
	
	@Override
	public void paint(Graphics g) {		
		Dimension size = getSize();
		Dimension RectSize = new Dimension(size.width -2,size.height -2);
		Image carImage=Toolkit.getDefaultToolkit().getImage("Icons/car_white.png");
		Image flagImage=Toolkit.getDefaultToolkit().getImage("Icons/flag.png");
		SetColorByStatus(g,this._status);		
		g.fillRect(0, 0, size.width-1, size.height-1);
		g.setColor(Color.black);
		if (this._withGridLine ){
			g.drawRect(0, 0, RectSize.width, RectSize.height );
		}
		if(this._status == Status.Path && !this._directions.isEmpty()){
			drawDircetions(g,this._directions,RectSize);			
		}
		if(this._status == Status.Start && !this._directions.isEmpty()){
			drawDircetions(g,this._directions,RectSize);			
		}
		if(this._status == Status.Finish && !this._directions.isEmpty()){
			drawDircetions(g,this._directions,RectSize);			
		}
		if(this._status == Status.Start ){
			g.drawImage(carImage, 2,2, RectSize.width, RectSize.height, this);			
		}
		if(this._status == Status.Finish ){
			g.drawImage(flagImage, 5,10, RectSize.width, RectSize.height, this);			
		}

		if(this._agnetNum != 0){
			g.setFont(new Font("sansserif", Font.BOLD, 11));
			g.drawString(Integer.toString(this._agnetNum),5,10);
		}
		//amit's additions
		Color backgr = this.getBackgroundColor();

		if (!Color.white.equals(backgr)) {
			g.setColor(backgr);
			g.fillRect(0, 0, size.width-1, size.height-1);
		}
		//amit's edition ends
	}

	private void SetColorByStatus(Graphics g,Status status) {
		if (status == Status.Empty) {
			g.setColor(Color.white);
		}
		if (status == Status.Start) {			
			g.setColor(Color.green);
		}
		if (status == Status.Finish) {
			g.setColor(Color.white);
		}
		if (status == Status.Blocked) {
			g.setColor(Color.black);
		}
		if (status == Status.inOpenList) {
			g.setColor(Color.gray);
		}
		if (status == Status.inClosedList) {
			g.setColor(Color.darkGray);
		}
		if (status == Status.Path ) {
			g.setColor(Color.yellow);			
		}		
	}

	private void drawDircetions(Graphics g, Vector<Direction> directions,Dimension size) {
		for (Direction direction : directions) {
			if (direction==Direction.LEFT_RIGHT || direction==Direction.RIGHT_LEFT ){
				g.drawLine(0, size.height/2, size.width,size.height/2 );
			}
			if (direction==Direction.TOP_DOWN || direction==Direction.DOWN_TOP){
				g.drawLine(size.width/2, 0, size.width/2,size.height);
			}
			if (direction==Direction.TOPLEFT_DOWNRIGHT || direction==Direction.DOWNRIGHT_TOPLEFT){
				g.drawLine(0, 0, size.width,size.height);
			}
			if (direction==Direction.DOWNLEFT_TOPRIGHT || direction==Direction.TOPRIGHT_DOWNLEFT){
				g.drawLine(0, size.height, size.width,0);
			}
			if (direction==Direction.LEFT_TOP || direction==Direction.TOP_LEFT){
				g.drawLine(0, size.height/2, size.width/2,size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width/2,0);
			}
			if (direction==Direction.LEFT_DOWN || direction==Direction.DOWN_LEFT){
				g.drawLine(0, size.height/2, size.width/2,size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width/2,size.height);
			}
			if (direction==Direction.TOP_RIGHT || direction==Direction.RIGHT_TOP){
				g.drawLine(size.width/2, 0, size.width/2,size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,size.height/2);
			}
			if (direction==Direction.RIGHT_DOWN || direction==Direction.DOWN_RIGHT){
				g.drawLine(size.width, size.height/2, size.width/2,size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width/2,size.height);
			}
			if (direction==Direction.CENTER_DOWN || direction==Direction.DOWN_CENTER){
				g.drawLine(size.width/2,size.height/2, size.width/2,size.height);
			}
			if (direction==Direction.CENTER_TOP || direction==Direction.TOP_CENTER){
				g.drawLine(size.width/2,size.height/2, size.width/2,0);
			}
			if (direction==Direction.CENTER_LEFT || direction==Direction.LEFT_CENTER){
				g.drawLine(size.width/2,size.height/2, 0,size.height/2);
			}
			if (direction==Direction.CENTER_RIGHT || direction==Direction.RIGHT_CENTER){
				g.drawLine(size.width/2,size.height/2, size.width,size.height/2);
			}
			if (direction==Direction.CENTER_TOPLEFT || direction==Direction.TOPLEFT_CENTER){
				g.drawLine(size.width/2,size.height/2, 0,0);
			}
			if (direction==Direction.CENTER_TOPRIGHT|| direction==Direction.TOPRIGHT_CENTER){
				g.drawLine(size.width/2,size.height/2, size.width,0);
			}
			if (direction==Direction.CENTER_DOWNLEFT|| direction==Direction.DOWNLEFT_CENTER){
				g.drawLine(size.width/2,size.height/2, 0, size.height);
			}
			if (direction==Direction.CENTER_DOWNRIGHT|| direction==Direction.DOWNRIGHT_CENTER){
				g.drawLine(size.width/2,size.height/2, size.width, size.height);
			}
			
			if (direction==Direction.TOP_TOPLEFT|| direction==Direction.TOPLEFT_TOP){
				g.drawLine(size.width/2,0, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,0);
			}
			if (direction==Direction.TOP_TOPRIGHT|| direction==Direction.TOPRIGHT_TOP){
				g.drawLine(size.width/2,0, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,0);
			}
			if (direction==Direction.TOP_DOWNRIGHT|| direction==Direction.DOWNRIGHT_TOP){
				g.drawLine(size.width/2,0, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,size.height);
			}
			if (direction==Direction.TOP_DOWNLEFT|| direction==Direction.DOWNLEFT_TOP){
				g.drawLine(size.width/2,0, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,size.height);
			}
			
			if (direction==Direction.DOWN_DOWNLEFT|| direction==Direction.DOWNLEFT_DOWN){
				g.drawLine(size.width/2,size.height, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,size.height);
			}
			if (direction==Direction.DOWN_DOWNRIGHT|| direction==Direction.DOWNRIGHT_DOWN){
				g.drawLine(size.width/2,size.height, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,size.height);
			}
			if (direction==Direction.DOWN_TOPLEFT|| direction==Direction.TOPLEFT_DOWN){
				g.drawLine(size.width/2,size.height, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,0);
			}
			if (direction==Direction.DOWN_TOPRIGHT|| direction==Direction.TOPRIGHT_DOWN){
				g.drawLine(size.width/2,size.height, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,0);
			}
			if (direction==Direction.LEFT_TOPLEFT|| direction==Direction.TOPLEFT_LEFT){
				g.drawLine(0,size.height, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,0);
			}
			if (direction==Direction.LEFT_TOPRIGHT|| direction==Direction.TOPRIGHT_LEFT){
				g.drawLine(0,size.height/2, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,0);
			}
			if (direction==Direction.LEFT_DOWNRIGHT|| direction==Direction.DOWNRIGHT_LEFT){
				g.drawLine(0,size.height/2, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,size.height);
			}
			if (direction==Direction.LEFT_DOWNLEFT|| direction==Direction.DOWNLEFT_LEFT){
				g.drawLine(0,size.height, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,size.height);
			}
			if (direction==Direction.RIGHT_TOPRIGHT|| direction==Direction.TOPRIGHT_RIGHT){
				g.drawLine(size.width,size.height/2, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,0);
			}
			if (direction==Direction.RIGHT_TOPLEFT|| direction==Direction.TOPLEFT_RIGHT){
				g.drawLine(size.width,size.height/2, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,0);
			}
			if (direction==Direction.RIGHT_DOWNRIGHT|| direction==Direction.RIGHT_DOWNRIGHT){
				g.drawLine(size.width,size.height/2, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, size.width,size.height);
			}
			if (direction==Direction.RIGHT_DOWNLEFT|| direction==Direction.DOWNLEFT_RIGHT){
				g.drawLine(size.width,size.height/2, size.width/2, size.height/2);
				g.drawLine(size.width/2,size.height/2, 0,size.height);
			}
			
		}
		
	}
	public void clearDirections() {
		this._directions.removeAllElements();
		
	}

	public void processMouseEvent(MouseEvent event) {
		super.processMouseEvent(event);
		if (event.getID() == MouseEvent.MOUSE_PRESSED) {
			myPoint p = getPointFromSource(event);
			if(_editMode ==  SET_BLOCKS){
				this._listeners.fireEvent(new SetBlockCellEvent(this,p));
			}
			if(_editMode ==  SET_START){
				this._listeners.fireEvent(new SetStartCellEvent(this,p,_agentSelected));
			}
			if(_editMode ==  SET_FINISH ){
				this._listeners.fireEvent(new SetFinishCellEvent(this,p,_agentSelected));
			}
		}
	}

	private myPoint getPointFromSource(MouseEvent event) {
		Object obj = event.getSource();
		Cell c = ((Cell) obj);
		return c.getPosition();
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

	

	

	
	
}// end of class Cell


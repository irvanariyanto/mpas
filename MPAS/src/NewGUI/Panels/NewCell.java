/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NewGUI.Panels;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

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
public class NewCell extends Component  implements ApplicationEventSource{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SET_BLOCKS=0,SET_START=1,SET_FINISH=2;
	public static enum Status {
		Empty, Start, Finish, Blocked, inOpenList, inClosedList,Path;
	}

	// fields
	private myPoint _position;
	private Status _status;
	private int _agnetNum;
	static int _editMode ;
	public static int _agentSelected;
	private ApplicationEventListenerCollection _listeners;

	// Constructor
	public NewCell(myPoint point) {
		this._position = point;	
		init();
	}

	// Constructor
	public NewCell(myPoint p, boolean block) {
		this._position = p;	
		this.set_status(Status.Blocked);
		init();	
	}

	/**
	 * initialize the components
	 */
	private void init() {
		this.set_status(Status.Empty);
		this._agnetNum=0;
		_editMode = SET_BLOCKS;
		// Action and mouse listener support
		this._listeners = new ApplicationEventListenerCollection();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
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
		NewCell._editMode = editMode;
		
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

	@Override
	public void paint(Graphics g) {	
		Dimension size = getSize();
		if (this._status == Status.Empty) {
			g.setColor(Color.white);
		}
		if (this._status == Status.Start) {
			g.setColor(Color.green);
		}
		if (this._status == Status.Finish) {
			g.setColor(Color.red);
		}
		if (this._status == Status.Blocked) {
			g.setColor(Color.black);
		}
		if (this._status == Status.inOpenList) {
			g.setColor(Color.gray);
		}
		if (this._status == Status.inClosedList) {
			g.setColor(Color.darkGray);
		}
		if (this._status == Status.Path && this._agnetNum ==1) {
			g.setColor(Color.blue);
		}
		if (this._status == Status.Path && this._agnetNum ==2) {
			g.setColor(Color.cyan);
		}
		g.fillRect(0, 0, size.width-1, size.height-1);	
		g.setColor(Color.black);	
		
		if(this._agnetNum != 0){
			g.setFont(new Font("sansserif", Font.BOLD, 10));
			g.drawString(Integer.toString(this._agnetNum),5,15);
		}
		g.drawRect(0, 0, size.width - 2, size.height - 2);
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
		NewCell c = ((NewCell) obj);
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


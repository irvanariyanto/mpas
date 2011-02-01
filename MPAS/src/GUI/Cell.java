/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import algorithms.myPoint;

/**
 * 
 * @author Liron Katav
 */
public class Cell extends Component {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Status {
		Empty, Start, Finish, Blocked, inOpenList, inClosedList,Path;
	}

	// fields
	private myPoint _position;
	private Status _status;
	private double _distFromStart;
	private double _distFromFinish;
	private double _cost;
	private mainFrame _main;
	private int _agnetNum;

	// Constructor
	public Cell(myPoint point,mainFrame main) {
		this._main=main;
		this._position = point;
		this._agnetNum=0;
		init();
	}

	// Constructor
	public Cell(myPoint p, boolean block,mainFrame main) {
		this._main=main;
		this._position = p;
		this._agnetNum=0;
		init();
		this.set_status(Status.Blocked);
	}

	private void init() {
		this.set_status(Status.Empty);
		this._distFromStart = -1;
		this._distFromFinish = -1;
		this._cost = -1;

		// Action and mouse listener support
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);

		MouseListener mouseOnCell = new MouseOnCell();
		this.addMouseListener(mouseOnCell);

	}

	/**
	 * @return the _position
	 */
	public myPoint getPosition() {
		return _position;
	}

	/**
	 * @param position
	 *            the _position to set
	 */
	public void setPosition(myPoint position) {
		this._position = position;
	}

	/**
	 * @return the _distFromStart
	 */
	public double getDistFromStart() {
		return _distFromStart;
	}

	/**
	 * @param distFromStart
	 *            the _distFromStart to set
	 */
	public void setDistFromStart(double distFromStart) {
		this._distFromStart = distFromStart;
	}

	/**
	 * @return the _distFromFinish
	 */
	public double getDistFromFinish() {
		double ans;
		if (this._status == Status.Start) {
			ans = 0;
		}
		if (this._status == Status.Blocked) {
			ans = -1;
		} else {
			ans = this._distFromStart;
		}
		return ans;
	}

	/**
	 * @param distFromFinish
	 *            the _distFromFinish to set
	 */
	public void setDistFromFinish(double distFromFinish) {
		this._distFromFinish = distFromFinish;
	}

	public void set_status(Status _status) {
		this._status = _status;
	}

	public Status get_status() {
		return _status;
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
		//g.setFont(new Font("sansserif", Font.BOLD, 13));
		//g.drawString(Double.toString(this._cost),5,15);
		g.drawRect(0, 0, size.width - 2, size.height - 2);
	}

	@Override
	public void processMouseEvent(MouseEvent event) {
		super.processMouseEvent(event);
		if (event.getID() == MouseEvent.MOUSE_PRESSED) {
			myPoint p = getPointFromSource(event);
			int agent = getAgentNumber();
			String command = getCommand();
			//System.out.println("the point is " + x + "," + y);
			//System.out.println("command is :" + "'"+command +"'"+ " agent : '" + agent+"'");
			if(command.equals("start") ){
				this._main.get_grid().addToStarts(p, agent);
			}
			if(command.equals("end") ){
				this._main.get_grid().addToEnds(p, agent);
			}
			if(command.equals("block") ){
				this._main.get_grid().addToBlock(p);
			}
		}
	}

	private myPoint getPointFromSource(MouseEvent event) {
		Object obj = event.getSource();
		Cell c = ((Cell) obj);
		return c.getPosition();
	}

	private int getAgentNumber() {
		return this._main.getAgentNumber();
	}
	
	private String getCommand() {
		return this._main.getCommand();
	}
	
	private Cell getCellFromSource(MouseEvent event) {
		Object obj = event.getSource();
		Cell c = ((Cell) obj);
		return c;
	}

	public void set_agent(int agentNum) {
		this._agnetNum = agentNum;
		
	}


}// end of class Cell


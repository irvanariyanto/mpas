/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Vector;

import GUI.Cell.Status;
import algorithms.myPoint;

/**
 * 
 * @author Liron Katav
 */
public class Grid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int LENGHT = 20;
	public final static int NUM_OF_AGENT = 2;
	// fields
	private int _width = LENGHT;
	private int _height = LENGHT;
	private Cell _grid[][] = new Cell[get_width()][get_height()];
	private myPoint[] _starts = new myPoint[NUM_OF_AGENT];
	private myPoint[] _ends = new myPoint[NUM_OF_AGENT];
	private Vector<myPoint> _blockList;
	private Vector<myPoint> _closedList;
	private Vector<myPoint> _openList;
	transient Image buffer;
	private mainFrame _mFrame;

	// constructor
	public Grid(mainFrame main) {
		super();
		this._mFrame = main;
		setLayout(new GridLayout(get_width(), get_height()));
		for (int i = 0; i < get_height(); i++) {
			for (int j = 0; j < get_width(); j++) {
				this._grid[i][j] = new Cell(new myPoint(i, j), _mFrame);
				add(this._grid[i][j]);
			}
		}
		this.set_blockList(new Vector<myPoint>());
		this.set_closedList(new Vector<myPoint>());
		this.set_openList(new Vector<myPoint>());
	}

	public void set_starts(myPoint[] _starts) {
		this._starts = _starts;
	}

	public myPoint[] get_starts() {
		return _starts;
	}

	public void set_ends(myPoint[] _ends) {
		this._ends = _ends;
	}

	public myPoint[] get_ends() {
		return _ends;
	}

	public void set_blockList(Vector<myPoint> _blockList) {
		this._blockList = _blockList;
	}

	public Vector<myPoint> get_blockList() {
		return _blockList;
	}

	public void set_closedList(Vector<myPoint> _closedList) {
		this._closedList = _closedList;
	}

	public Vector<myPoint> get_closedList() {
		return _closedList;
	}

	public void set_openList(Vector<myPoint> _openList) {
		this._openList = _openList;
	}

	public Vector<myPoint> get_openList() {
		return _openList;
	}

	/**
	 * set the cell in x,y to be empty
	 * 
	 * @param x
	 * @param y
	 */
	public void setEmptyCell(int x, int y) {
		this._grid[x][y].set_status(Status.Empty);
		repaint();
	}

	/**
	 * set the cell in x,y to be starting point
	 * 
	 * @param x
	 * @param y
	 */
	public void setStartCell(int x, int y) {
		this._grid[x][y].set_status(Status.Start);
		repaint();
	}

	/**
	 * set the cell in x,y to be ending point
	 * 
	 * @param x
	 * @param y
	 */
	public void setEndCell(int x, int y) {
		this._grid[x][y].set_status(Status.Finish);
		repaint();
	}

	/**
	 * set the cell in x,y to be blocked
	 * 
	 * @param x
	 * @param y
	 */
	public void setBlockedCell(int x, int y) {
		this._grid[x][y].set_status(Status.Blocked);
		repaint();
	}

	/**
	 * set the cell in x,y to be in open
	 * 
	 * @param x
	 * @param y
	 */
	public void setOpenListCell(int x, int y) {
		this._grid[x][y].set_status(Status.inOpenList);
		repaint();
	}

	public void setOpenListCell(Vector<myPoint> points) {
		for (myPoint p : points) {
			this.setOpenListCell(p.getX(), p.getY());
		}
	}

	/**
	 * set the cell in x,y to be blocked
	 * 
	 * @param x
	 * @param y
	 */
	public void setClosedListCell(int x, int y) {
		this._grid[x][y].set_status(Status.inClosedList);
		repaint();
	}

	public void setClosedListCell(Vector<myPoint> points) {
		for (myPoint p : points) {
			this.setClosedListCell(p.getX(), p.getY());
		}
	}

	/**
	 * set the cell in x,y to be part of the final path
	 * 
	 * @param x
	 * @param y
	 */
	public void setFinalPathCell(int x, int y) {
		this._grid[x][y].set_status(Status.Path);
		repaint(0);
	}

	/**
	 * set the cell in x,y to be part of the final path
	 * 
	 * @param x
	 * @param y
	 */
	public void setFinalPathCell(int x, int y, int agentNum) {
		this._grid[x][y].set_status(Status.Path);
		this._grid[x][y].set_agent(agentNum);
		repaint();
	}

	/**
	 * set the grid's width
	 * 
	 * @param _width
	 */
	public void set_width(int _width) {
		this._width = _width;
	}

	/**
	 * 
	 * @return the grid's width
	 */
	public int get_width() {
		return _width;
	}

	/**
	 * set the grid's height
	 * 
	 * @param _height
	 */
	public void set_height(int _height) {
		this._height = _height;
	}

	/**
	 * 
	 * @return the grid's height
	 */
	public int get_height() {
		return _height;
	}

	/**
	 * paint the grid
	 */
	@Override
	public void paint(Graphics g) {
		if (buffer == null) {
			buffer = createImage(getBounds().width, getBounds().height);
		}
		Graphics bg = buffer.getGraphics();
		super.paint(bg);
		bg.setColor(Color.black);
		g.drawImage(buffer, 0, 0, null);
	}

	/**
	 * recall the paint method
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * add starting point p of agent number : AgentNum to the start points
	 * 
	 * @param p
	 * @param AgentNum
	 */
	public void addToStarts(myPoint p, int AgentNum) {
		int agent = AgentNum - 1;
		if (this.get_starts()[agent] == null) {
			this.get_starts()[agent] = p;
			setStartCell(p.getX(), p.getY());
		} else {
			setEmptyCell(this.get_starts()[agent].getX(),
					this.get_starts()[agent].getY());
			this.get_starts()[agent] = p;
			setStartCell(p.getX(), p.getY());
		}
	}

	/**
	 * add end's point p of agent number: AgentNum to the end points
	 * 
	 * @param p
	 * @param AgentNum
	 */
	public void addToEnds(myPoint p, int AgentNum) {
		int agent = AgentNum - 1;
		if (this.get_ends()[agent] == null) {
			this.get_ends()[agent] = p;
			setEndCell(p.getX(), p.getY());
		} else {
			setEmptyCell(this.get_ends()[agent].getX(), this.get_ends()[agent]
					.getY());
			this.get_ends()[agent] = p;
			setEndCell(p.getX(), p.getY());
		}
	}

	public void addToBlock(myPoint p) {
		if (this.get_blockList().contains(p)) {
			setEmptyCell(p.getX(), p.getY());
			this.get_blockList().remove(p);
		} else {
			this.get_blockList().add(p);
			setBlockedCell(p.getX(), p.getY());
		}
	}

	/**
	 * add point p to the open list
	 * 
	 * @param p
	 *            point
	 */
	public void addToOpenList(myPoint p) {
		this.get_openList().add(p);
	}

	/**
	 * add a few points to the open list
	 * 
	 * @param pVector
	 */
	public void addToOpenList(Vector<myPoint> pVector) {
		for (int i = 0; i < pVector.size(); i++) {
			this.get_openList().add(pVector.elementAt(i));
		}

	}

	/**
	 * remove point from the open list
	 * 
	 * @param p
	 */
	public void removeFromOpenList(myPoint p) {
		for (int i = 0; i <= this.get_openList().size(); i++) {
			if (this.get_openList().elementAt(i).equals(p)) {
				this.get_openList().remove(i);
			}
		}
	}

	/**
	 * add point p to the closed list
	 * 
	 * @param p
	 *            point
	 */
	public void addToClosedList(myPoint p) {
		this.get_closedList().add(p);
	}

	/**
	 * add a few points to the closed list
	 * 
	 * @param pVector
	 */
	public void addToclosedList(Vector<myPoint> pVector) {
		for (int i = 0; i < pVector.size(); i++) {
			this.get_closedList().add(pVector.elementAt(i));
		}
	}

	/**
	 * remove point from the closed list
	 * 
	 * @param p
	 */
	public void removeFromclosedList(myPoint p) {
		for (int i = 0; i <= this.get_closedList().size(); i++) {
			if (this.get_closedList().elementAt(i).equals(p)) {
				this.get_closedList().remove(i);
			}
		}
	}

	/**
	 * checks if all the parameters entered correctly
	 * 
	 * @return
	 */
	public boolean checkArguments() {
		boolean ans = true;
		for (int i = 0; i < NUM_OF_AGENT; i++) {
			if (this.get_starts()[i] == null | this.get_ends()[i] == null) {
				ans = false;
				break;
			}
		}
		return ans;
	}

	// debug
	public void printStartPoints() {
		for (int i = 0; i < NUM_OF_AGENT; i++) {
			if (this.get_starts()[i] != null) {
				System.out.println(this.get_starts()[i].toString());
			}
		}
	}

	public void drawFinalPaths(Vector<Vector<myPoint>> finalPath) {
		for (int i = 0; i < finalPath.size(); i++) {
			Vector<myPoint> tStep = finalPath.elementAt(i);
			for (int j=0; j< tStep.size(); j++){
				myPoint p = tStep.elementAt(j); 
				setFinalPathCell(p.getX(), p.getY(),j+1);
			
			}
		}
	}

	public void clear() {
		for (int i = 0; i < get_height(); i++) {
			for (int j = 0; j < get_width(); j++) {
				setEmptyCell(i, j);
				this._grid[i][j].set_agent(0);
				
			}
		}
		this._starts  = new myPoint[NUM_OF_AGENT];
		this._ends  = new myPoint[NUM_OF_AGENT];
		this._blockList.removeAllElements();

	}

	public void drawOneStep(Vector<myPoint> tStep) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			setFinalPathCell(p.getX(), p.getY(),j+1);	
		}
	}

	public void setEmptyStep(Vector<myPoint> tStep) {
		for (int j=0; j< tStep.size(); j++){
			myPoint p = tStep.elementAt(j); 
			setEmptyCell(p.getX(), p.getY());	
		}
		
	}

}// end of class

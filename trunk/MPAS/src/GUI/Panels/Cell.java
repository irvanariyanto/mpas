/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Panels;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.RepeatBehavior;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;

import Defaults.Enums.Direction;
import Defaults.Enums.Status;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.SetBlockCellEvent;
import EventMechanism.Events.SetFinishCellEvent;
import EventMechanism.Events.SetStartCellEvent;
import GUI.Utils.ColorManager;
import algorithms.myPoint;

/**
 * 
 * @author Liron Katav
 */
public class Cell extends Component implements ApplicationEventSource {

	private static final long serialVersionUID = 1L;
	public static final int SET_BLOCKS = 0, SET_START = 1, SET_FINISH = 2;
	public static final int MINIMUM_SIZE = 20;

	// fields
	private myPoint _position;
	private Vector<TileStatus> _tileStatuses;

	static int _editMode;
	public static int _agentSelected;
	private ApplicationEventListenerCollection _listeners;
	private boolean _withGridLine = false;

	// openlist+closed list animation
	private Timeline _timeline;
	private SwingRepaintTimeline _repaintTimeline;
	public static final int TIMER = 2500;
	private int _timer;
	public Color backgroundColor;
	private boolean isAnimating;
	private boolean _animationwithIcon = false;

	// Constructor
	public Cell(myPoint point, SwingRepaintTimeline repaintTimeline) {
		this._tileStatuses = new Vector<TileStatus>();
		// Action and mouse listener support
		this._listeners = new ApplicationEventListenerCollection();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		this._position = point;
		this.addTileStatus(Status.Empty, 0);
		_editMode = SET_BLOCKS;
		this._timer = TIMER;
		this.backgroundColor = Color.white;
		this._timeline = new Timeline(this);
		this._timeline.setDuration(_timer);
		this._timeline.addPropertyToInterpolate("backgroundColor", Color.blue,Color.white);
		this._repaintTimeline = repaintTimeline;
		this.isAnimating = false;
	}

	// Constructor
	public Cell(myPoint p, boolean block) {
		this._position = p;
		this._tileStatuses = new Vector<TileStatus>();
		this.addTileStatus(Status.Blocked,0);
		_editMode = SET_BLOCKS;
		// Action and mouse listener support
		this._listeners = new ApplicationEventListenerCollection();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
		this._repaintTimeline.forceRepaintOnNextPulse();
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void addColorProperty(Color c) {
		this._timeline.addPropertyToInterpolate("backgroundColor", c,Color.white);
	}

	public void setAnimationTimer(int time) {
		this._timer = time;
	}

	/*
	 * //animation addon 
	 * public void doAnimation(boolean isAnimating){ 
	 * if (this.isAnimating == isAnimating){ 
	 * 		return; 
	 * } 
	 * this.isAnimating =isAnimating; 
	 * if (this.isAnimating){ 
	 * 		this._timeline.replay(); 
	 * } 
	 * }
	 */
	public void doAnimation(boolean isAnimating, Color color) {
		if (this.isAnimating == isAnimating) {
			return;
		}
		this.isAnimating = isAnimating;
		if (this.isAnimating) {
			this._timeline.cancel();
			this._timeline = new Timeline(this);
			this._timeline.setDuration(_timer);
			this._timeline.addPropertyToInterpolate("backgroundColor", color,Color.white);
			// this.isAnimating = false;
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
	 * @param position
	 *            the _position to set
	 */
	public void setPosition(myPoint position) {
		this._position = position;
	}

	
	public void addTileStatus(Status status, int agentNumber) {
		if (status== Status.Empty
				|| status == Status.Blocked ) {
			this._tileStatuses.removeAllElements();
		}
		this._tileStatuses.add(new TileStatus(agentNumber, status));
	}
	
	public void addTileStatus(Status status) {
		if (status== Status.Empty
				|| status == Status.Blocked ) {
			this._tileStatuses.removeAllElements();
		}
		this._tileStatuses.add(new TileStatus(status));
	}

	/**
	 * set the status
	 * 
	 * @param tileStatus
	 */
	public void addTileStatus(TileStatus tileStatus) {
		if (tileStatus.getStatus() == Status.Empty
				|| tileStatus.getStatus() == Status.Blocked ) {
			this._tileStatuses.removeAllElements();
		}
		this._tileStatuses.add(tileStatus);
	}
	
	public void addTileStatus(Status status, int agent, Direction direction) {
		if (status == Status.Empty
				|| status == Status.Blocked) {
			this._tileStatuses.removeAllElements();
		}
		this._tileStatuses.add(new TileStatus(agent,status,direction));
		
	}

	/**
	 * @return the _status
	 */
	public Status getTileStatusAt(int i) {
		return this._tileStatuses.elementAt(i).getStatus();
	}

	/**
	 * @return the _status
	 */
	public int getAgentNumberAt(int i) {
		return this._tileStatuses.elementAt(i).getAgnetNum();
	}

	public void removeTileStatusAt(int i) {
		this._tileStatuses.removeElementAt(i);
	}

	public void removeTileStatus(Status status) {
		for (int i=0; i<this._tileStatuses.size(); i++){
			if (this._tileStatuses.elementAt(i).getStatus() == status){
				removeTileStatusAt(i);
			}
		}
		repaint();
		
	}
	/**
	 * @return the _status
	 */
	public Vector<TileStatus> getTileStatuses() {
		return this._tileStatuses;
	}

	/**
	 * @return the _editMode
	 */
	public int get_editMode() {
		return _editMode;

	}

	/**
	 * set the editMode
	 * 
	 * @param editMode
	 */
	public void set_editMode(int editMode) {
		Cell._editMode = editMode;

	}

	public boolean getWithGridLine() {
		return this._withGridLine;

	}

	public void setWithGridLine(boolean bool) {
		this._withGridLine = bool;
		repaint();
	}

	public void setAnimationwithIcon(boolean bool) {
		this._animationwithIcon = bool;
		repaint();
	}

	public boolean getAnimationwithIcon() {
		return this._animationwithIcon;
	}

	@Override
	public void paint(Graphics g) {
		Dimension size = getSize();
		Dimension RectSize = new Dimension(size.width - 2, size.height - 2);
		// Cast to Graphics2D so we can set composite information.
		Graphics2D g2d = (Graphics2D) g;

		// Save the original composite.
		Composite oldComp = g2d.getComposite();

		//float alpha = calculateAlpha();
		float alpha = 0.4f;
		Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);	
		if(this._tileStatuses.size()== 1 && (this._tileStatuses.elementAt(0).getStatus()==Status.Empty) || this._tileStatuses.elementAt(0).getStatus()==Status.Blocked){
			SetColorByStatus(g, this._tileStatuses.elementAt(0));
			g.fillRect(0, 0, size.width - 1, size.height - 1);			
		}
		else{
			for (int i = this._tileStatuses.size()-1; i > 0; i--) {
				TileStatus tStatus = this._tileStatuses.elementAt(i);
				SetColorByStatus(g, tStatus);	
				g2d.setComposite(alphaComp);
				if (this._tileStatuses.size() == 2 && (tStatus.getStatus()==Status.Start || tStatus.getStatus()==Status.Finish)) {
					g2d.setComposite(oldComp);
					g.fillRect(0, 0, size.width - 1, size.height - 1);
				}
				if (tStatus.getStatus() == Status.Path){
					g.fillRect(0, 0, size.width - 1, size.height - 1);
				}
				if (tStatus.getStatus() == Status.inOpenList || tStatus.getStatus() == Status.inClosedList){
					g.fillRect(0, 0, size.width - 1, size.height - 1);
				}				
				if (tStatus.getStatus() == Status.Path && tStatus.getDirection() != null) {
					g2d.setComposite(oldComp);
					g.setColor(Color.black);
					drawDircetions(g, tStatus.getDirection(), RectSize);
				}
				if (tStatus.getStatus()== Status.Start && tStatus.getDirection() != null) {
					g2d.setComposite(oldComp);
					g.setColor(Color.black);
					drawDircetions(g, tStatus.getDirection(), RectSize);
				}
				if (tStatus.getStatus() == Status.Finish && tStatus.getDirection() != null) {
					g2d.setComposite(oldComp);
					g.setColor(Color.black);
					drawDircetions(g, tStatus.getDirection(), RectSize);
				}
				if (tStatus.getStatus() == Status.Start) {
					g2d.setComposite(oldComp);
					if (RectSize.width > MINIMUM_SIZE && RectSize.height > MINIMUM_SIZE) {
						BufferedImage carImage = loadImage("Icons/newCar.png");
						tint(carImage, Color.white, ColorManager.getInstance().getColor("agent" + tStatus.getAgnetNum()));
						g.drawImage(carImage, 2, 2, RectSize.width,RectSize.height, this);
					}
					else{
						g2d.setColor(Color.green);
						g2d.fillRect(0, 0, size.width - 1, size.height - 1);
					}
				}
				if (tStatus.getStatus()== Status.Path && _animationwithIcon ) {
					if (!isStatusesContains(Status.Finish, tStatus.getAgnetNum())){
						g2d.setComposite(oldComp);
						g.setColor(Color.white);
						g.fillRect(0, 0, size.width - 1, size.height - 1);		
						if (RectSize.width > MINIMUM_SIZE && RectSize.height > MINIMUM_SIZE) {
							BufferedImage carImage = loadImage("Icons/newCar.png");
							tint(carImage, Color.white, ColorManager.getInstance().getColor("agent" + tStatus.getAgnetNum()));
							g.drawImage(carImage, 2, 2, RectSize.width,RectSize.height, this);
						}
					}
				}
				if (tStatus.getStatus()== Status.Finish ) {
					g2d.setComposite(oldComp);
					if (RectSize.width > MINIMUM_SIZE && RectSize.height > MINIMUM_SIZE) {
						BufferedImage flagImage = loadImage("Icons/finishFlag1.png");
						tint(flagImage, Color.white, ColorManager.getInstance().getColor("agent" + tStatus.getAgnetNum()));
						g.drawImage(flagImage, 5, 5, RectSize.width,RectSize.height, this);
					}
					else{
						g2d.setColor(Color.red);
						g2d.fillRect(0, 0, size.width - 1, size.height - 1);
					}
				}
				
			}//end of for
			
		}//end of else
		if (this._tileStatuses.elementAt(this._tileStatuses.size()-1).getAgnetNum() != 0 ) {
			g2d.setComposite(oldComp);
			g2d.setColor(Color.black);
			g.setFont(new Font("sansserif", Font.BOLD, 11));
			g.drawString(Integer.toString(this._tileStatuses.elementAt(this._tileStatuses.size()-1).getAgnetNum()), 5, 10);
		}
		if (this._withGridLine) {
			g2d.setComposite(oldComp);
			g.setColor(Color.black);
			g.drawRect(0,0, RectSize.width, RectSize.height);
		}
		// amit's additions
		Color backgr = this.getBackgroundColor();
		if (!Color.white.equals(backgr)) {
			g2d.setColor(backgr);
			g2d.fillRect(0, 0, size.width - 1, size.height - 1);
			
			int StartAgnet = isStatusesContains(Status.Start);
			if(StartAgnet!= -1){
				g2d.setComposite(oldComp);
				if (RectSize.width > MINIMUM_SIZE && RectSize.height > MINIMUM_SIZE) {
					BufferedImage carImage = loadImage("Icons/newCar.png");
					tint(carImage, Color.white, ColorManager.getInstance().getColor("agent" + StartAgnet));
					g.drawImage(carImage, 2, 2, RectSize.width,RectSize.height, this);
				}
				else{
					g2d.setColor(Color.green);
					g2d.fillRect(0,0, size.width - 1, size.height - 1);
				}
			}//end of ifelse
			int FinishAgnet = isStatusesContains(Status.Finish);
			if(FinishAgnet!= -1){
				g2d.setComposite(oldComp);
				if (RectSize.width > MINIMUM_SIZE && RectSize.height > MINIMUM_SIZE) {
					BufferedImage flagImage = loadImage("Icons/finishFlag1.png");
					tint(flagImage, Color.white, ColorManager.getInstance().getColor("agent" +FinishAgnet));
					g.drawImage(flagImage, 5, 5, RectSize.width,RectSize.height, this);
				}
				else{
					g2d.setColor(Color.red);
					g2d.fillRect(0, 0, size.width - 1, size.height - 1);
				}
			}
			
			
			
			
		}
		// amit's edition ends
	}
	
	private int isStatusesContains(Status status) {
		int res = -1;
		for (int i=0; i< this._tileStatuses.size(); i++){
			TileStatus tileStatuses  = this._tileStatuses.elementAt(i);
			if(tileStatuses.getStatus() == status  ){
				res = tileStatuses.getAgnetNum();
			}
		}
		return res;
	}

	private boolean isStatusesContains( Status status, int agnet){
		boolean res = false;
		for (int i=0; i< this._tileStatuses.size(); i++){
			TileStatus tileStatuses  = this._tileStatuses.elementAt(i);
			if(tileStatuses.getStatus() == status  ){
				if(tileStatuses.getAgnetNum() == agnet){
				res = true;
				}
				else{
					res = false;
				}
			}
		}
		return res;
	}

	private float calculateAlpha() {
		return 1 / (float) (this._tileStatuses.size());
	}

	private void SetColorByStatus(Graphics g, TileStatus tileStatus) {
		Status tStatus = tileStatus.getStatus(); 
		if (tStatus.equals(Status.Empty)) {
			g.setColor(Color.white);
		}
		if (tStatus.equals(Status.Start)) {
			// g.setColor(Color.green);
			g.setColor(Color.white);
		}
		if (tStatus.equals(Status.Finish)) {
			// g.setColor(Color.red);
			g.setColor(Color.white);
		}
		if (tStatus.equals(Status.Blocked)) {
			g.setColor(Color.black);
		}
		if (tStatus.equals(Status.inOpenList)) {
			Color tColor = ColorManager.getInstance().getColor("agent" + tileStatus.getAgnetNum());
			g.setColor(tColor);
		}
		if (tStatus.equals(Status.inClosedList)) {
			Color tColor = ColorManager.getInstance().getColor("agent" + tileStatus.getAgnetNum());
			g.setColor(tColor);
		}
		if (tStatus.equals(Status.Path)) {
			if (_animationwithIcon) {
				g.setColor(Color.white);
			} else {
				Color tColor = ColorManager.getInstance().getColor("agent" + tileStatus.getAgnetNum());
				g.setColor(tColor);
			}
		}

	}

	private void drawDircetions(Graphics g, Direction direction, Dimension size) {
		if (direction == Direction.LEFT_RIGHT
				|| direction == Direction.RIGHT_LEFT) {
			g.drawLine(0, size.height / 2, size.width, size.height / 2);
		}
		if (direction == Direction.TOP_DOWN || direction == Direction.DOWN_TOP) {
			g.drawLine(size.width / 2, 0, size.width / 2, size.height);
		}
		if (direction == Direction.TOPLEFT_DOWNRIGHT
				|| direction == Direction.DOWNRIGHT_TOPLEFT) {
			g.drawLine(0, 0, size.width, size.height);
		}
		if (direction == Direction.DOWNLEFT_TOPRIGHT
				|| direction == Direction.TOPRIGHT_DOWNLEFT) {
			g.drawLine(0, size.height, size.width, 0);
		}
		if (direction == Direction.LEFT_TOP || direction == Direction.TOP_LEFT) {
			g.drawLine(0, size.height / 2, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width / 2, 0);
		}
		if (direction == Direction.LEFT_DOWN
				|| direction == Direction.DOWN_LEFT) {
			g.drawLine(0, size.height / 2, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width / 2,
					size.height);
		}
		if (direction == Direction.TOP_RIGHT
				|| direction == Direction.RIGHT_TOP) {
			g.drawLine(size.width / 2, 0, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width,
					size.height / 2);
		}
		if (direction == Direction.RIGHT_DOWN
				|| direction == Direction.DOWN_RIGHT) {
			g.drawLine(size.width, size.height / 2, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width / 2,
					size.height);
		}
		if (direction == Direction.CENTER_DOWN
				|| direction == Direction.DOWN_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, size.width / 2,
					size.height);
		}
		if (direction == Direction.CENTER_TOP
				|| direction == Direction.TOP_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, size.width / 2, 0);
		}
		if (direction == Direction.CENTER_LEFT
				|| direction == Direction.LEFT_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, 0, size.height / 2);
		}
		if (direction == Direction.CENTER_RIGHT
				|| direction == Direction.RIGHT_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, size.width,
					size.height / 2);
		}
		if (direction == Direction.CENTER_TOPLEFT
				|| direction == Direction.TOPLEFT_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, 0, 0);
		}
		if (direction == Direction.CENTER_TOPRIGHT
				|| direction == Direction.TOPRIGHT_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, size.width, 0);
		}
		if (direction == Direction.CENTER_DOWNLEFT
				|| direction == Direction.DOWNLEFT_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, 0, size.height);
		}
		if (direction == Direction.CENTER_DOWNRIGHT
				|| direction == Direction.DOWNRIGHT_CENTER) {
			g.drawLine(size.width / 2, size.height / 2, size.width, size.height);
		}

		if (direction == Direction.TOP_TOPLEFT
				|| direction == Direction.TOPLEFT_TOP) {
			g.drawLine(size.width / 2, 0, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, 0);
		}
		if (direction == Direction.TOP_TOPRIGHT
				|| direction == Direction.TOPRIGHT_TOP) {
			g.drawLine(size.width / 2, 0, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, 0);
		}
		if (direction == Direction.TOP_DOWNRIGHT
				|| direction == Direction.DOWNRIGHT_TOP) {
			g.drawLine(size.width / 2, 0, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, size.height);
		}
		if (direction == Direction.TOP_DOWNLEFT
				|| direction == Direction.DOWNLEFT_TOP) {
			g.drawLine(size.width / 2, 0, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, size.height);
		}

		if (direction == Direction.DOWN_DOWNLEFT
				|| direction == Direction.DOWNLEFT_DOWN) {
			g.drawLine(size.width / 2, size.height, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, size.height);
		}
		if (direction == Direction.DOWN_DOWNRIGHT
				|| direction == Direction.DOWNRIGHT_DOWN) {
			g.drawLine(size.width / 2, size.height, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, size.height);
		}
		if (direction == Direction.DOWN_TOPLEFT
				|| direction == Direction.TOPLEFT_DOWN) {
			g.drawLine(size.width / 2, size.height, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, 0);
		}
		if (direction == Direction.DOWN_TOPRIGHT
				|| direction == Direction.TOPRIGHT_DOWN) {
			g.drawLine(size.width / 2, size.height, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, 0);
		}
		if (direction == Direction.LEFT_TOPLEFT
				|| direction == Direction.TOPLEFT_LEFT) {
			g.drawLine(0, size.height, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, 0);
		}
		if (direction == Direction.LEFT_TOPRIGHT
				|| direction == Direction.TOPRIGHT_LEFT) {
			g.drawLine(0, size.height / 2, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, 0);
		}
		if (direction == Direction.LEFT_DOWNRIGHT
				|| direction == Direction.DOWNRIGHT_LEFT) {
			g.drawLine(0, size.height / 2, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, size.height);
		}
		if (direction == Direction.LEFT_DOWNLEFT
				|| direction == Direction.DOWNLEFT_LEFT) {
			g.drawLine(0, size.height, size.width / 2, size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, size.height);
		}
		if (direction == Direction.RIGHT_TOPRIGHT
				|| direction == Direction.TOPRIGHT_RIGHT) {
			g.drawLine(size.width, size.height / 2, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, 0);
		}
		if (direction == Direction.RIGHT_TOPLEFT
				|| direction == Direction.TOPLEFT_RIGHT) {
			g.drawLine(size.width, size.height / 2, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, 0);
		}
		if (direction == Direction.RIGHT_DOWNRIGHT
				|| direction == Direction.RIGHT_DOWNRIGHT) {
			g.drawLine(size.width, size.height / 2, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, size.width, size.height);
		}
		if (direction == Direction.RIGHT_DOWNLEFT
				|| direction == Direction.DOWNLEFT_RIGHT) {
			g.drawLine(size.width, size.height / 2, size.width / 2,
					size.height / 2);
			g.drawLine(size.width / 2, size.height / 2, 0, size.height);
		}
	}

	public void processMouseEvent(MouseEvent event) {
		super.processMouseEvent(event);
		if (event.getID() == MouseEvent.MOUSE_PRESSED && this.isEnabled()) {
			myPoint p = getPointFromSource(event);
			if (_editMode == SET_BLOCKS) {
				this._listeners.fireEvent(new SetBlockCellEvent(this, p));
			}
			if (_editMode == SET_START) {
				this._listeners.fireEvent(new SetStartCellEvent(this, p,
						_agentSelected));
			}
			if (_editMode == SET_FINISH) {
				this._listeners.fireEvent(new SetFinishCellEvent(this, p,
						_agentSelected));
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

	/**
	 * stops the cell animation
	 */
	public synchronized void stopAnimation() {
		this._timeline.end();
		this.backgroundColor = Color.white;


	}

	public static BufferedImage loadImage(String url) {
		ImageIcon icon = new ImageIcon(url);
		Image image = icon.getImage();

		// Create empty BufferedImage, sized to Image
		BufferedImage buffImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw Image into BufferedImage
		Graphics g = buffImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		return buffImage;
	}

	public static void tint(BufferedImage img, Color oldColor, Color newColor) {
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				// do something with the color :) (change the hue, saturation
				// and/or brightness)
				// float[] hsb = new float[3];
				// Color.RGBtoHSB(color.getRed(), old.getGreen(), old.getBlue(),
				// hsb);

				// or just call brighter to just tint it
				if (img.getRGB(x, y) == oldColor.getRGB()) {
					img.setRGB(x, y, newColor.getRGB());
				}

			}
		}
	}

	public static BufferedImage makeColorTransparent(String ref, Color color) {
		BufferedImage image = loadImage(ref);
		BufferedImage dimg = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dimg.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(image, null, 0, 0);
		g.dispose();
		for (int i = 0; i < dimg.getHeight(); i++) {
			for (int j = 0; j < dimg.getWidth(); j++) {
				if (dimg.getRGB(j, i) == color.getRGB()) {
					dimg.setRGB(j, i, 0x123456);
				}
			}
		}
		return dimg;
	}

	

	

	

}// end of class Cell


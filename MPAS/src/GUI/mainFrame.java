/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controller.Controller;
import Events.ApplicationEvent;
import Events.ApplicationEventListener;
import Events.ClosedListChangeEvent;
import Events.OpenListChangeEvent;
import Events.finalPathEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import algorithms.AStarPathFinder;
import algorithms.myPoint;

/**
 * 
 * @author Liron Katav
 */
public class mainFrame extends JFrame implements ActionListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel _selectionPanel;
	public JPanel __mainPanel;
	private JPanel _contorlPanel;
	private JRadioButton _rBlock, _rStart, _rEnd;
	private ButtonGroup _selectionGroup;
	private JComboBox _AgentBox;
	private JButton _bStart, _bStop, _bStep, _bClear;
	private JMenuBar _menuBar;
	private JMenu _FileMenu, _ViewMenu, _helpMenu;
	private JMenuItem _openItem, _saveItem, _exitItem;
	private JCheckBoxMenuItem _FinalPathsItem, _openListItem, _closedListItem;
	private boolean _showFinalPath, _showOpenList, _showClosedList;

	String[] agentsStrings = { "Agent1", "Agent2" };
	private Grid _grid;
	private Controller _controller;
	private String _command;
	private int _agentNum;
	private Vector<Vector<myPoint>> _finalPaths;
	private int _stepNum;
	private boolean _stepPressed;

	// Constructor
	public mainFrame(String title) {
		super(title);
		this._stepPressed = false;
		initComponenets();
	}

	private void initComponenets() {
		set_grid(new Grid(this));
		set_controller(new Controller(this));
		this._controller.addListener(new MainWindowListener());
		_selectionPanel = new JPanel();
		__mainPanel = new JPanel();
		_contorlPanel = new JPanel();
		this._menuBar = new JMenuBar();
		this._FileMenu = new JMenu("File");
		this._ViewMenu = new JMenu("View");
		this._helpMenu = new JMenu("Help");
		this._command = "start";
		this._agentNum = 1;
		_finalPaths = null;
		this._stepNum = 0;
		this._showFinalPath = false;
		this._showOpenList = false;
		this._showClosedList = false;

		_menuBar.add(_FileMenu);
		_openItem = new JMenuItem("Open");
		_FileMenu.add(_openItem);
		_saveItem = new JMenuItem("Save");
		_FileMenu.add(_saveItem);
		_exitItem = new JMenuItem("Exit");
		_FileMenu.add(_exitItem);

		_menuBar.add(_ViewMenu);
		_FinalPathsItem = new JCheckBoxMenuItem("Show Final Paths");
		_ViewMenu.add(_FinalPathsItem);
		_openListItem = new JCheckBoxMenuItem("Show Open List");
		_ViewMenu.add(_openListItem);
		_closedListItem = new JCheckBoxMenuItem("Show Closed List");
		_ViewMenu.add(_closedListItem);

		_menuBar.add(_helpMenu);

		_selectionGroup = new ButtonGroup();
		_rBlock = new JRadioButton("Block");
		_rStart = new JRadioButton("Start Point");
		_rStart.setSelected(true);
		_rEnd = new JRadioButton("End Point");

		_AgentBox = new JComboBox(agentsStrings);

		_bStart = new JButton("Start");
		_bStop = new JButton("stop");
		_bStep = new JButton("step");
		_bClear = new JButton("Clear");

		_selectionGroup.add(_rBlock);
		_selectionGroup.add(_rStart);
		_selectionGroup.add(_rEnd);

		GroupLayout layout = new GroupLayout(_selectionPanel);
		_selectionPanel.setLayout(layout);

		layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(_AgentBox).addComponent(_rStart)
						.addComponent(_rEnd).addComponent(_rBlock)));

		layout.setVerticalGroup(layout.createSequentialGroup().addGap(50)
				.addComponent(_AgentBox).addComponent(_rStart)
				.addComponent(_rEnd).addComponent(_rBlock).addGap(350));

		_contorlPanel.add(_bStart);
		_contorlPanel.add(_bStop);
		_contorlPanel.add(_bStep);
		_contorlPanel.add(_bClear);

		__mainPanel.setLayout(new BorderLayout(10, 10));
		__mainPanel.setSize(612, 482);

		__mainPanel.add(_selectionPanel, BorderLayout.WEST);
		__mainPanel.add(get_grid(), BorderLayout.CENTER);
		__mainPanel.add(_contorlPanel, BorderLayout.SOUTH);
		this.setJMenuBar(_menuBar);

		this._rStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startPointActionPerformed(evt);
			}
		});

		this._rEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				endPointActionPerformed(evt);
			}
		});

		this._rBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				blockedActionPerformed(evt);
			}
		});

		this._AgentBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				agentBoxActionPerformed(evt);
			}
		});

		this._bStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bStartActionPerformed(evt);
			}
		});

		this._bStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bStopActionPerformed(evt);
			}
		});

		this._bStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bStepActionPerformed(evt);
			}
		});

		this._bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bClearActionPerformed(evt);
			}
		});

		_openItem.addActionListener(this);
		_saveItem.addActionListener(this);
		_exitItem.addActionListener(this);
		_FinalPathsItem.addItemListener(this);
		_openListItem.addItemListener(this);
		_closedListItem.addItemListener(this);
		// _helpMenu.addActionListener( this);
	}

	public void set_grid(Grid _grid) {
		this._grid = _grid;
	}

	public Grid get_grid() {
		return _grid;
	}

	private void startPointActionPerformed(ActionEvent evt) {
		this._command = "start";
	}

	private void endPointActionPerformed(ActionEvent evt) {
		this._command = "end";

	}

	private void blockedActionPerformed(ActionEvent evt) {
		this._command = "block";
	}

	private void agentBoxActionPerformed(ActionEvent evt) {
		this._agentNum = this._AgentBox.getSelectedIndex() + 1;
	}

	private void bStartActionPerformed(ActionEvent evt) {
		if (this._grid.checkArguments()) {
			this._controller.setTile(this._grid.get_blockList());
			this._bClear.setEnabled(false);
			this._bStart.setEnabled(false);
			this._bStep.setEnabled(false);
			this.get_controller()
					.findPath(this.get_grid().get_starts(),
							this.get_grid().get_ends(),
							this.get_grid().get_blockList());
		} else {
			JOptionPane.showMessageDialog(this,
					"You havn't entered all the parameters, please try again",
					"Missing Argumets", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void bStopActionPerformed(ActionEvent evt) {
		if (this._controller.getThread() != null) {
			this._controller.getThread().stop();
		}
		this._bClear.setEnabled(true);
	}

	private void bStepActionPerformed(ActionEvent evt) {
		if (this._grid.checkArguments()) {
			this._controller.setTile(this._grid.get_blockList());
			mainFrame.this._stepPressed = true;
			if (this._finalPaths == null) {
				this._bStep.setEnabled(false);
				this._bClear.setEnabled(false);
				this._bStart.setEnabled(false);
				this.get_controller().findPath(this.get_grid().get_starts(),
						this.get_grid().get_ends(),
						this.get_grid().get_blockList());
			} else {
				if (this._stepNum < this._finalPaths.size()) {
					Vector<myPoint> tstep = this._finalPaths
							.elementAt(this._finalPaths.size() - this._stepNum
									- 1);

					if (this.get_showFinalPath() == false) {
						Vector<myPoint> prevStep = mainFrame.this._finalPaths
								.elementAt(mainFrame.this._finalPaths.size()
										- mainFrame.this._stepNum);
						for (myPoint p : prevStep) {
							mainFrame.this.get_grid().setEmptyCell(p.getX(),
									p.getY());
						}
					}
					this.get_grid().drawOneStep(tstep);
					this._stepNum++;
				} else {
					JOptionPane.showMessageDialog(this, "No more steps",
							"Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"You havn't entered all the parameters, please try again",
					"Missing Argumets", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void bClearActionPerformed(ActionEvent evt) {
		this.get_grid().clear();
		this._stepNum = 0;
		this._finalPaths = null;
		this._bStart.setEnabled(true);
		this._bStep.setEnabled(true);
		this._stepPressed = false;
	}

	public String getCommand() {
		return this._command;

	}

	public int getAgentNumber() {
		return this._agentNum;

	}

	public void set_controller(Controller _controller) {
		this._controller = _controller;
	}

	public Controller get_controller() {
		return _controller;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JMenuItem source = (JMenuItem) (event.getSource());
		if (source == this._openItem) {
			System.out.println("save clicked");
			openItemClicked();
		} else if (source == this._saveItem) {
			System.out.println("save clicked");
			saveItemClicked();
		} else if (source == this._exitItem) {
			System.out.println("exit clicked");
			exitItemClicked();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		JMenuItem source = (JMenuItem) (event.getSource());
		if (source == this._FinalPathsItem) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this._showFinalPath = true;
			} else {
				this._showFinalPath = false;
			}
		} else if (source == this._openListItem) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this._showOpenList = true;
			} else {
				this._showOpenList = false;
			}
		} else if (source == this._closedListItem) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this._showClosedList = true;
			} else {
				this._showClosedList = false;
			}
		}
	}

	private void openItemClicked() {
		// TODO Auto-generated method stub

	}

	private void exitItemClicked() {
		// TODO Auto-generated method stub
	}

	private void saveItemClicked() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the _showFinalPath
	 */
	public boolean get_showFinalPath() {
		return this._showFinalPath;
	}

	/**
	 * @return the _showFinalPath
	 */
	public boolean get_showOpenList() {
		return this._showOpenList;
	}

	/**
	 * @return the _showFinalPath
	 */
	public boolean get_showClosedList() {
		return this._showClosedList;
	}

	protected class MainWindowListener implements ApplicationEventListener {

		@Override
		public void handle(ApplicationEvent event) {
			if (event instanceof finalPathEvent) {
				AStarPathFinder pathFinder = (AStarPathFinder) event
						.getSource();
				mainFrame.this._finalPaths = pathFinder.get_finalPath();
				if (mainFrame.this._finalPaths.isEmpty()) {
					JOptionPane.showMessageDialog(mainFrame.this,
							"There is no path", "no path",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (mainFrame.this._stepPressed) {
					if (mainFrame.this._stepNum < mainFrame.this._finalPaths
							.size()) {
						Vector<myPoint> tstep = mainFrame.this._finalPaths
								.elementAt(mainFrame.this._finalPaths.size()
										- mainFrame.this._stepNum - 1);
						mainFrame.this.get_grid().drawOneStep(tstep);
						mainFrame.this._stepNum++;
					}
					mainFrame.this._stepPressed = false;
				} else {
					if (mainFrame.this.get_showFinalPath() == true) {
						mainFrame.this.get_grid().drawFinalPaths(
								pathFinder.get_finalPath());
					} else {
						/*
						 * for(int i=0; i<pathFinder.get_finalPath().size()-1;
						 * i++){ Vector<myPoint> tstep =
						 * mainFrame.this._finalPaths
						 * .elementAt(mainFrame.this._finalPaths.size() - i -1);
						 * Vector<myPoint> PrevStep =
						 * mainFrame.this._finalPaths.
						 * elementAt(mainFrame.this._finalPaths.size() - i);
						 * mainFrame.this.get_grid().drawOneStep(tstep);
						 * mainFrame.this.get_grid().setEmptyStep(PrevStep);
						 * //delay
						 * 
						 * }
						 */
						mainFrame.this.get_grid().drawFinalPaths(
								pathFinder.get_finalPath());
					}
					mainFrame.this._bStart.setEnabled(true);
				}
				mainFrame.this._bClear.setEnabled(true);
				mainFrame.this._bStep.setEnabled(true);
			} else if (event instanceof OpenListChangeEvent) {
				if (mainFrame.this.get_showOpenList() == true) {
					mainFrame.this.get_grid().setOpenListCell(
							((OpenListChangeEvent) event).get_points());
				}
			} else if (event instanceof ClosedListChangeEvent) {
				if (mainFrame.this.get_showClosedList() == true) {
					mainFrame.this.get_grid().setClosedListCell(
							((ClosedListChangeEvent) event).get_points());
				}
			}

		}

	}

}// end of class


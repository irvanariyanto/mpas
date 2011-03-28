package GUI.Panels;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorithms.myPoint;


import Controller.GridController;
import Defaults.defaultsValues;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.OpenListChangeEvent;
import EventMechanism.Events.SetBlockCellEvent;
import EventMechanism.Events.SetFinishCellEvent;
import EventMechanism.Events.SetStartCellEvent;
import EventMechanism.Events.finalPathEvent;
import EventMechanism.Events.showOpenListStateEvent;
import GUI.AutoStepsThread;


public class mainPanel extends JPanel implements ApplicationEventSource{

	private static final long serialVersionUID = 1L;
	
	// Variables declaration
	private String _algorithmChosen;
	private String _heuristicChosen;
	private boolean _directionChosen; // 1 = 8D, 0 = 4D
	private int _numberOfAgents;
	private int _gridSize ;
	//for running in step mode
	private boolean _firstStep;
	
	private ApplicationEventListenerCollection _listeners; 
	private GridPanel _grid;
	private ConfigurationPanel _configPanel;
	private GridController _controller;
	private AutoStepsThread _stepThread;
	// End of variables declaration
	
	//TODO make it better later
	private Vector<myPoint> oldState;
	
	/**
	 * Constructor
	 */
	public mainPanel() {
		super();
		initComponenets();
		_firstStep = true;
	}
	public GridPanel getGrid(){
		return this._grid;
	}
	public ConfigurationPanel getConfiguarationPanel(){
		return this._configPanel;
	}
	/**
	 * initialize all the swing Components
	 */
	private void initComponenets() {
		_grid = new GridPanel(defaultsValues.GridSize);	
		_gridSize = defaultsValues.GridSize;
		_configPanel = new ConfigurationPanel();
		_controller = new GridController();
		_heuristicChosen = this._configPanel.getSettingsPanel().getHeuristic();
		init_controller();	
		this._controller.addListener(new ApplicationEventListener() {
			
			@Override
			public void handle(ApplicationEvent event) {
				if (event instanceof finalPathEvent){
					Vector<Vector<myPoint>> path = mainPanel.this._controller.getFinalPath();
					mainPanel.this._grid.drawFinalPaths(path);
					mainPanel.this.reset();
				}
				else if (event instanceof showOpenListStateEvent<?>){
					mainPanel.this.oldState = ((showOpenListStateEvent<myPoint>)event).getCoordinates();
					mainPanel.this.getGrid().drawOneStep(mainPanel.this.oldState);
					mainPanel.this._configPanel.getInfoPanel().writeToTextArea(mainPanel.this.oldState.toString());
				}
				
			}
		});
		this._listeners = new ApplicationEventListenerCollection();
		this.setLayout(new BorderLayout(2,2));
		this.add(_configPanel , BorderLayout.WEST);
		this.add(_grid , BorderLayout.CENTER);
		getcAlgorithm().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 cAlgorithmActionPerformed(evt);
	         }
	     });
		getcHeuristic().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 cHeuristicActionPerformed(evt);
	         }
	     });
		getcDirections().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 cDirectionsActionPerformed(evt);
	         }
	     });
		getsNumOfAgents().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent evt) {
				sNumOfAgentsActionPerformed(evt);
				
			}
	     });
		getsGridSize().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent evt) {
				sGridSizeActionPerformed(evt);			
			}
	     });
		getSetBlock().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 rBlockActionPerformed(evt);
	    	 }
	     });
		getSetStart().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                rStartActionPerformed(evt);
            }
        });
        getSetFinish().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                rEndActionPerformed(evt);
            }
        });
        getAgentComboBox().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
            	AgentComboBoxActionPerformed(evt);
            }
        });	
        getbGeneratePositions().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
            	bGeneratePositionsActionPerformed(evt);
            }
        });	
        getbClearPositions().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
            	bClearPositionsActionPerformed(evt);
            }
        });	
        
        getbRandomMap().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
            	bRandomMapActionPerformed(evt);
            }
        });	
        getbClearMap().addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
            	bClearMapActionPerformed(evt);
            }
        });	
        getbFindPath().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 bFindPathActionPerformed(evt);
	         }
	     });
        getbClearPath().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 bClearPathActionPerformed(evt);
	         }
	     });
        getStepButton().addActionListener(new ActionListener() {
			
        	public void actionPerformed(ActionEvent evt) {
				bStepActionPerformed(evt);
				
			}
		});
        getStopButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
				
			}
		});
        _configPanel.getControlPanel().getAutoButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!_configPanel.getControlPanel().getAutoButton().isSelected()){
					_configPanel.getControlPanel().getStepButton().setEnabled(true);
					_stepThread.stop();
				}
				
			}
		});
        _configPanel.getControlPanel().getAutoStepSlider().addChangeListener(new ChangeListener() {
			//TODO remove duplicate listeners
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    _configPanel.getControlPanel().setAutoStepLabel(value *100 + " msec");
			    if (_stepThread !=null){
				_stepThread.setInterval(_configPanel.getControlPanel().getAutoStepValue() * 100);
			    }
				
			}
		});
		this._grid.addListener(new MainFrameListener());
		this._grid.setAgentNumber(1);//Default			
	}	
	
	public void stop(){
		reset();
		if (this._controller.getAlgorithmThread() != null){
			this._controller.getAlgorithmThread().stop();
		}
	
	}
	
	


	protected void reset() {
		if (_stepThread != null){
			_stepThread.stop();
		}
		_firstStep = true;
		_configPanel.getControlPanel().getStepButton().setEnabled(true);
		
	}
	
	protected void bStepActionPerformed(ActionEvent evt) {
		if (_configPanel.getControlPanel().getAutoButton().isSelected()){
			_stepThread = new AutoStepsThread(this._configPanel.getControlPanel().getAutoStepValue()*100,this);
			_stepThread.start();
			this._configPanel.getControlPanel().getStepButton().setEnabled(false);
		}
		else{
			performStep();
		}
	
	}
	//TODO move to gui controller
	public void performStep(){
		if (this._firstStep){
			System.out.println(this._controller.getMap().toString());
			this._controller.runAlgorithmWithPause(this._grid.get_startsList(),this._grid.get_FinishList());
			_firstStep = false;
		}
		else{
			if (oldState != null){
				this.getGrid().setEmptyStep(oldState);
			}
			this._controller.resumeAlgorithm();
		}
	}
	public GridController get_controller() {
		return this._controller;
	}

	
	private void init_controller(){
		this._controller.setAlgorithm(_algorithmChosen);
		this._controller.setHeuristic(_heuristicChosen);
		this._controller.setDirection(_directionChosen);
		this._controller.setNumberOfAgents (_numberOfAgents);
		this._controller.setMapSize (_gridSize);		
	}
	
	private void init_controller(String algo, String heuristic, boolean dir, int numOfAgents, int gridSize){
		this._controller.setAlgorithm(algo);
		this._controller.setHeuristic(heuristic);
		this._controller.setDirection(dir);
		this._controller.setNumberOfAgents (numOfAgents);
		this._controller.setMapSize (gridSize);		
	}
	

	protected void sGridSizeActionPerformed(ChangeEvent evt) {
		this._grid.clearBlocks(this._controller.getMap());
		this._grid.clearPositions();
		_gridSize = this._configPanel.getSettingsPanel().getGridSize();
		ChangeGridPanel(_gridSize);	
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	protected void sNumOfAgentsActionPerformed(ChangeEvent evt) {
		_numberOfAgents = this._configPanel.getSettingsPanel().getNumberOfAgents();
		ChangeComboBoxSize(_numberOfAgents);
		this._grid.setNUM_OF_AGENT(_numberOfAgents);
		this._grid.setAgentNumber(1);
		//this._grid.clearBlocks(this._controller.getMap());
		//this._grid.clearPositions();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	protected void cDirectionsActionPerformed(ActionEvent evt) {
		_directionChosen = this._configPanel.getSettingsPanel().getDirection();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	protected void cHeuristicActionPerformed(ActionEvent evt) {
		_heuristicChosen = this._configPanel.getSettingsPanel().getHeuristic();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	protected void cAlgorithmActionPerformed(ActionEvent evt) {
		_algorithmChosen = this._configPanel.getSettingsPanel().getAlgorithm();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	protected void bClearMapActionPerformed(ActionEvent evt) {
		this._grid.clearAll(this._controller.getMap());
		//this._controller.clearMap();
		
	}
	protected void bRandomMapActionPerformed(ActionEvent evt) {
		this._grid.createRandomBlocks(this._configPanel.getSettingsPanel().getsDensityValue(), this._controller.getMap());
		
	}
	
	protected void bFindPathActionPerformed(ActionEvent evt) {
		this._configPanel.getInfoPanel().setText("");
		if (this._grid.checkArguments()) {
			this._controller.setTile(this._grid.get_blockList());
			this.get_controller().findPath(this._grid.get_startsList(),this._grid.get_FinishList());
		} else {
			JOptionPane.showMessageDialog(this,
					"You havn't entered all the parameters, please try again",
					"Missing Argumets", JOptionPane.ERROR_MESSAGE);
		}
	}	
	protected void rEndActionPerformed(ActionEvent evt) {
		this._grid.set_editMode(NewCell.SET_FINISH);
		
	}

	protected void rStartActionPerformed(ActionEvent evt) {
		this._grid.set_editMode(NewCell.SET_START);
		
	}

	protected void rBlockActionPerformed(ActionEvent evt) {
		this._grid.set_editMode(NewCell.SET_BLOCKS);	
	}


	protected void AgentComboBoxActionPerformed(ActionEvent evt) {
		int agentSelected = this._configPanel.getSettingsPanel().getcAgents().getSelectedIndex();
		this._grid.setAgentNumber(agentSelected+1);	
	}


	protected void bClearPositionsActionPerformed(ActionEvent evt) {
		this._grid.clearPositions();
		this._grid.clearFinalPath();
	}

	protected void bGeneratePositionsActionPerformed(ActionEvent evt) {
		this._grid.clearFinalPath();	
		this._grid.GeneratePositions();		
	}
	private void ChangeComboBoxSize(int numberofAgents) {
		this._configPanel.getSettingsPanel().ChangeComboBoxSize(numberofAgents);
	}
	
	protected void bClearPathActionPerformed(ActionEvent evt) {
		this._grid.clearFinalPath();
		this._grid.LoadPositions();
		
	}

	/**
	 * change the grid panel to the new grid size which is given
	 * @param gridSize
	 */
	public void ChangeGridPanel(int gridSize){		
		this.remove(_grid );
		this._grid = new GridPanel(gridSize);
		this.add(_grid , BorderLayout.CENTER);
		this._grid .revalidate();
		this._grid.addListener(new MainFrameListener());
	}
	
    
    
    /**
     * returns the GeneratePositions button
     * @return GeneratePositions button component
     */
    public JButton getbGeneratePositions(){
    	return this._configPanel.getSettingsPanel().getbGeneratePositions();
    }
    
    /**
     * returns the ClearPositions button
     * @return ClearPositions button component
     */
    public JButton getbClearPositions(){
    	return this._configPanel.getSettingsPanel().getbClearPositions();
    }
    
    public JButton getStopButton(){
    	return this._configPanel.getControlPanel().getStopButton();
    }
    /**
     * returns the findPath button
     * @return findPath button component
     */
	public JButton getbFindPath(){
    	return this._configPanel.getControlPanel().getbFindPath();
    }
	
	public JButton getbRandomMap(){ 
		return this._configPanel.getSettingsPanel().getbRandomMap();
	}
	
	public JButton getbClearMap(){ 
		return this._configPanel.getSettingsPanel().getbClearMap();
	}
	public JComboBox getcAlgorithm(){
		return this._configPanel.getSettingsPanel().getcAlgorithm();
	}
	public JComboBox getcHeuristic(){
		return this._configPanel.getSettingsPanel().getcHeuristic();
	}
	public JComboBox getcDirections(){
		return this._configPanel.getSettingsPanel().getcDirections();
	}
	public JSlider getsNumOfAgents(){
		return this._configPanel.getSettingsPanel().getsNumOfAgents();
	}
	public JSlider getsGridSize(){
		return this._configPanel.getSettingsPanel().getsGridSize();
	}
	public JButton getbClearPath(){ 
		return this._configPanel.getControlPanel().getbClearPath();
	}
	public JButton getStepButton(){
		return this._configPanel.getControlPanel().getStepButton();
	}
    /**
     * returns the set start radio button
     * @return setStart radioButton component
     */
	public JRadioButton getSetStart(){
    	return this._configPanel.getSettingsPanel().getRStart();
    }
	
	/**
     * returns the set finish radio button
     * @return setFinish radioButton component
     */
	public JRadioButton getSetFinish(){
    	return this._configPanel.getSettingsPanel().getRFinish();
    }
	
	/**
     * returns the set block radio button
     * @return setblock radioButton component
     */
	public JRadioButton getSetBlock(){
    	return this._configPanel.getSettingsPanel().getRBlock();
    }
    
	 /**
     * returns the Agent comboBox 
     * @return Agent comboBox   component
     */
    public JComboBox getAgentComboBox(){
    	return this._configPanel.getSettingsPanel().getcAgents();
    }
    
    /**
     * returns the String from the combobox where the user select the algorithm
     * @return Algorithm name
     */
    public String getAlgorithmChosen(){
    	return this._algorithmChosen;
    }
    /**
     * returns the String from the combobox where the user select the heuristic
     * @return heuristic name
     */
    public String getHeuristicChosen(){
    	return this._heuristicChosen;
    }
    
    /**
	 * returns the direction for the algorithm 
	 * if 8D is chosen it will returns true, 
	 * if 8D is chosen it will returns false,
	 * @return true if 8D is chosen
	 */
    public boolean get_directionChosen(){ // true = 8D, false = 4D(){
    	return this._directionChosen;
    }
    /**
	 * return the number of agents
	 * @return number of agents
	 */
    public int getNumberOfAgents(){ 
    	return this._numberOfAgents;
    }
    /**
	 * return the grid size
	 * @return grid size
	 */
    public int getGridSize(){ 
    	return this._gridSize;
    }
    
 

    protected class MainFrameListener implements ApplicationEventListener {

		@Override
		public void handle(ApplicationEvent event) {
			if (event instanceof SetBlockCellEvent) {
				SetBlockCellEvent blockEvent = (SetBlockCellEvent)event;
				mainPanel.this._grid.setBlockCell(blockEvent.getPosition());
				mainPanel.this._controller.setTile(blockEvent.getPosition());
			}
			if (event instanceof SetStartCellEvent) {
				SetStartCellEvent startEvent = (SetStartCellEvent)event;
				mainPanel.this._grid.setStartCell(startEvent.getPosition(),startEvent.getAgent());
			}
			if (event instanceof SetFinishCellEvent) {
				SetFinishCellEvent finishEvent = (SetFinishCellEvent)event;
				mainPanel.this._grid.setFinishCell(finishEvent.getPosition(),finishEvent.getAgent());
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
   
}

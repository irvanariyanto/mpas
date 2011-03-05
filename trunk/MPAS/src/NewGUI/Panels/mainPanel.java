package NewGUI.Panels;

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

import algorithms.myPoint;


import Controller.GridController;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.SetBlockCellEvent;
import EventMechanism.Events.SetFinishCellEvent;
import EventMechanism.Events.SetStartCellEvent;
import EventMechanism.Events.finalPathEvent;


public class mainPanel extends JPanel implements ApplicationEventSource{

	private static final long serialVersionUID = 1L;
	
	// Variables declaration
	private String _algorithmChosen;
	private String _heuristicChosen;
	private boolean _directionChosen; // 1 = 8D, 0 = 4D
	private int _numberOfAgents;
	private int _gridSize ;
	
	
	private ApplicationEventListenerCollection _listeners; 
	private GridPanel _grid;
	private ConfigurationPanel _configPanel;
	private GridController _controller;
	// End of variables declaration
	
	
	/**
	 * Constructor
	 */
	public mainPanel() {
		super();
		initComponenets();
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
		_grid = new GridPanel(15);	
		_gridSize = 15;
		_configPanel = new ConfigurationPanel();
		_controller = new GridController();
		init_controller();	
		this._controller.addListener(new ApplicationEventListener() {
			
			@Override
			public void handle(ApplicationEvent event) {
				if (event instanceof finalPathEvent){
					Vector<Vector<myPoint>> path = mainPanel.this._controller.getFinalPath();
					mainPanel.this._grid.drawFinalPaths(path);
				}
				
			}
		});
		this._listeners = new ApplicationEventListenerCollection();
		this.setLayout(new BorderLayout(2,2));
		this.add(_configPanel , BorderLayout.WEST);
		this.add(_grid , BorderLayout.CENTER);
		getbApply().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 bApplyActionPerformed(evt);
	         }
	     });
		getbCancel().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 bCancelActionPerformed(evt);
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
		this._grid.addListener(new MainFrameListener());
		this._grid.setAgentNumber(1);//Default			
	}	
	
	
	
	public GridController get_controller() {
		return this._controller;
	}

	/**
	 * when the apply button is pressed
	 * @param evt
	 */
	protected void bApplyActionPerformed(ActionEvent evt) {
		_algorithmChosen = this._configPanel.getSettingsPanel().getAlgorithm();
		_heuristicChosen = this._configPanel.getSettingsPanel().getHeuristic();
		_directionChosen = this._configPanel.getSettingsPanel().getDirection();
		_numberOfAgents = this._configPanel.getSettingsPanel().getNumberOfAgents();		
		_gridSize = this._configPanel.getSettingsPanel().getGridSize();
		getSetBlock().setSelected(true);
		this._grid.set_editMode(NewCell.SET_BLOCKS);
		this._grid.setNUM_OF_AGENT(_numberOfAgents);
		this._grid.setAgentNumber(1);	
		this._grid.clearBlocks();
		this._grid.clearPositions();
		ChangeGridPanel(_gridSize);	
		ChangeComboBoxSize(_numberOfAgents);
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);	
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
	
	protected void bCancelActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub	
	}
	
	protected void bClearMapActionPerformed(ActionEvent evt) {
		this._grid.clearBlocks();
		//this._controller.clearMap();
		
	}
	protected void bRandomMapActionPerformed(ActionEvent evt) {
		this._grid.createRandomBlocks(this._configPanel.getSettingsPanel().getsDensityValue());
		
	}
	
	protected void bFindPathActionPerformed(ActionEvent evt) {
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
		int agentSelected = this._configPanel.getSettingsPanel().get_cAgents().getSelectedIndex();
		this._grid.setAgentNumber(agentSelected+1);	
	}


	protected void bClearPositionsActionPerformed(ActionEvent evt) {
		this._grid.clearPositions();	
	}

	protected void bGeneratePositionsActionPerformed(ActionEvent evt) {
		this._grid.GeneratePositions();		
	}
	private void ChangeComboBoxSize(int numberofAgents) {
		this._configPanel.getSettingsPanel().ChangeComboBoxSize(numberofAgents);
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
     * returns the Apply button
     * @return Apply button component
     */
	public JButton getbApply(){
    	return this._configPanel.getSettingsPanel().getbApply();
    }
	/**
     * returns the Cancel button
     * @return Cancel button component
     */
    public JButton getbCancel(){
    	return this._configPanel.getSettingsPanel().getbCancel();
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
    	return this._configPanel.getSettingsPanel().get_cAgents();
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

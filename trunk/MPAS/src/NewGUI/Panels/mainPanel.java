package NewGUI.Panels;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.Events.SetBlockCellEvent;
import EventMechanism.Events.SetFinishCellEvent;
import EventMechanism.Events.SetStartCellEvent;


public class mainPanel extends JPanel {

	//private fields
	private String _algorithmChosen;
	private String _heuristicChosen;
	private boolean _directionChosen; // 1 = 8D, 0 = 4D
	private int _numberOfAgents;
	private int _gridSize;
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private GridPanel _grid;
	private ConfigurationPanel _configPanel;	
	// End of variables declaration
	

	/**
	 * Constructor
	 */
	public mainPanel() {
		super();
		initComponenets();
	}

	/**
	 * initialize all the swing Components
	 */
	private void initComponenets() {
		_grid = new GridPanel(20);		
		_configPanel = new ConfigurationPanel();
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
            	ClearPositionsActionPerformed(evt);
            }
        });	
		this._grid.addListener(new MainFrameListener());
		this._grid.setAgentNumber(1);	
	}	
	


	

	/**
	 * when the apply button is pressed
	 * @param evt
	 */
	protected void bApplyActionPerformed(ActionEvent evt) {
		_algorithmChosen = this._configPanel.getSettingPanel().getAlgorithm();
		_heuristicChosen = this._configPanel.getSettingPanel().getHeuristic();
		_directionChosen = this._configPanel.getSettingPanel().getDirection();
		_numberOfAgents = this._configPanel.getSettingPanel().getNumberOfAgents();		
		_gridSize = this._configPanel.getSettingPanel().getGridSize();
		getSetBlock().setSelected(true);
		this._grid.set_editMode(NewCell.SET_BLOCKS);
		this._grid.setNUM_OF_AGENT(_numberOfAgents);
		this._grid.setAgentNumber(1);	
		ChangeGridPanel(_gridSize);	
		ChangeComboBoxSize(_numberOfAgents);
	}
	
	protected void bCancelActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub	
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
		int agentSelected = this._configPanel.getSettingPanel().get_cAgents().getSelectedIndex();
		this._grid.setAgentNumber(agentSelected+1);	
	}


	protected void ClearPositionsActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void bGeneratePositionsActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}
	private void ChangeComboBoxSize(int numberofAgents) {
		this._configPanel.getSettingPanel().ChangeComboBoxSize(numberofAgents);
	}

	/**
	 * change the grid panel to the new grid size which is given
	 * @param gridSize
	 */
	private void ChangeGridPanel(int gridSize){		
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
    	return this._configPanel.getSettingPanel().getbApply();
    }
	/**
     * returns the Cancel button
     * @return Cancel button component
     */
    public JButton getbCancel(){
    	return this._configPanel.getSettingPanel().getbCancel();
    }
    
    /**
     * returns the GeneratePositions button
     * @return GeneratePositions button component
     */
    public JButton getbGeneratePositions(){
    	return this._configPanel.getSettingPanel().getbGeneratePositions();
    }
    
    /**
     * returns the ClearPositions button
     * @return ClearPositions button component
     */
    public JButton getbClearPositions(){
    	return this._configPanel.getSettingPanel().getbClearPositions();
    }
    /**
     * returns the set start radio button
     * @return setStart radioButton component
     */
	public JRadioButton getSetStart(){
    	return this._configPanel.getSettingPanel().getRStart();
    }
	
	/**
     * returns the set finish radio button
     * @return setFinish radioButton component
     */
	public JRadioButton getSetFinish(){
    	return this._configPanel.getSettingPanel().getRFinish();
    }
	
	/**
     * returns the set block radio button
     * @return setblock radioButton component
     */
	public JRadioButton getSetBlock(){
    	return this._configPanel.getSettingPanel().getRBlock();
    }
    
	 /**
     * returns the Agent comboBox 
     * @return Agent comboBox   component
     */
    public JComboBox getAgentComboBox(){
    	return this._configPanel.getSettingPanel().get_cAgents();
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
   
}

package NewGUI.Panels;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


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
	    		 _bApplyActionPerformed(evt);
	         }
	     });
		getbCanel().addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _bCancelActionPerformed(evt);
	    	 }
	     });
		_grid.setStartCell(0,0,2);
		_grid.setEndCell(0,3,2);
	}	
	
	protected void _bCancelActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub	
	}

	/**
	 * when the apply button is pressed
	 * @param evt
	 */
	protected void _bApplyActionPerformed(ActionEvent evt) {
		_algorithmChosen = this._configPanel.getSettingPanel().getAlgorithm();
		_heuristicChosen = this._configPanel.getSettingPanel().getHeuristic();
		_directionChosen = this._configPanel.getSettingPanel().getDirection();
		_numberOfAgents = this._configPanel.getSettingPanel().getNumberOfAgents();		
		_gridSize = this._configPanel.getSettingPanel().getGridSize();
		ChangeGridPanel(_gridSize);	
		ChangeComboBoxSize(_numberOfAgents);
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
    public JButton getbCanel(){
    	return this._configPanel.getSettingPanel().getbCancel();
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

	
   
}

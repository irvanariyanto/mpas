package NewGUI.Panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class mainPanel extends JPanel{

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
	

	public mainPanel() {
		initComponenets();
	}

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
		
	}	
	
	protected void _bCancelActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void _bApplyActionPerformed(ActionEvent evt) {
		_algorithmChosen = this._configPanel.getAlgorithm();
		_heuristicChosen = this._configPanel.getHeuristic();
		_directionChosen = this._configPanel.getDirection();
		_numberOfAgents = this._configPanel.getNumberOfAgents();		
		_gridSize = this._configPanel.getGridSize();
		ChangeGridPanel(_gridSize);
		
	}
	
	private void ChangeGridPanel(int gridSize){		
		this.remove(_grid );
		this._grid = new GridPanel(gridSize);
		this.add(_grid , BorderLayout.CENTER);
		this._grid .revalidate();
	}
	public JButton getbApply(){
    	return this._configPanel.getbApply();
    }
    
    public JButton getbCanel(){
    	return this._configPanel.getbCanel();
    }
    
    public String getAlgorithmChosen(){
    	return this._algorithmChosen;
    }
    public String getHeuristicChosen(){
    	return this._heuristicChosen;
    }
    
    public boolean get_directionChosen(){ // true = 8D, false = 4D(){
    	return this._directionChosen;
    }
    
    public int getNumberOfAgents(){ 
    	return this._numberOfAgents;
    }
    public int getGridSize(){ 
    	return this._gridSize;
    }
}

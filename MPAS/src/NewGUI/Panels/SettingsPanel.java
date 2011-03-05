package NewGUI.Panels;



import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel  {

	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private ButtonGroup _actionGroup;
	private JButton _bApply;
	private JButton _bCancel;
	private JButton _bGeneratePositions;
	private JButton _bClearPositions;
	private JButton _bClearMap;
	private JButton _bRandomMap;
	private JComboBox _cAgents;
	private JComboBox _cAlgorithm;
	private JComboBox _cDirections;
	private JComboBox _cHeuristic;
	private JLabel _lAgents;
	private JLabel _lAlgorithm;
	private JLabel _lDirections;
	private JLabel _lGridSize;
	private JLabel _lHeuristic;
	private JLabel _lNumOfAgents;
	private JLabel _lNumber;
	private JLabel _lSize;
	private JLabel _lDensity;
    private JLabel _lDensityNumber;
	private JRadioButton _rSetBlock;
	private JRadioButton _rSetFinish;
	private JRadioButton _rSetStart;
	private JSlider _sNumOfAgents;
	private JSlider _sDensity;
	private JSlider _sGridSize;	
	private JSeparator _separator1;
	private JSeparator _separator2;
	
	// End of variables declaration
	/**
	 * Constructor
	 */
	public SettingsPanel() {
		super();
		initComponents();
		

	}

	/**
	 * initialize all the swing Components
	 */
	private void initComponents() {
		this.setBorder(BorderFactory.createTitledBorder("Settings"));
		_lAlgorithm = new JLabel("Algorithm:");
		_cAlgorithm = new JComboBox();
		_lHeuristic = new JLabel("Heuristic:");
		_cHeuristic = new JComboBox();
		_lDirections = new JLabel("Directions:");
		_cDirections = new JComboBox();
		_lNumOfAgents = new JLabel("Number of Agents:");
		_sNumOfAgents = new JSlider(1,100);
		_lNumber = new JLabel("2");
		_lGridSize = new JLabel("Grid Size :");
		_sGridSize = new JSlider(3, 100);
		_lSize = new JLabel("15 * 15");
		_bApply = new JButton("Apply");
		_bCancel = new JButton("Cancel");
		_separator1 = new JSeparator();
		_separator2 = new JSeparator();
		_lAgents = new JLabel("Agent's number:");
		_cAgents = new JComboBox();
		_rSetStart = new JRadioButton("Start");
		_rSetFinish = new JRadioButton("Finish");
		_rSetBlock = new JRadioButton("Block");
		_actionGroup = new ButtonGroup();
		_bGeneratePositions= new JButton("Random Positions");
		_bClearPositions= new JButton("Clear Positions");
		_bClearMap = new JButton("Clear Map");
		_bRandomMap = new JButton("Random Map");
		_lDensity = new JLabel("Density:");
		_sDensity = new JSlider(0,100);
		_lDensityNumber = new JLabel("20 %");

		_cAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "A-Star" }));
		_cHeuristic.setModel(new DefaultComboBoxModel(new String[] { "Manhattan " }));
		_cDirections.setModel(new DefaultComboBoxModel(new String[] {"4 Directions", "8 Directions" }));
		_cAgents.setModel(new DefaultComboBoxModel(new String[] { "1","2" }));
		_sNumOfAgents.setValue(2);
		_sGridSize.setValue(15);
		_sDensity.setValue(20);
		_actionGroup.add(_rSetBlock);
		_actionGroup.add(_rSetStart);
		_actionGroup.add(_rSetFinish);
		_rSetBlock.setSelected(true);
		_bCancel.setEnabled(false);
		
		//adding ActionListeners
		_sNumOfAgents.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    _lNumber.setText("" + value);				
			}			
		});
		
		_sGridSize.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    _lSize.setText(value + " * "+value);				
			}			
		});
		
		_sDensity.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    _lDensityNumber.setText(value + " %");				
			}			
		});
		
 	
        
		//setting the layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
	            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	                            .addComponent(_lAlgorithm)
	                            .addComponent(_lDirections)
	                            .addComponent(_lHeuristic))
	                        .addGap(18, 18, 18)
	                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
	                            .addComponent(_cAlgorithm, 0,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(_cHeuristic, 0,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(_cDirections, 0, 102, Short.MAX_VALUE)))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_lNumOfAgents)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_sNumOfAgents,  GroupLayout.PREFERRED_SIZE, 88,  GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_lNumber))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_lGridSize)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_sGridSize,  GroupLayout.PREFERRED_SIZE, 116,  GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_lSize))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_bApply)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_bCancel))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_lAgents)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_cAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_rSetStart)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_rSetFinish)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_rSetBlock))
	                    .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING, false)
	                        .addComponent(_separator1,  GroupLayout.Alignment.LEADING,  GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
	                        .addGroup(layout.createSequentialGroup()
	                            .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	                                .addGroup(layout.createSequentialGroup()
	                                    .addComponent(_lDensity)
	                                    .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                                    .addComponent(_sDensity,  GroupLayout.PREFERRED_SIZE, 78,  GroupLayout.PREFERRED_SIZE)
	                                    .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                                    .addComponent(_lDensityNumber))
	                                .addGroup(layout.createSequentialGroup()
	                                    .addComponent(_bRandomMap,  GroupLayout.PREFERRED_SIZE, 138,  GroupLayout.PREFERRED_SIZE)
	                                    .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                                    .addComponent(_bClearMap,  GroupLayout.PREFERRED_SIZE, 101,  GroupLayout.PREFERRED_SIZE)))
	                            .addGap(26, 26, 26))
	                        .addComponent(_separator2,  GroupLayout.Alignment.LEADING,  GroupLayout.PREFERRED_SIZE, 255,  GroupLayout.PREFERRED_SIZE)
	                        .addGroup( GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                            .addComponent(_bGeneratePositions,  GroupLayout.PREFERRED_SIZE, 140,  GroupLayout.PREFERRED_SIZE)
	                            .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                            .addComponent(_bClearPositions))))
	                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_lAlgorithm)
	                    .addComponent(_cAlgorithm,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_cHeuristic,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                    .addComponent(_lHeuristic))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_cDirections,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                    .addComponent(_lDirections))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(11, 11, 11)
	                        .addComponent(_lNumber))
	                    .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
	                        .addComponent(_sNumOfAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                        .addComponent(_lNumOfAgents)))
	                .addGap(10, 10, 10)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
	                    .addComponent(_lGridSize)
	                    .addComponent(_sGridSize,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                    .addComponent(_lSize))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_bApply)
	                    .addComponent(_bCancel))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(_separator1,  GroupLayout.PREFERRED_SIZE, 10,  GroupLayout.PREFERRED_SIZE)
	                .addGap(1, 1, 1)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_lAgents)
	                    .addComponent(_cAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_rSetStart)
	                    .addComponent(_rSetFinish)
	                    .addComponent(_rSetBlock))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_bGeneratePositions)
	                    .addComponent(_bClearPositions))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
	                .addComponent(_separator2,  GroupLayout.PREFERRED_SIZE, 10,  GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_lDensity)
	                        .addGap(7, 7, 7))
	                    .addComponent(_sDensity,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                    .addComponent(_lDensityNumber))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_bRandomMap)
	                    .addComponent(_bClearMap))
	                .addContainerGap())
	        );
	    }

   
    /**
     * returns the Apply button
     * @return Apply button component
     */
    public JButton getbApply(){
    	return this._bApply;
    }
    /**
     * returns the Cancel button
     * @return Cancel button component
     */
    public JButton getbCancel(){
    	return this._bCancel;
    }
    
    

    /**
     * returns the GeneratePositions button
     * @return GeneratePositions button component
     */
    public JButton getbGeneratePositions() {
    	return this._bGeneratePositions;
	}
    /**
     * returns the ClearPositions button
     * @return ClearPositions button component
     */
    public JButton getbClearPositions(){
    	return this._bClearPositions;
    }
    
    public JButton getbRandomMap() {
		return this._bRandomMap;
	}
    
    public JButton getbClearMap() {
		return this._bClearMap;
	}
    
    /**
     * returns the RadioButton block
     * @return block RadioButton component
     */
    public JRadioButton getRBlock(){
    	return this._rSetBlock;
    }
    /**
     * returns the RadioButton setStart
     * @return start RadioButton component
     */
    public JRadioButton getRStart(){
    	return this._rSetStart;
    }
    /**
     * returns the RadioButton setFinish
     * @return finish RadioButton component
     */
    public JRadioButton getRFinish(){
    	return this._rSetFinish;
    }
    
    /**
     * returns the Agent comboBox 
     * @return Agent comboBox   component
     */
    public JComboBox get_cAgents(){
    	return this._cAgents;
    }
    
    
    /**
     * returns the String from the combobox where the user select the algorithm
     * @return Algorithm name
     */
	public String getAlgorithm() {
		return this._cAlgorithm.getActionCommand();
	}

	/**
     * returns the String from the combobox where the user select the heuristic
     * @return heuristic name
     */
	public String getHeuristic() {
		return this._cHeuristic.getActionCommand();
	}

	/**
	 * returns the direction for the algorithm 
	 * if 8D is chosen it will returns true, 
	 * if 8D is chosen it will returns false,
	 * @return true if 8D is chosen
	 */
	public boolean getDirection() {
		if (this._cHeuristic.getActionCommand() == "8 Directions")
			return true;
		else
			return false;
	}
	/**
	 * return the number of agents
	 * @return number of agents
	 */
	public int getNumberOfAgents() {
		return this._sNumOfAgents.getValue();
	}
	
	/**
	 * return the agent
	 * @return agent's number
	 */
	public int getAgentSelectedNumber() {
		return this._cAgents.getSelectedIndex();		 
	}
	/**
	 * return the grid size
	 * @return grid size
	 */
	public int getGridSize() {
		return this._sGridSize.getValue();
	}
	public void setGridSizeValue(int value){
		this._sGridSize.setValue(value);
	}
	public void setNumOfAgentsValue(int numOfAgents) {
		this._sNumOfAgents.setValue(numOfAgents);
		
	}
	/**
	 * change the agent combobox according to the new number of agents
	 * @param numberofAgents
	 */
	public void ChangeComboBoxSize(int numberofAgents) {
		this._cAgents.removeAllItems();
		for(int i= 1; i <= numberofAgents; i++){
			this._cAgents.addItem(i);
		}
	}

	

	public int getsDensityValue(){
		return this._sDensity.getValue();
	}


	



	

}

package NewGUI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private JRadioButton _rSetBlock;
	private JRadioButton _rSetFinish;
	private JRadioButton _rSetStart;
	private JSlider _sNumOfAgents;
	private JSeparator _separator;
	private JSlider _sGridSize;	
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
		_lSize = new JLabel("20 * 20");
		_bApply = new JButton("Apply");
		_bCancel = new JButton("Cancel");
		_separator = new JSeparator();
		_lAgents = new JLabel("Agent's number:");
		_cAgents = new JComboBox();
		_rSetStart = new JRadioButton("Start");
		_rSetFinish = new JRadioButton("Finish");
		_rSetBlock = new JRadioButton("Block");
		_actionGroup = new ButtonGroup();
		_bGeneratePositions= new JButton("Generate Positions");
		_bClearPositions= new JButton("Clear All Positions");

		_cAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "A-Star" }));
		_cHeuristic.setModel(new DefaultComboBoxModel(new String[] { "Manhattan " }));
		_cDirections.setModel(new DefaultComboBoxModel(new String[] {"4 Directions", "8 Directions" }));
		_cAgents.setModel(new DefaultComboBoxModel(new String[] { "1","2" }));
		_sNumOfAgents.setValue(2);
		_sGridSize.setValue(20);
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
		
 	
        
		//setting the layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
	            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
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
	                        .addContainerGap()
	                        .addComponent(_rSetStart)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_rSetFinish)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_rSetBlock))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(_lAgents)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_cAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
	                            .addGroup( GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                .addContainerGap()
	                                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
	                                    .addGroup(layout.createSequentialGroup()
	                                        .addComponent(_lNumOfAgents)
	                                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                                        .addComponent(_sNumOfAgents,  GroupLayout.PREFERRED_SIZE, 88,  GroupLayout.PREFERRED_SIZE))
	                                    .addGroup(layout.createSequentialGroup()
	                                        .addComponent(_lGridSize)
	                                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                                        .addComponent(_sGridSize, 0, 0, Short.MAX_VALUE))))
	                            .addGroup( GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
	                                .addGap(45, 45, 45)
	                                .addComponent(_bApply)
	                                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                                .addComponent(_bCancel)))
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	                            .addComponent(_lSize)
	                            .addComponent(_lNumber)))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING, false)
	                            .addComponent(_bClearPositions,  GroupLayout.Alignment.LEADING,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(_bGeneratePositions,  GroupLayout.Alignment.LEADING,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
	                .addContainerGap())
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(_separator,  GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
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
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
	                            .addComponent(_sNumOfAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                            .addComponent(_lNumOfAgents)))
	                    .addComponent(_lNumber))
	                .addGap(17, 17, 17)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.TRAILING)
	                    .addComponent(_lGridSize)
	                    .addComponent(_sGridSize,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
	                    .addComponent(_lSize))
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_bApply)
	                    .addComponent(_bCancel))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(_separator,  GroupLayout.PREFERRED_SIZE, 10,  GroupLayout.PREFERRED_SIZE)
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
	                .addComponent(_bGeneratePositions)
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(_bClearPositions)
	                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	    }                               
	
	


    private void _bGeneratePositionsActionPerformed( ActionEvent evt) {
        // TODO add your handling code here:
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

	



	

}

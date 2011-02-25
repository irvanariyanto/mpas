package NewGUI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private ButtonGroup _actionGroup;
	private JButton _bApply;
	private JButton _bCancel;
	private JButton _bGeneratePositions;
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
	private JRadioButton _rBlock;
	private JRadioButton _rEnd;
	private JRadioButton _rStart;
	private JSlider _sNumOfAgents;
	private JSeparator _separator;
	private JTextField _tGridSize;
	// End of variables declaration

	public SettingsPanel() {
		initComponents();

	}

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
		_tGridSize = new JTextField("", 3);
		_bApply = new JButton("Apply");
		_bCancel = new JButton("Cancel");
		_separator = new JSeparator();
		_lAgents = new JLabel("Agent's number:");
		_cAgents = new JComboBox();
		_rStart = new JRadioButton("Start");
		_rEnd = new JRadioButton("End");
		_rBlock = new JRadioButton("Block");
		_actionGroup = new ButtonGroup();
		_bGeneratePositions= new JButton("Generate Positions");
		
		

		_cAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "A-Star" }));
		_cHeuristic.setModel(new DefaultComboBoxModel(new String[] { "Manhattan " }));
		_cDirections.setModel(new DefaultComboBoxModel(new String[] {"4 Directions", "8 Directions" }));
		_cAgents.setModel(new DefaultComboBoxModel(new String[] { "1","2", "3", "4" }));
		_sNumOfAgents.setValue(2);
		_actionGroup.add(_rBlock);
		_actionGroup.add(_rStart);
		_actionGroup.add(_rEnd);
		
		//adding ActionListeners

		_sNumOfAgents.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    _lNumber.setText("" + value);
				
			}
			
		});
				 
	    
	     
       
        _rStart.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                _rStartActionPerformed(evt);
            }
        });
        _rEnd.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                _rEndActionPerformed(evt);
            }
        });
        _rBlock.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                _rBlockActionPerformed(evt);
            }
        });		 
        _bGeneratePositions.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                _bGeneratePositionsActionPerformed(evt);
            }
        });		

		

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
	                        .addComponent(_lNumOfAgents)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_sNumOfAgents,  GroupLayout.PREFERRED_SIZE, 88,  GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_lNumber))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(_rStart)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_rEnd)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_rBlock))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(_lAgents)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_cAgents,  GroupLayout.PREFERRED_SIZE, 33,  GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(_bGeneratePositions))
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(_lGridSize)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(_tGridSize,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(45, 45, 45)
	                        .addComponent(_bApply)
	                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(_bCancel)))
	                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(_separator,  GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
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
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
	                    .addComponent(_lGridSize)
	                    .addComponent(_tGridSize,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
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
	                    .addComponent(_rStart)
	                    .addComponent(_rEnd)
	                    .addComponent(_rBlock))
	                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(_bGeneratePositions)
	                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
		}
		                                 
	
	private void _rStartActionPerformed( ActionEvent evt) {
        // TODO add your handling code here:
    }
    private void _rEndActionPerformed( ActionEvent evt) {                                     
        // TODO add your handling code here:
    }
    private void _rBlockActionPerformed( ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void _bGeneratePositionsActionPerformed( ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    public JButton getbApply(){
    	return this._bApply;
    }
    
    public JButton getbCanel(){
    	return this._bCancel;
    }

	public String getAlgorithm() {
		return this._cAlgorithm.getActionCommand();
	}

	public String getHeuristic() {
		return this._cHeuristic.getActionCommand();
	}

	public boolean getDirection() {
		if (this._cHeuristic.getActionCommand() == "8 Directions")
			return true;
		else
			return false;
	}
	
	public int getNumberOfAgents() {
		return this._sNumOfAgents.getValue();
	}
	
	public int getGridSize() {
		String size = this._tGridSize.getText();
		return  Integer.parseInt(size);
	}
	

}

package GUI.Panels;


import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GUIController;

public class InfoPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	// Variables declaration 
    private JLabel _lFinalPathCost;
    private JLabel _lCost;
    private Font _font;
    private JPanel _mainPanel;
    //private JScrollPane _scrollPane;
    //private JTextArea _tOpenList;
    private GUIController _guiController;
    // End of variables declaration
    
    /**
	 * Constructor
     * @param controller 
	 */
	public InfoPanel(GUIController controller) {
		super();
		this._guiController=controller;
		initComponents();
	}

	/**
	 * initialize all the swing Components
	 */
	private void initComponents() {
		_mainPanel = new JPanel();	
		_lFinalPathCost = new JLabel("Final Cost:");
		_lCost = new JLabel("");
		_font = new Font("Serif", Font.PLAIN, 30);
		_lFinalPathCost.setFont(_font);
		_lCost.setFont(_font);
		_mainPanel.add(_lFinalPathCost);
		_mainPanel.add(_lCost);
		_mainPanel.setBorder(BorderFactory.createTitledBorder("Information"));
		this.add(_mainPanel);
	}
	
	public void setFinalCost(float finalCost){
		_lCost.setText(Float.toString(finalCost));
	}
	
	public void clearFinalCost(){
		_lCost.setText("");
	}
	
	
}

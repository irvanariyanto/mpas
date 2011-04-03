package GUI.Panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import GUI.GUIController;

public class InfoPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	// Variables declaration 
    private javax.swing.JLabel _lOpenList;
    private javax.swing.JScrollPane _scrollPane;
    private javax.swing.JTextArea _tOpenList;
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
		this.setBorder(BorderFactory.createTitledBorder("Information"));
		_scrollPane = new JScrollPane();
		_tOpenList = new JTextArea(5,20);
		_tOpenList.setEditable(false);
	    _lOpenList = new JLabel("Open List:");
	    _scrollPane.setViewportView(_tOpenList);
	    this.add(_lOpenList);
	    this.add(_scrollPane);

	}
	
	public void writeToTextArea(String text){
		String tstr = this._tOpenList.getText();
		this._tOpenList.setText(text +"\n"+ tstr);
	}

	public void setText(String string) {
		this._tOpenList.setText(string);
		
	}
}

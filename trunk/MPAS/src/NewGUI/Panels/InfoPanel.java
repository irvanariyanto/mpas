package NewGUI.Panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	// Variables declaration 
    private javax.swing.JLabel _lOpenList;
    private javax.swing.JScrollPane _scrollPane;
    private javax.swing.JTextArea _tOpenList;
    // End of variables declaration
    
    /**
	 * Constructor
	 */
	public InfoPanel() {
		super();
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
}

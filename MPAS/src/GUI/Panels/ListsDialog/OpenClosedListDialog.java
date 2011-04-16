package GUI.Panels.ListsDialog;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class OpenClosedListDialog  extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel _mainPanel;
	private JLabel _lcurrentState;
	private JLabel _lOpenList;
	private JLabel _lClosedList;
	private JTable _currentState;
	private JTable _openListtable;
	private JTable _closedListtable;
	private JScrollPane _openListScrollPane;
	private JScrollPane _closedListScrollPane;
	private JScrollPane _currentStateScrollPane;
	
	public OpenClosedListDialog(JFrame parent){
		super(parent);
		this.setTitle("Open and Closed Lists");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600,300));
        _mainPanel = new JPanel(new GridLayout(0,1));
        _lcurrentState = new JLabel("Current State");
        _lOpenList = new JLabel("Open List");
        _lClosedList = new JLabel("Closed List");
        
        _openListtable = new JTable(new OpenListTableModel());
        _openListtable.setPreferredScrollableViewportSize(new Dimension(500, 100));
        _openListtable.setFillsViewportHeight(true);
        _openListScrollPane = new JScrollPane(_openListtable);
        
        _closedListtable = new JTable(new ClosedListTableModel());
        _closedListtable.setPreferredScrollableViewportSize(new Dimension(500, 100));
        _closedListtable.setFillsViewportHeight(true);
        _closedListScrollPane = new JScrollPane(_closedListtable);
        
        _currentState = new JTable(new CurrentStateTableModel());
        _currentState.setPreferredScrollableViewportSize(new Dimension(500, 16));
       // _currentState.setFillsViewportHeight(true);
        _currentStateScrollPane = new JScrollPane(_currentState);

              

        //Add the scroll pane to this panel.
        _mainPanel.add(_lcurrentState);
        _mainPanel.add(_currentStateScrollPane);
        _mainPanel.add(_lOpenList);
        _mainPanel.add(_openListScrollPane);
        _mainPanel.add(_lClosedList);
        _mainPanel.add(_closedListScrollPane);
        this.add(_mainPanel);
	}

}

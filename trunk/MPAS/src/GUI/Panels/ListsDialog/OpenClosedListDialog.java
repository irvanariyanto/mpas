package GUI.Panels.ListsDialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import algorithms.myPoint;
import algorithms.Interfaces.StateInterface;

public class OpenClosedListDialog  extends JDialog {

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
	private JButton _bClear;
	private String columnsHeaders[] = {"State Number","Coordinates","g","h","f"};
	private String currentStateHeaders[] = {"Coordinates","g","h","f"};
	DefaultTableModel _currentStatemodel; 
	DefaultTableModel _OpenListmodel; 
	DefaultTableModel _closedListmodel; 
	
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
        _bClear = new JButton("Clear");
        
        _OpenListmodel = new DefaultTableModel(null,columnsHeaders){
        	private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        };  
        _openListtable = new JTable(_OpenListmodel);
        _openListtable.setPreferredScrollableViewportSize(new Dimension(500, 100));
        _openListtable.setFillsViewportHeight(true);
        _openListScrollPane = new JScrollPane(_openListtable);

        _closedListmodel = new DefaultTableModel(null,columnsHeaders){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        }; 
        _closedListtable = new JTable(_closedListmodel);
        _closedListtable.setPreferredScrollableViewportSize(new Dimension(500, 100));
        _closedListtable.setFillsViewportHeight(true);
        _closedListScrollPane = new JScrollPane(_closedListtable);
        
        _currentStatemodel = new DefaultTableModel(null,currentStateHeaders){
        	private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        };
        _currentState = new JTable(_currentStatemodel);
        _currentState.setPreferredScrollableViewportSize(new Dimension(500, 25));
        _currentState.setFillsViewportHeight(true);
        _currentStateScrollPane = new JScrollPane(_currentState);

              

        //Add the scroll pane to this panel.
        _mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
        _lcurrentState.setAlignmentX(Component.LEFT_ALIGNMENT);
        _currentStateScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _lOpenList.setAlignmentX(Component.LEFT_ALIGNMENT);
        _openListScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _lClosedList.setAlignmentX(Component.LEFT_ALIGNMENT);
        _closedListScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _bClear.setAlignmentX(Component.LEFT_ALIGNMENT);
        _mainPanel.add(_lcurrentState);
        _mainPanel.add(_currentStateScrollPane);
        _mainPanel.add(_lOpenList);
        _mainPanel.add(_openListScrollPane);
        _mainPanel.add(_lClosedList);
        _mainPanel.add(_closedListScrollPane);
        _mainPanel.add(_bClear);
        this.add(_mainPanel);
        _bClear.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 ClearTables();
	         }
	     });
	}

	

	protected void ClearTables() {
		//clear the current state table model
		_currentStatemodel.removeRow(0);
		//clear the open List table model
		int openListSize = _OpenListmodel.getRowCount();
		for(int i=0; i<openListSize;i++){
			_OpenListmodel.removeRow(0);
		}
		int ClosedListSize = _closedListmodel.getRowCount();
		for(int i=0; i<ClosedListSize;i++){
			_closedListmodel.removeRow(0);
		}
		

	}



	public void addToOpenList(Vector<myPoint> coor, float g, float h, float f){
		_OpenListmodel.insertRow(0,new Object[]{_OpenListmodel.getRowCount(),coor,g,h,f});
	}
	
	public void addToClosedList(Vector<myPoint> coor, float g, float h, float f){
		_closedListmodel.insertRow(0,new Object[]{_closedListmodel.getRowCount(),coor,g,h,f});
	}
	
	public void currentStateChange(Vector<myPoint> coor, float g, float h, float f){
		if (_currentStatemodel.getDataVector().isEmpty()){
			_currentStatemodel.insertRow(0,new Object[]{coor,g,h,f});
		}
		else{
			_currentStatemodel.removeRow(0);
			_currentStatemodel.insertRow(0,new Object[]{coor,g,h,f});			
		}
			
		
	}
	

}

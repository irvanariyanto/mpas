package GUI.Panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import javax.swing.table.TableRowSorter;

import algorithms.myPoint;
import algorithms.Astar.myState;
import algorithms.Interfaces.StateInterface;

public class OpenClosedListDialog  extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel _mainPanel;
	private JTable _currentState;
	private JTable _openListtable;
	private JTable _closedListtable;
	private JScrollPane _openListScrollPane;
	private JScrollPane _closedListScrollPane;
	private JScrollPane _currentStateScrollPane;
	private String columnsHeaders[] = {"State Number","Coordinates","g","h","f"};
	private String currentStateHeaders[] = {"Coordinates","g","h","f"};
	private DefaultTableModel _currentStatemodel; 
	private DefaultTableModel _OpenListmodel; 
	private DefaultTableModel _closedListmodel; 
	private int openListCounter ,closedListCounter;
	
	public OpenClosedListDialog(JFrame parent){
		super(parent);
		this.setTitle("Open and Closed Lists");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(new Dimension(600,300));
        _mainPanel = new JPanel(new GridLayout(0,1));
        openListCounter = 0;
        closedListCounter = 0;
        
        _OpenListmodel = new DefaultTableModel(null,columnsHeaders){
        	private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        };  
        _openListtable = new JTable(_OpenListmodel);        
        _openListtable.setPreferredScrollableViewportSize(new Dimension(500, 100));
        _openListtable.setFillsViewportHeight(true);
        _openListtable.setAutoCreateRowSorter(true);
        _openListScrollPane = new JScrollPane(_openListtable);
        _openListScrollPane.setBorder(BorderFactory.createTitledBorder("Open List"));

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
        _closedListScrollPane.setBorder(BorderFactory.createTitledBorder("Closed List"));
        
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
        _currentStateScrollPane.setBorder(BorderFactory.createTitledBorder("Current State"));

              

        //Add the scroll pane to this panel.
        _mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
        _currentStateScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _openListScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _closedListScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        _mainPanel.add(_currentStateScrollPane);
        _mainPanel.add(_openListScrollPane);
        _mainPanel.add(_closedListScrollPane);
        this.add(_mainPanel);
	}

	

	public void ClearTables() {
		//clear the current state table model
		if(!_currentStatemodel.getDataVector().isEmpty()){
			_currentStatemodel.removeRow(0);
			_currentStatemodel.fireTableDataChanged();
		}
		//clear the open List table model
		int openListSize = _OpenListmodel.getRowCount();
		for(int i=0; i<openListSize;i++){
			_OpenListmodel.removeRow(0);
			_OpenListmodel.fireTableDataChanged();
		}
		int ClosedListSize = _closedListmodel.getRowCount();
		for(int i=0; i<ClosedListSize;i++){
			_closedListmodel.removeRow(0);
			_closedListmodel.fireTableDataChanged();
		}
		closedListCounter = 0;
		openListCounter = 0;
		

	}



	/**
	 * add the parameters into the table
	 * @param coor
	 * @param g
	 * @param h
	 * @param f
	 */
	public void addToOpenList(Vector<myPoint> coor, float g, float h, float f){
		_OpenListmodel.insertRow(0,new Object[]{openListCounter,coor,g,h,f});
		_OpenListmodel.fireTableDataChanged();
		openListCounter++;
	}
	
	/**
	 * add the parameters into the table
	 * @param coor
	 * @param g
	 * @param h
	 * @param f
	 */
	public void addToClosedList(Vector<myPoint> coor, float g, float h, float f){
		_closedListmodel.insertRow(0,new Object[]{closedListCounter,coor,g,h,f});
		_closedListmodel.fireTableDataChanged();
		closedListCounter++;
	}
	
	/**
	 * add the parameters into the table
	 * @param coor
	 * @param g
	 * @param h
	 * @param f
	 */
	public void currentStateChange(Vector<myPoint> coor, float g, float h, float f){
		if (_currentStatemodel.getDataVector().isEmpty()){
			_currentStatemodel.insertRow(0,new Object[]{coor,g,h,f});
			
		}
		else{
			_currentStatemodel.removeRow(0);
			_currentStatemodel.insertRow(0,new Object[]{coor,g,h,f});			
		}
		_currentStatemodel.fireTableDataChanged();
	}



	/**
	 * remove from the table
	 * @param get_Coordinates
	 * @param get_cost
	 * @param get_heuristic
	 * @param get_f
	 */
	public void removeFromOpenList(Vector<myPoint> coordinates,
			float g, float h, float f) {
		for (int row = 0; row< _OpenListmodel.getRowCount();row++){
			@SuppressWarnings("unchecked")
			Vector<myPoint> tCoordinates = (Vector<myPoint>) _OpenListmodel.getValueAt(row, 1);
			if(tCoordinates.equals(coordinates)){
				_OpenListmodel.removeRow(row);
				_OpenListmodel.fireTableDataChanged();
				//System.out.println("remove cordinate" + tCoordinates.toString() + "row" + row);
				break;
			}
			
		}
		
	}
	

}



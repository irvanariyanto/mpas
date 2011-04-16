package GUI.Panels.ListsDialog;

import javax.swing.table.AbstractTableModel;

public class ClosedListTableModel extends AbstractTableModel {
	private String[] columnNames = {"State Number","Coordinates" ,"g","h","f"};
	private Object[][] data = {
		    {"", "","", "",""}};

	public ClosedListTableModel(){
	
	}
	@Override
	public int getColumnCount() {
		 return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public String getColumnName(int col) {
        return columnNames[col].toString();
    }
	
	 public boolean isCellEditable(int row, int col){ 
		 return false; 
	 }
	 
	 public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	 public void setValueAt(Object value, int row, int col) {
		 data[row][col] = value;
	     fireTableCellUpdated(row, col);
	 }
 

}
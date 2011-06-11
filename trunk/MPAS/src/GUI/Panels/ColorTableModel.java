package GUI.Panels;

import java.awt.Color;

import javax.swing.table.AbstractTableModel;

import GUI.Utils.ColorManager;

public class ColorTableModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2213926686479567499L;
	private String[] columnNames = {"Element",
                                    "Color"};
    private Object[][] data;

    //constructor for the model, reads the color configuration from the colors.xml file
    public ColorTableModel(){
    	data = ColorManager.createDataModelFromFile();
    }
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {

        data[row][col] = value;
        if (col == 1){
            ColorManager.getInstance().setColor((String)data[row][0], (Color)value);
        }

        fireTableCellUpdated(row, col);

    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}

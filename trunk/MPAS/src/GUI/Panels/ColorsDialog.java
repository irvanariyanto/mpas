package GUI.Panels;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is like TableDemo, except that it substitutes a
 * Favorite Color column for the Last Name column and specifies
 * a custom cell renderer and editor for the color data.
 */
public class ColorsDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel _mainPanel;
    public ColorsDialog(JFrame frame) {
    	super(frame);
    	
        _mainPanel = new JPanel(new GridLayout(1,0));
        JTable table = new JTable(new ColorTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up renderer and editor for the Favorite Color column.
        table.setDefaultRenderer(Color.class,
                                 new ColorRenderer(true));
        table.setDefaultEditor(Color.class,
                               new ColorEditor());

        //Add the scroll pane to this panel.
        _mainPanel.add(scrollPane);
        this.setTitle("Colors Editor");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        Container contentPane = getContentPane();
        contentPane.add(_mainPanel);
        this.setSize(new Dimension(600,400));
    }
    
    

}

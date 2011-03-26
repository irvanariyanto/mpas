package GUI.Panels;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ColorsDialog {
	/**
	 * 
	 */
	private JDialog _root;
	private static final long serialVersionUID = 1L;
	public ColorsDialog(){
		_root = new JDialog();
		initComponents();
	}
	public ColorsDialog(JFrame frame){
		_root = new JDialog(frame,"Color Manager",true);
        initComponents();
	}
	private void initComponents() {
		_root.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _root.pack();
        _root.setVisible(true);
	}
}


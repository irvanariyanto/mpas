package GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ColorsDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane _editorScrollPane;
	private JEditorPane _editorPane;
	private JButton _clearButton;
	private JPanel _statsPanel;
	private JPanel _buttonsPane;
	public ColorsDialog(JFrame parent){
		super(parent);
		this.setTitle("Colors Editor");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.pack();
        this.setModal(true);
        this.setSize(new Dimension(600,400));
        _buttonsPane = new JPanel();

        //editor pane
        _editorPane = new JEditorPane();
        //scroll pane
        _editorScrollPane = new JScrollPane(_editorPane);
        _editorScrollPane.setPreferredSize(new Dimension(250, 100));
        _editorScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        _editorScrollPane.setVisible(true);
        //main panel
        _statsPanel = new JPanel();
        _statsPanel.setLayout(new BoxLayout(_statsPanel, BoxLayout.PAGE_AXIS));
        _statsPanel.add(Box.createRigidArea(new Dimension(0,5)));
        _statsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        _statsPanel.add(_editorScrollPane);
        //clear button
        _clearButton = new JButton();
        _clearButton.setText("Clear");
        _clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearText();
				
			}
		});
     //   _statsPanel.add(_editorScrollPane);
        _buttonsPane.add(_clearButton);
        //Put everything together, using the content pane's BorderLayout.
        Container contentPane = getContentPane();
        contentPane.add(_statsPanel, BorderLayout.CENTER);
        contentPane.add(_buttonsPane, BorderLayout.PAGE_END);
    //    this._editorScrollPane.add(_editorPane);
        _editorPane.setText("Amitdsfdsdfs dsff  dgs\nds  dssdg");
        _editorPane.setEditable(false);
        _editorPane.setVisible(true);

	}
	public synchronized void addLine(String line){
		String text = this._editorPane.getText();
		this._editorPane.setText(text + "\n" + line);
	}
	public synchronized void clearText(){
		_editorPane.setText("");
	}
}

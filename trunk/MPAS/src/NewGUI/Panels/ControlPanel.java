package NewGUI.Panels;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private  JButton _bFindPath;
	private  JButton _bStop;
	private  JButton _bStep;
	private  JButton _bClearPath;
	// End of variables declaration

	/**
	 * Constructor
	 */
	public ControlPanel() {
		super();
		initComponents();

	}

	/**
	 * initialize all the swing Components
	 */
	private void initComponents() {
		this.setBorder(BorderFactory.createTitledBorder("Control"));
		_bFindPath = new JButton("Find Path");
		_bStop = new JButton("Stop");
		_bStep = new JButton("Step");
		_bClearPath = new JButton("Clear Path");
		this.add(_bFindPath);
		this.add(_bStop);
		this.add(_bStep);
		this.add(_bClearPath);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    		  .addComponent(_bFindPath)
						      .addComponent(_bStop)
						      .addComponent(_bStep)
						      .addComponent(_bClearPath))
				);
				layout.setVerticalGroup(
				   layout.createSequentialGroup()
				      .addComponent(_bFindPath)
				      .addComponent(_bStop)
				      .addComponent(_bStep)
				      .addComponent(_bClearPath)
				);
	}


	public JButton getbFindPath() {
		return this._bFindPath;
	}
	public JButton getStepButton(){
		return this._bStep;
	}

	public JButton getbClearPath() {
		return this._bClearPath;
	}
	
}

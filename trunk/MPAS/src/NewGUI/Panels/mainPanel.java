package NewGUI.Panels;

import java.awt.BorderLayout;
import javax.swing.JPanel;


public class mainPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private GridPanel _grid;
	private ConfigurationPanel _configPanel;	
	// End of variables declaration
	

	public mainPanel() {
		initComponenets();
	}

	private void initComponenets() {
		_grid = new GridPanel(40);		
		_configPanel = new ConfigurationPanel();
		this.setLayout(new BorderLayout(2,2));
		this.add(_configPanel , BorderLayout.WEST);
		this.add(_grid , BorderLayout.CENTER);		
	}

		
}

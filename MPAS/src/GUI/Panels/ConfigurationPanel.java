package GUI.Panels;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ConfigurationPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private SettingsPanel _setPanel;
	private ControlPanel _controlPanel;
	private InfoPanel _infoPanel;
	// End of variables declaration
	
	/**
	 * Constructor
	 */
	public ConfigurationPanel() {
		super();
		initComponents();
	}

	/**
	 * initialize all the swing Components
	 */
	private void initComponents() {
		_setPanel = new SettingsPanel();
		_controlPanel = new ControlPanel();
		_infoPanel = new InfoPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_setPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		_controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		_infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(_setPanel);
		this.add(_controlPanel);
		this.add(_infoPanel);
	}
	
	public SettingsPanel getSettingsPanel(){
		return this._setPanel;
	}
	
	public ControlPanel getControlPanel(){
		return this._controlPanel;
	}
	
	public InfoPanel getInfoPanel(){
		return this._infoPanel;
	}

	
}//end of class ConfigurationPanel


package NewGUI.Panels;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ConfigurationPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private SettingsPanel _setPanel;
	private ControlPanel _controlPanel;
	private InfoPanel _infoPanel;
	// End of variables declaration
	
	public ConfigurationPanel() {
		initComponents();

	}

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
	
	public JButton getbApply(){
    	return this._setPanel.getbApply();
    }
    
    public JButton getbCanel(){
    	return this._setPanel.getbCanel();
    }

    public String getAlgorithm() {
		return this._setPanel.getAlgorithm();
	}

	public String getHeuristic() {
		return this._setPanel.getHeuristic();
	}

	public int getNumberOfAgents() {
		return this._setPanel.getNumberOfAgents();
	}

	public boolean getDirection() {
		return this._setPanel.getDirection();
	}

	public int getGridSize() {
		return this._setPanel.getGridSize();
	}

	
}//end of class ConfigurationPanel


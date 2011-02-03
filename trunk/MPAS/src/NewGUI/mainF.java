package NewGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;


public class mainF extends JFrame{

	public mainF(String title) {
		super(title);
		initComponenets();
	}

	private void initComponenets() {
		SettingsPanel setPanel = new SettingsPanel();
		this.getContentPane().add(setPanel);
		
	}
	
	
}

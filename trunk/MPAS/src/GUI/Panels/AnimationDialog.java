package GUI.Panels;

import javax.swing.JDialog;
import javax.swing.JFrame;

import GUI.GUIController;

public class AnimationDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	private AnimationPanel _animationPanel;
	private GUIController _guiController;
	
	public AnimationDialog(JFrame frame,GUIController guiController){
		super (frame);
		_guiController = guiController;
		this._animationPanel = new AnimationPanel(_guiController);
		setContentPane(_animationPanel);
		this.setTitle("Animation");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.pack();		
	}

	public int getAnimationSpeedValue() {
		return this._animationPanel.getAnimationSpeedValue();
	}
	
	public AnimationPanel getAnimationPanel() {
		return this._animationPanel;
	}

}

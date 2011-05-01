package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUIController _guiController = new GUIController();
		MainFrame main = new MainFrame("MPAS - Multi-agent Pathfinding Algorithms Simulator v1.3",_guiController);		
		_guiController.initGuiController(main);

		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(new Dimension(890,750));
		main.setResizable(true);
		main.setVisible(true);

	}

}

package NewGUI;

import java.awt.Dimension;
import javax.swing.JFrame;

public class testNewMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame2 main = new MainFrame2("MPAS - Multi-agent Pathfinding Algorithms Simulator v1.1");		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(new Dimension(890,750));
		main.setResizable(true);
		main.setVisible(true);

	}

}

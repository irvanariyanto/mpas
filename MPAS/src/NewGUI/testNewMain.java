package NewGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class testNewMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mainF main = new mainF("MPAS - Multi-agent Pathfinding Algorithms Simulator v1.1");		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(780,780);
		main.setResizable(true);
		main.setVisible(true);

	}

}

package NewGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mainF main = new mainF("MPAS");		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(700, 550);
		main.setResizable(false);
		main.setVisible(true);

	}

}

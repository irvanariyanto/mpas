package tests;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import GUI.mainFrame;


public class GridTest {
	
	public static void main(String args[]) {
		mainFrame main = new mainFrame("A* Simulator");		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getContentPane().add(main.__mainPanel, BorderLayout.CENTER);
		main.setSize(700, 550);
		main.setResizable(false);
		main.setVisible(true);
	}

}

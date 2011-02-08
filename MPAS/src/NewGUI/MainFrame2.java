package NewGUI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Controller.Controller;
import NewGUI.Panels.mainPanel;

public class MainFrame2 extends JFrame {

	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private JMenuBar _menuBar;
	private JMenu _FileMenu,_MapMenu, _helpMenu;	
	private JMenuItem _openMap, _saveMap, _exitItem;
	private mainPanel _mainPanel;
	private Controller _controller;
	// End of variables declaration
	
	
	public MainFrame2(String title) {
		super(title);
		initComponenets();
	}
	
	private void initComponenets() {
		this._menuBar = new JMenuBar();
		this._FileMenu = new JMenu("File");
		this._MapMenu = new JMenu("Grid Map");
		this._helpMenu = new JMenu("Help");
		this._mainPanel = new mainPanel();		
		_menuBar.add(_FileMenu);
		_exitItem = new JMenuItem("Exit");
		_FileMenu.add(_exitItem);

		_menuBar.add(_MapMenu);
		_openMap = new JMenuItem("Open");
		_MapMenu.add(_openMap);
		_saveMap = new JMenuItem("Save");
		_MapMenu.add(_saveMap);
		
		_menuBar.add(_helpMenu);
		
		this.setJMenuBar(_menuBar);
		this.add(_mainPanel);
		
	}
}

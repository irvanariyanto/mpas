package NewGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import GUI.Grid;


public class mainF extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridMap _grid;
	private SettingsPanel _setPanel;
	private ControlPanel _controlPanel;
	private JPanel _leftPanel;
	private JMenuBar _menuBar;
	private JMenu _FileMenu,_MapMenu, _helpMenu;	
	private JMenuItem _openMap, _saveMap, _exitItem;
	private String _action;
	private int _agentNum;

	public mainF(String title) {
		super(title);
		initComponenets();
	}

	private void initComponenets() {
		_grid = new GridMap(this,20);
		_setPanel = new SettingsPanel();
		_controlPanel = new ControlPanel();
		_leftPanel = new JPanel();
		_leftPanel.setLayout(new GridLayout(0, 1));
		_leftPanel.add(_setPanel);
		_leftPanel.add(_controlPanel);
		
		this._menuBar = new JMenuBar();
		this._FileMenu = new JMenu("File");
		this._MapMenu = new JMenu("Grid Map");
		this._helpMenu = new JMenu("Help");
		
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
		this.setLayout(new BorderLayout(5, 5));
		this.add(_leftPanel , BorderLayout.WEST);
		this.add(_grid , BorderLayout.CENTER);
		
	}

	public GridMap get_grid() {
		return this._grid;
	}

	public int getAgentNumber() {
		return this._agentNum;
	}

	public String getAction() {
		return this._action;
	}
	
	
}

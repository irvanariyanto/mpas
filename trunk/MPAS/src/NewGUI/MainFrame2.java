package NewGUI;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import maps.GridMapUtility;
import maps.TileBasedMap;

import NewGUI.Panels.mainPanel;

public class MainFrame2 extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private JMenuBar _menuBar;
	private JMenu _FileMenu,_MapMenu, _helpMenu;	
	private JMenuItem _openMap, _saveMap, _exitItem,_loadScenario,_saveScenario;
	private mainPanel _mainPanel;
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
		_openMap = new JMenuItem("Load map");
		_MapMenu.add(_openMap);
		_saveMap = new JMenuItem("Save map");
		_MapMenu.add(_saveMap);
		_loadScenario = new JMenuItem("Load scenario");
		_saveScenario = new JMenuItem("Save scenario");
		_MapMenu.add(_loadScenario);
		_MapMenu.add(_saveScenario);
		
		_menuBar.add(_helpMenu);
		
		this.setJMenuBar(_menuBar);
		this.add(_mainPanel);
		
		_openMap.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadMap();
			}
		});
		_saveMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveMap();
				
			}
		});
		_loadScenario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		_saveScenario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		

	}
	public void loadMap() {
		JFileChooser fc = new JFileChooser();
    //	fc.addChoosableFileFilter(new TextFileFilter());
    	fc.showOpenDialog(this);
    	File tMapFile = fc.getSelectedFile();
		if (tMapFile != null){
			TileBasedMap map = GridMapUtility.loadMap(tMapFile);
			this._mainPanel.ChangeGridPanel(map.getCells().length); //TODO change it later so it won't use getCells
			this._mainPanel.get_controller().setMap(map);
			this._mainPanel.getGrid().drawMap(map);
		}
	}
	public void saveMap(){
		JFileChooser fc = new JFileChooser();
	    //	fc.addChoosableFileFilter(new TextFileFilter());
	    	fc.showSaveDialog(this);
	    	File tMapFile = fc.getSelectedFile();
	  //  	this._mainPanel.get_controller().setMap(this._mainPanel.getGrid().get_height()); //TODO remove later when map is properly initialized
	    //	this._mainPanel.get_controller().setTile(this._mainPanel.getGrid().get_blockList());
	    	GridMapUtility.saveMap(tMapFile, this._mainPanel.get_controller().getMap());
	}
	

}

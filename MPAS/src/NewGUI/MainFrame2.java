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
import maps.Scenario;
import maps.TileBasedMap;

import NewGUI.Panels.mainPanel;
import NewGUI.utils.ScenarioFileFilter;

public class MainFrame2 extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private JMenuBar _menuBar;
	private JMenu _FileMenu,_MapMenu, _helpMenu,_EditMenu,_ViewMenu;	
	private JMenuItem _openMap, _saveMap, _exitItem,_loadScenario,_saveScenario,_ColorsEditor;
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
		this._EditMenu = new JMenu("Edit");
		this._ViewMenu = new JMenu("View");
		this._mainPanel = new mainPanel();		
		_menuBar.add(_FileMenu);
		_menuBar.add(_EditMenu);
		_menuBar.add(_ViewMenu);
		_exitItem = new JMenuItem("Exit");
		_FileMenu.add(_exitItem);
		_ColorsEditor = new JMenuItem("Colors Editor");
		_EditMenu.add(_ColorsEditor);
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
		_exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame2.this.dispose();
				
			}
		});
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
				loadScenario();
				
			}
		});
		_saveScenario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveScenario();
				
			}

		});
		

	}
	private void loadMap() {
		JFileChooser fc = new JFileChooser();
    	fc.addChoosableFileFilter(new ScenarioFileFilter());
    	fc.showOpenDialog(this);
    	File tMapFile = fc.getSelectedFile();
		if (tMapFile != null){
			TileBasedMap map = GridMapUtility.loadMap(tMapFile);
			this._mainPanel.ChangeGridPanel(map.getHeightInTiles()); 
			this._mainPanel.get_controller().setMap(map);
			this._mainPanel.getGrid().drawMap(map);
			this._mainPanel.getConfiguarationPanel().getSettingsPanel().setGridSizeValue(map.getHeightInTiles());
		}
	}
	private void saveMap(){
		JFileChooser fc = new JFileChooser();
	    	fc.addChoosableFileFilter(new ScenarioFileFilter());
	    	fc.showSaveDialog(this);
	    	File tMapFile = fc.getSelectedFile();
	  //  	this._mainPanel.get_controller().setMap(this._mainPanel.getGrid().get_height()); //TODO remove later when map is properly initialized
	    //	this._mainPanel.get_controller().setTile(this._mainPanel.getGrid().get_blockList());
	    	GridMapUtility.saveMap(tMapFile, this._mainPanel.get_controller().getMap());
	}
	

	private void saveScenario() {
		JFileChooser fc = new JFileChooser();
    	fc.addChoosableFileFilter(new ScenarioFileFilter());
    	fc.showSaveDialog(this);
    	File tFile = fc.getSelectedFile();
		Scenario s = new Scenario(this._mainPanel.get_controller().getMap(),this._mainPanel.getGrid().get_startsList(),this._mainPanel.getGrid().get_FinishList());
		GridMapUtility.saveSecnario(s, tFile);
	}
	private void loadScenario(){
		JFileChooser fc = new JFileChooser();
	    	fc.addChoosableFileFilter(new ScenarioFileFilter());
	    	fc.showOpenDialog(this);
	    	File tFile = fc.getSelectedFile();
	    	if (tFile != null){
	    		Scenario s = GridMapUtility.loadScenario(tFile);
				this._mainPanel.ChangeGridPanel(s.getMap().getHeightInTiles()); 
				this._mainPanel.get_controller().setMap(s.getMap());
				int numOfAgents = s.getStartLocations().size();
				this._mainPanel.get_controller().setNumberOfAgents(numOfAgents);
				this._mainPanel.getGrid().drawScenario(s);
				this._mainPanel.getConfiguarationPanel().getSettingsPanel().setGridSizeValue(s.getMap().getHeightInTiles());
				this._mainPanel.getConfiguarationPanel().getSettingsPanel().setNumOfAgentsValue(numOfAgents);
				
	    	}
	}
}

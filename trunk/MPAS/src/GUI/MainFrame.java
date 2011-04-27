package GUI;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import EventMechanism.Events.OpenListChangeEvent;
import GUI.Panels.ColorsDialog;
import GUI.Panels.StatisticsDialog;
import GUI.Panels.mainPanel;
import GUI.Panels.ListsDialog.OpenClosedListDialog;


public class MainFrame extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private JMenuBar _menuBar;
	private JMenu _FileMenu,_MapMenu, _helpMenu,_EditMenu,_ViewMenu;	
	private JMenuItem _openMap, _saveMap, _exitItem,_loadScenario,_saveScenario,_ColorsEditor;
	private JCheckBoxMenuItem _ShowStatsPanel,_ShowGridLine,_ShowPathTrace,_writeStatistics,_animation,_showListsStat,_WriteToListsStat,_withAnimatedPath;
	private mainPanel _mainPanel;
	private StatisticsDialog _statsPanel;
	private OpenClosedListDialog _ListsPanel;
	private ColorsDialog _colorsDialog;
	private GUIController _guiController;
	// End of variables declaration
	
	
	public MainFrame(String title,GUIController guiController) {
		super(title);
		this._guiController = guiController;
		initComponenets();
	}
	
	private void initComponenets() {
		this._mainPanel = new mainPanel(_guiController);
		
		this._menuBar = new JMenuBar();
		this._FileMenu = new JMenu("File");
		this._MapMenu = new JMenu("Grid Map");
		this._helpMenu = new JMenu("Help");
		this._EditMenu = new JMenu("Edit");
		this._ViewMenu = new JMenu("View");	
		this._statsPanel = new StatisticsDialog(this);
		this._statsPanel.setVisible(false);
		this._ListsPanel = new OpenClosedListDialog(this);
		this._ListsPanel.setVisible(false);
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
		_ShowStatsPanel = new JCheckBoxMenuItem("Statistics panel");
		_ShowGridLine = new JCheckBoxMenuItem("Show Grid Line");
		_ShowPathTrace = new JCheckBoxMenuItem("Show Path Trace");
		_writeStatistics = new JCheckBoxMenuItem("Write statistics");
		_showListsStat = new JCheckBoxMenuItem("show Lists statistics");
		_animation = new JCheckBoxMenuItem("Open/Closed list animation");
		_WriteToListsStat = new JCheckBoxMenuItem("Write To Tables");
		_withAnimatedPath = new  JCheckBoxMenuItem("Animated Path");
		_ViewMenu.add(_ShowGridLine);
		_ViewMenu.add(_ShowPathTrace);
		_ViewMenu.add(_writeStatistics);
		_ViewMenu.add(_WriteToListsStat);
		_ViewMenu.add(_ShowStatsPanel);
		_ViewMenu.add(_showListsStat);
		_ViewMenu.add(_animation);
		_ViewMenu.add(_withAnimatedPath);

		_menuBar.add(_helpMenu);
		
		this.setJMenuBar(_menuBar);
		this.add(_mainPanel);
		
		_exitItem.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.dispose();		
			}
		});
		_openMap.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				_guiController.loadMap();			
			}
		});
		_saveMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_guiController.saveMap();	
			}
		});
		_loadScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_guiController.loadScenario();		
			}
		});
		_saveScenario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_guiController.saveScenario();		
			}
		});
		_ColorsEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (_colorsDialog == null){
					_colorsDialog = new ColorsDialog(MainFrame.this);
				}
				_colorsDialog.setVisible(true);	
			}
		});
		_ShowStatsPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (_statsPanel == null){
					_statsPanel = new StatisticsDialog(MainFrame.this);
				}
				if (((JCheckBoxMenuItem)arg0.getSource()).isSelected()){
					_statsPanel.setVisible(true);
				}
				else{
					_statsPanel.setVisible(false);				
				}
			}
		});
		
		_showListsStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (_ListsPanel == null){
					_ListsPanel = new OpenClosedListDialog(MainFrame.this);
				}
				if (((JCheckBoxMenuItem)arg0.getSource()).isSelected()){
					_ListsPanel.setVisible(true);
				}
				else{
					_ListsPanel.setVisible(false);				
				}
			}
		});
		_ShowPathTrace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (((JCheckBoxMenuItem)arg0.getSource()).isSelected()){
					_guiController.withPathTrace(true);
				}
				else{
					_guiController.withPathTrace(false);			
				} 
			}
		});
		_writeStatistics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_guiController.setWriteStats(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		
		_WriteToListsStat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_guiController.setWriteToTablePanel(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		_animation.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				_guiController.setAnimation(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		_withAnimatedPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_guiController.withAnimatedPath(((JCheckBoxMenuItem)e.getSource()).isSelected());
				_guiController.openAnimationDiaglog();
			}
		});
		_ShowGridLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (((JCheckBoxMenuItem)arg0.getSource()).isSelected()){
					_guiController.withGridLines(true);
				}
				else{
					_guiController.withGridLines(false);			
				} 
			}
		});
	}
	
	
	public mainPanel getMainPanel() {
		return this._mainPanel;
	}
	
	
	public StatisticsDialog getStatsDialog(){
		return this._statsPanel;
	}
	
	public OpenClosedListDialog getTablesDialog(){
		return this._ListsPanel;
	}

	

	
}

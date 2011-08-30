package GUI;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EtchedBorder;

import GUI.Panels.AnimationDialog;
import GUI.Panels.AnimationPanel;
import GUI.Panels.ColorsDialog;
import GUI.Panels.OpenClosedListDialog;
import GUI.Panels.StatisticsDialog;
import GUI.Panels.mainPanel;


public class MainFrame extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private JMenuBar _menuBar;
	private JMenu _FileMenu,_MapMenu, _helpMenu,_EditMenu,_ViewMenu,_windowsMenu;	
	private JMenuItem _openMap, _saveMap, _exitItem,_loadScenario,_saveScenario,_ColorsEditor,_OpenAnimatedPathDialog;
	private JCheckBoxMenuItem _ShowStatsPanel,_ShowGridLine,_ShowPathTrace,_writeStatistics,_animation,_showListsStat,_WriteToListsStat;
	private mainPanel _mainPanel;
	private StatisticsDialog _statsPanel;
	private OpenClosedListDialog _ListsPanel;
	private ColorsDialog _colorsDialog;
	private AnimationDialog _animationDialog;
	private GUIController _guiController;
	private final JLabel status = new JLabel(" ");
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
		this._windowsMenu = new JMenu("Window");
		this._statsPanel = new StatisticsDialog(this);
		this._statsPanel.setVisible(false);
		this._ListsPanel = new OpenClosedListDialog(this);
		this._ListsPanel.setVisible(false);
		this._animationDialog = new AnimationDialog(this,_guiController);
		this._animationDialog.setVisible(false);
		_menuBar.add(_FileMenu);
		_menuBar.add(_EditMenu);
		_menuBar.add(_ViewMenu);
		_exitItem = new JMenuItem("Exit");
		_FileMenu.add(_exitItem);
		_ColorsEditor = new JMenuItem("Colors Editor");
		_EditMenu.add(_ColorsEditor);
		_menuBar.add(_MapMenu);
		_menuBar.add(_windowsMenu);
		_openMap = new JMenuItem("Load map");
		_MapMenu.add(_openMap);
		_saveMap = new JMenuItem("Save map");
		_MapMenu.add(_saveMap);
		_loadScenario = new JMenuItem("Load scenario");
		_saveScenario = new JMenuItem("Save scenario");
		_MapMenu.add(_loadScenario);
		_MapMenu.add(_saveScenario);
		_ShowStatsPanel = new JCheckBoxMenuItem("Show log panel");
		_ShowGridLine = new JCheckBoxMenuItem("Show Grid Line");
		_ShowPathTrace = new JCheckBoxMenuItem("Show Path Trace");
		_writeStatistics = new JCheckBoxMenuItem("Write to log");
		_showListsStat = new JCheckBoxMenuItem("Show tables");
		_animation = new JCheckBoxMenuItem("Open/Closed list animation");
		_WriteToListsStat = new JCheckBoxMenuItem("Write To Tables");
		_OpenAnimatedPathDialog = new  JMenuItem("Animated Path Control");
		_ViewMenu.add(_ShowGridLine);
		_ViewMenu.add(_ShowPathTrace);
		_windowsMenu.add(_ShowStatsPanel);
		_windowsMenu.add(_writeStatistics);
		_windowsMenu.add(_showListsStat);
		_windowsMenu.add(_WriteToListsStat);
		_ViewMenu.add(_animation);
		_windowsMenu.add(_OpenAnimatedPathDialog);

		_menuBar.add(_helpMenu);
		
		this.setJMenuBar(_menuBar);
		this.add(_mainPanel);
		 status.setBorder(new EtchedBorder());
	     this.add(status, BorderLayout.SOUTH);

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
		_OpenAnimatedPathDialog.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_animationDialog ==null){
					_animationDialog= new AnimationDialog(MainFrame.this,_guiController);
				}
				_animationDialog.setVisible(true);
				_animationDialog.getAnimationPanel().enablePanel(false);	
				_guiController.setWithAnimatedPath(true);
			}
		});
		_ShowGridLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					_guiController.withGridLines(((JCheckBoxMenuItem)arg0.getSource()).isSelected());
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
	
	public AnimationPanel getAnimationPanel(){
		return this._animationDialog.getAnimationPanel();
	}
	
	 public void setStatusText(String s) {
		 status.setText(s);
	 }
	 
	 public boolean getWithGridLines() {
		 return this._ShowGridLine.isSelected();
	 }

	

	
}

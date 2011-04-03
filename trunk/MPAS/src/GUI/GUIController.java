package GUI;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;

import maps.GridMapUtility;
import maps.Scenario;
import maps.TileBasedMap;

import algorithms.myPoint;

import Controller.GridController;
import Defaults.defaultsValues;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.Events.finalPathEvent;
import EventMechanism.Events.showOpenListStateEvent;
import GUI.Panels.Cell;
import GUI.Utils.ScenarioFileFilter;

public class GUIController {
	//Panels
	private MainFrame _main;
	private GridController _controller;
	
	//fields
	private String _algorithmChosen;
	private String _heuristicChosen;
	private boolean _directionChosen; // 1 = 8D, 0 = 4D
	private int _numberOfAgents;
	private int _gridSize ;	
	private AutoStepsThread _stepThread;	
	private Vector<myPoint> oldState; //TODO make it better later	
	private boolean _firstStep;//for running in step mode
	
	public GUIController(){
		_controller = new GridController();
		_firstStep = true;	
		_gridSize = defaultsValues.GridSize;
		this._controller.addListener(new ApplicationEventListener() {
			
			@Override
			public void handle(ApplicationEvent event) {
				if (event instanceof finalPathEvent){
					Vector<Vector<myPoint>> path = GUIController.this._controller.getFinalPath();
					//true means to draw with lines
					GUIController.this._main.getMainPanel().getGridPanel().drawFinalPaths(path,true);
					_main.getStatsDialog().addLine("Final path cost: " + ((finalPathEvent)event).getCost());
					GUIController.this.reset();
				}
				else if (event instanceof showOpenListStateEvent<?>){
					GUIController.this.oldState = ((showOpenListStateEvent<myPoint>)event).getCoordinates();
					GUIController.this._main.getMainPanel().getGridPanel().drawOneStep(GUIController.this.oldState);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().writeToTextArea(GUIController.this.oldState.toString());
					_main.getStatsDialog().addLine(GUIController.this.oldState.toString());
					
				}
				
			}
		});
	}
	
	public void initGuiController(MainFrame main){
		this._main = main;
		_algorithmChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getAlgorithm();
		_heuristicChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getHeuristic();
		_directionChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getDirection();
		init_controller();
	}
	
	public void setMainFrame(MainFrame main){
		this._main = main;
	}
	public GridController getController(){
		return this._controller;
	}
	
	
	public void cAlgorithmActionPerformed(ActionEvent evt) {
		_algorithmChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getAlgorithm();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
	}
	
	public void cHeuristicActionPerformed(ActionEvent evt) {
		_heuristicChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getHeuristic();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);		
	}
	
	public void cDirectionsActionPerformed(ActionEvent evt) {
		_directionChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getDirection();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);	
	}
	
	public void sNumOfAgentsActionPerformed(ChangeEvent evt) {
		_numberOfAgents = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getNumberOfAgents();
		ChangeComboBoxSize(_numberOfAgents);
		this._main.getMainPanel().getGridPanel().setNUM_OF_AGENT(_numberOfAgents);
		this._main.getMainPanel().getGridPanel().setAgentNumber(1);
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	public void sGridSizeActionPerformed(ChangeEvent evt) {
		this._main.getMainPanel().getGridPanel().clearBlocks(this._controller.getMap());
		this._main.getMainPanel().getGridPanel().clearPositions();
		_gridSize = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getGridSize();
		this._main.getMainPanel().ChangeGridPanel(_gridSize);	
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	
	public void rEndActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().set_editMode(Cell.SET_FINISH);
		
	}

	public void rStartActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().set_editMode(Cell.SET_START);
		
	}

	public void rBlockActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().set_editMode(Cell.SET_BLOCKS);	
	}
	
	private void ChangeComboBoxSize(int numberofAgents) {
		this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().ChangeComboBoxSize(numberofAgents);
	}
	
	public void AgentComboBoxActionPerformed(ActionEvent evt) {
		int agentSelected = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getcAgents().getSelectedIndex();
		this._main.getMainPanel().getGridPanel().setAgentNumber(agentSelected+1);	
	}
	
	public void bGeneratePositionsActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearFinalPath();	
		this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getMainPanel().getGridPanel().GeneratePositions();
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().setText("");
		}
	
	public void bClearPositionsActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearPositions();
		this._main.getMainPanel().getGridPanel().clearFinalPath();
	}
	
	public void bRandomMapActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().createRandomBlocks(this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getsDensityValue(), this._controller.getMap());
		
	}
	
	public void bClearMapActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearAll(this._controller.getMap());
		
	}
	
	public void bFindPathActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearFinalPath();
		//this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().setText("");
		if (this._main.getMainPanel().getGridPanel().checkArguments()) {
			this._controller.setTile(this._main.getMainPanel().getGridPanel().get_blockList());
			this._controller.findPath(this._main.getMainPanel().getGridPanel().get_startsList(),this._main.getMainPanel().getGridPanel().get_FinishList());
		} else {
			JOptionPane.showMessageDialog(this._main.getMainPanel(),
					"You havn't entered all the parameters, please try again",
					"Missing Argumets", JOptionPane.ERROR_MESSAGE);
		}
	}	
		
	public void bClearPathActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearFinalPath();
		this._main.getMainPanel().getGridPanel().LoadPositions();
		
	}
	
	public void bStepActionPerformed(ActionEvent evt) {
		if (this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getAutoButton().isSelected()){
			_stepThread = new AutoStepsThread(this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getAutoStepValue()*100,this);
			_stepThread.start();
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getStepButton().setEnabled(false);
		}
		else{
			performStep();
		}
	
	}
	
	public void AutoStepActionPerformed() {
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getStepButton().setEnabled(true);
			_stepThread.stop();		
	}
	
	public void sAutoStepModeActionPerformed() {
		if (_stepThread !=null){
			_stepThread.setInterval(this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getAutoStepValue() * 100);
		    }
		
	}
	

	
	protected void reset() {
		if (this._stepThread != null){
			_stepThread.stop();
		}
		_firstStep = true;
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getStepButton().setEnabled(true);
		
	}
	
	//TODO move to gui controller
	public void performStep(){
		if (this._firstStep){
			System.out.println(this._controller.getMap().toString());
			this._controller.runAlgorithmWithPause(this._main.getMainPanel().getGridPanel().get_startsList(),this._main.getMainPanel().getGridPanel().get_FinishList());
			_firstStep = false;
		}
		else{
			if (oldState != null){
				this._main.getMainPanel().getGridPanel().setEmptyStep(oldState);
			}
			this._controller.resumeAlgorithm();
		}
	}
	
	public void stop(){
		reset();
		if (this._controller.getAlgorithmThread() != null){
			this._controller.getAlgorithmThread().stop();
		}
	
	}

	
	
	public void loadMap() {
		JFileChooser fc = new JFileChooser();
    	fc.addChoosableFileFilter(new ScenarioFileFilter());
    	fc.showOpenDialog(this._main);
    	File tMapFile = fc.getSelectedFile();
		if (tMapFile != null){
			TileBasedMap map = GridMapUtility.loadMap(tMapFile);
			this._main.getMainPanel().ChangeGridPanel(map.getHeightInTiles()); 
			this._controller.setMap(map);
			this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().setGridSizeValue(map.getHeightInTiles());
			this._main.getMainPanel().getGridPanel().drawMap(map);

		}
	}
	public void saveMap(){
		JFileChooser fc = new JFileChooser();
	    	fc.addChoosableFileFilter(new ScenarioFileFilter());
	    	fc.showSaveDialog(this._main);
	    	File tMapFile = fc.getSelectedFile();
	  //  	this._mainPanel.get_controller().setMap(this._mainPanel.getGrid().get_height()); //TODO remove later when map is properly initialized
	    //	this._mainPanel.get_controller().setTile(this._mainPanel.getGrid().get_blockList());
	    	if (tMapFile !=null){
	    	GridMapUtility.saveMap(tMapFile, this._controller.getMap());
	    	}
	}
	

	public void saveScenario() {
		JFileChooser fc = new JFileChooser();
    	fc.addChoosableFileFilter(new ScenarioFileFilter());
    	fc.showSaveDialog(this._main);
    	File tFile = fc.getSelectedFile();
		Scenario s = new Scenario(this._controller.getMap(),this._main.getMainPanel().getGridPanel().get_startsList(),this._main.getMainPanel().getGridPanel().get_FinishList());
		GridMapUtility.saveSecnario(s, tFile);
	}
	public void loadScenario(){
		JFileChooser fc = new JFileChooser();
	    	fc.addChoosableFileFilter(new ScenarioFileFilter());
	    	fc.showOpenDialog(this._main);
	    	File tFile = fc.getSelectedFile();
	    	if (tFile != null){
	    		Scenario s = GridMapUtility.loadScenario(tFile);
				this._main.getMainPanel().ChangeGridPanel(s.getMap().getHeightInTiles()); 
			//	this._mainPanel.get_controller().setMap(s.getMap());
				int numOfAgents = s.getStartLocations().size();
				this._controller.setNumberOfAgents(numOfAgents);
				this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().setGridSizeValue(s.getMap().getHeightInTiles());
				this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().setNumOfAgentsValue(numOfAgents);
				this._main.getMainPanel().getGridPanel().drawScenario(s);
				this._controller.setMap(s.getMap());
				
	    	}
	}
	
	
	public void init_controller(){
		this._controller.setAlgorithm(_algorithmChosen);
		this._controller.setHeuristic(_heuristicChosen);
		this._controller.setDirection(_directionChosen);
		this._controller.setNumberOfAgents (_numberOfAgents);
		this._controller.setMapSize (_gridSize);	
	}
	
	public void init_controller(String algo, String heuristic, boolean dir, int numOfAgents, int gridSize){
		this._controller.setAlgorithm(algo);
		this._controller.setHeuristic(heuristic);
		this._controller.setDirection(dir);
		this._controller.setNumberOfAgents (numOfAgents);
		this._controller.setMapSize (gridSize);		
	}


	


	

	
	
    

}

package GUI;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;

import maps.GridMapUtility;
import maps.Scenario;
import maps.TileBasedMap;

import algorithms.myPoint;
import algorithms.CooperativeAstar.TableKeyInterface;

import Controller.GridController;
import Defaults.Enums.Status;
import Defaults.defaultsValues;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.Events.ClosedListChangeEvent;
import EventMechanism.Events.OpenListChangeEvent;
import EventMechanism.Events.PathNotFoundEvent;
import EventMechanism.Events.ReservationTableUpdateEvent;
import EventMechanism.Events.SingleAgentSearchEvent;
import EventMechanism.Events.finalPathEvent;
import EventMechanism.Events.removeFromOpenListEvent;
import EventMechanism.Events.showStepEvent;
import GUI.Panels.Cell;
import GUI.Utils.ColorManager;
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
	private FinalPathThread _finalAnimationThread;
	private Vector<myPoint> oldState; //TODO make it better later	
	private boolean _firstStep;//for running in step mode
	private boolean _withPathTrace = false;
	private boolean _writeStatistics = false;
	private boolean _writeToTables = false;
	private boolean _animation = false;
	private boolean _animatedPath = false;
	private int _agentNum; //used for color selection for each single A* run inside the CA* algorithm
	//AnimationDialog _animationDialog;
	public GUIController(){
		_controller = new GridController();
		_firstStep = true;	
		_gridSize = defaultsValues.GridSize;
		this._agentNum = 1;
		this._controller.addListener(new ApplicationEventListener() {
			
			@Override
			public void handle(ApplicationEvent event) {
				if (event instanceof finalPathEvent){
					_main.getMainPanel().getConfiguarationPanel().getSettingsPanel().enableSettingsPanel(true);
					Vector<Vector<myPoint>> path = GUIController.this._controller.getFinalPath();
					float finalCost = ((finalPathEvent)event).getCost();
					GUIController.this.setStatusText("Path is found");		
					if(!_animatedPath){
						GUIController.this._main.getMainPanel().getGridPanel().drawFinalPaths(path,_withPathTrace);
					}
					else{//final Path animation;
						GUIController.this._main.getMainPanel().getGridPanel().setFinalPath(path);
					}
					
					if (_writeStatistics){
						_main.getStatsDialog().addLine("\nFinal path cost: " + ((finalPathEvent)event).getCost());
					}
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().setFinalCost(finalCost);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(true);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStopButton(false);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStepButton(false);
					//_animationDialog.getAnimationPanel().enableCheckBox(false);
					if(!GUIController.this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().getIsSelectedCheckBox()){
						GUIController.this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().enablePanel(false);
					}
					GUIController.this.reset();
				}
				else if (event instanceof PathNotFoundEvent){
					_main.getMainPanel().getConfiguarationPanel().getSettingsPanel().enableSettingsPanel(true);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(true);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStopButton(false);
					GUIController.this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStepButton(false);
					GUIController.this.reset();
					JOptionPane.showMessageDialog(_main.getMainPanel(),
							"Path was not found.",
							"Path not found", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (event instanceof showStepEvent<?>){
					GUIController.this.oldState = ((showStepEvent<myPoint>)event).getCoordinates();
					GUIController.this._main.getMainPanel().getGridPanel().drawOneStep(GUIController.this.oldState,_agentNum);
					//GUIController.this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().writeToTextArea(GUIController.this.oldState.toString());	
				}
				else if (event instanceof OpenListChangeEvent<?>){
					OpenListChangeEvent<myPoint> e = (OpenListChangeEvent<myPoint>)event;
					if (_writeStatistics){
						_main.getStatsDialog().addLine("Added to openList:\t" + e.getState().toString()+"\n");
					}
					if (_writeToTables){
						_main.getTablesDialog().addToOpenList(e.getState().get_Coordinates(),e.getState().get_cost(), e.getState().get_heuristic(), e.getState().get_f());
					}
					if (_animation){
						Vector<myPoint> v = e.getState().get_Coordinates();
						for (int i = 0; i < v.size() ;i++){
							myPoint p = v.elementAt(i);
							String currentAgent = "agent" + (i+1);
							if (v.size() == 1){
								currentAgent = "agent" + _agentNum;
							}
							_main.getMainPanel().getGridPanel().animateCell(p.getX(), p.getY(),ColorManager.getInstance().getColor(currentAgent));
						}
					}
				}	
				else if (event instanceof SingleAgentSearchEvent){
					_agentNum = ((SingleAgentSearchEvent)event).getAgentNum();
				}
				else if (event instanceof ReservationTableUpdateEvent<?>){
					if (_writeStatistics){
						HashMap<TableKeyInterface<myPoint>,Integer> reservationTable = ((ReservationTableUpdateEvent<myPoint>)event).getReservationTable();
					    Iterator it = reservationTable.entrySet().iterator();

					    while (it.hasNext()) { //TODO fix ugly code to more generic one later
					        Map.Entry pairs = (Map.Entry)it.next();
					        myPoint coordinates = ((TableKeyInterface<myPoint>) pairs.getKey()).getCoordinates();
					        int time = ((TableKeyInterface<myPoint>) pairs.getKey()).getT();
					        _main.getStatsDialog().addLine("X: " + coordinates.getX() + "\tY: " + coordinates.getY() + "\tT: " + time  + "\tAgent: " +  pairs.getValue());
					    }
					    _main.getStatsDialog().addLine("Reservation Table updated:\n\n");
					}
				}
				else if (event instanceof ClosedListChangeEvent<?>){
					ClosedListChangeEvent<myPoint> e = (ClosedListChangeEvent<myPoint>)event;
					if (_writeStatistics){
						_main.getStatsDialog().addLine("Added to closedList:\t" + e.getState().toString() + "\n");
					}
					if (_writeToTables){
						_main.getTablesDialog().addToClosedList(e.getState().get_Coordinates(),e.getState().get_cost(), e.getState().get_heuristic(), e.getState().get_f());
						
					}
					if (_animation){
						Vector<myPoint> v = e.getState().get_Coordinates();
						for (int i = 0; i < v.size() ;i++){
							myPoint p = v.elementAt(i);
							String currentAgent = "agent" + (i+1);
							if (v.size() == 1){
								currentAgent = "agent" + _agentNum;
							}
							_main.getMainPanel().getGridPanel().animateCell(p.getX(), p.getY(),ColorManager.getInstance().getColor(currentAgent));
						}
					}
				}
				else if (event instanceof removeFromOpenListEvent<?>){
					removeFromOpenListEvent<myPoint> e = (removeFromOpenListEvent<myPoint>)event;
					if (_writeToTables){
						_main.getTablesDialog().currentStateChange(e.getState().get_Coordinates(),e.getState().get_cost(), e.getState().get_heuristic(), e.getState().get_f());
						_main.getTablesDialog().removeFromOpenList(e.getState().get_Coordinates(),e.getState().get_cost(), e.getState().get_heuristic(), e.getState().get_f());
					}
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
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
	}
	
	public void cHeuristicActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		_heuristicChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getHeuristic();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);		
	}
	
	
	public void cDirectionsActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		_directionChosen = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getDirection();
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);	
	}
	
	public void sNumOfAgentsActionPerformed(ChangeEvent evt) {
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		this._main.getMainPanel().getGridPanel().clearPositions();
		this._main.getMainPanel().getGridPanel().clearFinalPath();
		_numberOfAgents = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getNumberOfAgents();
		ChangeComboBoxSize(_numberOfAgents);
		this._main.getMainPanel().getGridPanel().setNUM_OF_AGENT(_numberOfAgents);
		this._main.getMainPanel().getGridPanel().setAgentNumber(1);
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		
	}
	public void sGridSizeActionPerformed(ChangeEvent evt) {
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		this._main.getMainPanel().getGridPanel().clearBlocks(this._controller.getMap());
		this._main.getMainPanel().getGridPanel().clearPositions();
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().clearFinalCost();
		_gridSize = this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getGridSize();
		this._main.getMainPanel().ChangeGridPanel(_gridSize);	
		init_controller(_algorithmChosen,_heuristicChosen,_directionChosen,_numberOfAgents,_gridSize);
		Runtime.getRuntime().gc();
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
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().clearFinalCost();
		this._main.getMainPanel().getGridPanel().clearPositions();
		this._main.getMainPanel().getGridPanel().clearFinalPath();	
		this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getMainPanel().getGridPanel().GeneratePositions();
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		//this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().setText("");
		}
	
	public void bClearPositionsActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearPositions();
		this._main.getTablesDialog().ClearTables();
		this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getMainPanel().getGridPanel().clearFinalPath();
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().clearFinalCost();
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
	}
	
	public void bRandomMapActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().clearFinalCost();
		this._main.getMainPanel().getGridPanel().clearFinalPath();
		this._main.getMainPanel().getGridPanel().createRandomBlocks(this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().getsDensityValue(), this._controller.getMap());
		
	}
	
	public void bClearMapActionPerformed(ActionEvent evt) {
		this._main.getTablesDialog().ClearTables();
		this._main.getMainPanel().getGridPanel().clearAll(this._controller.getMap());
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().clearFinalCost();
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		
		
	}
	
	public void bFindPathActionPerformed(ActionEvent evt) {
		
		this._main.getMainPanel().getGridPanel().clearFinalPath();	
		this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getMainPanel().getGridPanel().setAnimationwithIcon(false);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(false);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		//this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().setText("");
		if(this._main.getMainPanel().getGridPanel().isFinalPathFound()){
			this._main.getTablesDialog().ClearTables();
			_finalAnimationThread = null;
			this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().enableCheckBox(false);
			//this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(true);
		}
		if (this._main.getMainPanel().getGridPanel().checkArguments()) {
			this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().enableSettingsPanel(false);
			this._controller.setTile(this._main.getMainPanel().getGridPanel().get_blockList());
			this._controller.findPath(this._main.getMainPanel().getGridPanel().get_startsList(),this._main.getMainPanel().getGridPanel().get_FinishList());
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStopButton(true);
		} else {
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
			JOptionPane.showMessageDialog(this._main.getMainPanel(),
					"You havn't entered all the parameters, please try again",
					"Missing Argumets", JOptionPane.ERROR_MESSAGE);
		}
		
	}	
		
	public void bClearPathActionPerformed(ActionEvent evt) {
		this._main.getMainPanel().getGridPanel().clearFinalPath();
		this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getTablesDialog().ClearTables();
		this._main.getMainPanel().getConfiguarationPanel().getInfoPanel().clearFinalCost();
		//this._main.getMainPanel().getGridPanel().LoadPositions();
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStepButton(true);
		GUIController.this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().enablePanel(true);
		GUIController.this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().setAnimationCheckBox(false);						
		this._animatedPath = false;
		_finalAnimationThread = null;
		this.setStatusText(" ");
	}
	
	public void bStepActionPerformed(ActionEvent evt) {
		if(this._main.getMainPanel().getGridPanel().checkArguments()){
			if (this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getAutoButton().isSelected()){
				_stepThread = new AutoStepsThread(this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getAutoStepValue()*100,this);
				_stepThread.start();
				this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getStepButton().setEnabled(false);				
			}
			else{
				performStep();
			}
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStopButton(true);	
			this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().enableSettingsPanel(false);
		}
		else{
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
			this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(false);
			JOptionPane.showMessageDialog(this._main.getMainPanel(),
					"You havn't entered all the parameters, please try again",
					"Missing Argumets", JOptionPane.ERROR_MESSAGE);
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
	
	public void sAnimationSpeedActionPerformed(int time) {
		_main.getMainPanel().getGridPanel().setAnimationTimer(time);	
	}
	
	public void sFinalAnimationSpeedActionPerformed(int value) {
		if(this._finalAnimationThread!=null){
			this._finalAnimationThread.setInterval(value);
		}		
	}
	
	public void bPreviousActionPerformed(ActionEvent evt) {
		_main.getMainPanel().getGridPanel().drawPreviousFinalStep();
		
	}

	public void bPlayActionPerformed(ActionEvent evt) {
		if(_finalAnimationThread == null && _animatedPath){
			_finalAnimationThread = new FinalPathThread(this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().getAnimationSpeedValue()*10,this);
			_finalAnimationThread.start();			
		}
					
		//_main.getMainPanel().getGridPanel().setFinalPathStep (0);
		
	}

	public void bNextActionPerformed(ActionEvent evt) {
		_main.getMainPanel().getGridPanel().drawNextFinalStep();
		
	}
	
	public void bPauseActionPerformed(ActionEvent evt) {
		if(_finalAnimationThread!=null){
			_finalAnimationThread.setFinished(true);
			_finalAnimationThread=null;
		}

		
	}
	

	
	protected void reset() {
		if (this._stepThread != null){
			_stepThread.stop();
		}
		_firstStep = true;
		//this._main.getMainPanel().getConfiguarationPanel().getControlPanel().getStepButton().setEnabled(true);
		
	}
	
	public void performStep(){
		if (this._firstStep){
			this._main.getTablesDialog().ClearTables();
			this._controller.runAlgorithmWithPause(this._main.getMainPanel().getGridPanel().get_startsList(),this._main.getMainPanel().getGridPanel().get_FinishList());
			_firstStep = false;
		}
		else{
			if (oldState != null){
					this._main.getMainPanel().getGridPanel().removeStatus(oldState, Status.inOpenList);
			}
			this._controller.resumeAlgorithm();
		}
	}
	
	public void stop(){
		this._main.getMainPanel().getConfiguarationPanel().getSettingsPanel().enableSettingsPanel(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableStopButton(false);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableClearPathButton(true);

		reset();
		if (this._controller.getAlgorithmThread() != null){
			this._controller.getAlgorithmThread().stop();
		}
		_main.getMainPanel().getGridPanel().stopAnimations();
		this._main.getMainPanel().getGridPanel().clearOpenList();
		this._main.getMainPanel().getGridPanel().LoadPositions();
	}
	
	public void preformFinalPathStep() {
		this._main.getMainPanel().getGridPanel().drawNextFinalStep();
		
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
		this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
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
		if (tFile != null){
			GridMapUtility.saveSecnario(s, tFile);
		}
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
	    	this._main.getMainPanel().getConfiguarationPanel().getControlPanel().enableFindPathButton(true);
	}
	
	/*public void openAnimationDiaglog() {
		if (_animationDialog ==null){
			_animationDialog= new AnimationDialog(this._main,this);
			_animationDialog.setVisible(true);
		}
		_animationDialog.setVisible(true);
	
	}*/
	
	public int getAnimationSpeedValue(){
		return this._main.getMainPanel().getConfiguarationPanel().getAnimationPanel().getAnimationSpeedValue();
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
	
	public void withPathTrace(boolean with){
		this._withPathTrace = with;
	}

	public void withGridLines(boolean b) {
		this._main.getMainPanel().getGridPanel().setWithGridLines(b);
		
	}
	
	public boolean getWriteStats(){
		return this._writeStatistics;
	}
	public void setWriteStats(boolean writeStats){
		this._writeStatistics = writeStats;
	}

	public void setAnimation(boolean selected) {
		this._animation = selected;
		
	}
	public boolean getAnimation(){
		return this._animation;
	}

	public void setWriteToTablePanel(boolean selected) {
		this._writeToTables = selected;
		
	}

	public void withAnimatedPath(boolean selected) {
		this._animatedPath  = selected;
		
	}
	
	public void setStatusText(String s) {
		 this._main.setStatusText(s);
	 }

	



	

	

	
	
	
    

}

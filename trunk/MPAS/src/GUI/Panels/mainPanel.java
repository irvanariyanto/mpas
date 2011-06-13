package GUI.Panels;

import java.awt.BorderLayout;



import javax.swing.JPanel;

import maps.TileStatus;
import Defaults.defaultsValues;
import EventMechanism.ApplicationEvent;
import EventMechanism.ApplicationEventListener;
import EventMechanism.ApplicationEventListenerCollection;
import EventMechanism.ApplicationEventSource;
import EventMechanism.Events.SetBlockCellEvent;
import EventMechanism.Events.SetFinishCellEvent;
import EventMechanism.Events.SetStartCellEvent;
import GUI.GUIController;


public class mainPanel extends JPanel implements ApplicationEventSource{

	private static final long serialVersionUID = 1L;
	
	// Variables declaration
	private ApplicationEventListenerCollection _listeners; 
	private GridPanel _grid;
	private ConfigurationPanel _configPanel;
	private GUIController _guiController;
	// End of variables declaration
	
	
	
	/**
	 * Constructor
	 * @param controller 
	 */
	public mainPanel(GUIController controller) {
		super();
		this._guiController = controller;
		_grid = new GridPanel(defaultsValues.GridSize);			
		_configPanel = new ConfigurationPanel(_guiController);
		//this._guiController.init_controller();			
		this._listeners = new ApplicationEventListenerCollection();		
		initComponenets();
		
	}
	public GridPanel getGridPanel(){
		return this._grid;
	}
	public ConfigurationPanel getConfiguarationPanel(){
		return this._configPanel;
	}
	/**
	 * initialize all the swing Components
	 */
	private void initComponenets() {				
		this.setLayout(new BorderLayout(2,2));
		this.add(_configPanel , BorderLayout.WEST);
		this.add(_grid , BorderLayout.CENTER);
		this._grid.addListener(new MainFrameListener());
		this._grid.setAgentNumber(1);//Default			
	}	


	/**
	 * change the grid panel to the new grid size which is given
	 * @param gridSize
	 */
	public void ChangeGridPanel(int gridSize, boolean withGridLines){		
		this.remove(_grid );
		this._grid = new GridPanel(gridSize);
		this.add(_grid , BorderLayout.CENTER);
		this._grid.setWithGridLines(withGridLines);
		this._grid .revalidate();
		this._grid.addListener(new MainFrameListener());
	}
	
    
   
 

    protected class MainFrameListener implements ApplicationEventListener {

		@Override
		public void handle(ApplicationEvent event) {
			if (event instanceof SetBlockCellEvent) {
				SetBlockCellEvent blockEvent = (SetBlockCellEvent)event;
				mainPanel.this._grid.setBlockCell(blockEvent.getPosition());
				if(mainPanel.this._grid.get_blockList().contains(blockEvent.getPosition())){
					mainPanel.this._guiController.getController().setTile(blockEvent.getPosition());
				}
				else{
					mainPanel.this._guiController.getController().setTile( blockEvent.getPosition(),TileStatus.free);
				}
			}
			if (event instanceof SetStartCellEvent) {
				SetStartCellEvent startEvent = (SetStartCellEvent)event;
				mainPanel.this._grid.setStartCell(startEvent.getPosition(),startEvent.getAgent());
			}
			if (event instanceof SetFinishCellEvent) {
				SetFinishCellEvent finishEvent = (SetFinishCellEvent)event;
				mainPanel.this._grid.setFinishCell(finishEvent.getPosition(),finishEvent.getAgent());
			}		
		}
	}

    @Override
	public void addListener(ApplicationEventListener listener) {
		this._listeners.add(listener);
		
	}

	@Override
	public void clearListeners() {
		this._listeners.clear();
		
	}

	@Override
	public void removeListener(ApplicationEventListener listener) {
		this._listeners.remove(listener);
	}
	
   
}

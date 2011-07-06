package GUI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import GUI.GUIController;


public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	// Variables declaration 
	private  JButton _bFindPath;
	private  JButton _bStop;
	private  JButton _bStep;
	private  JButton _bClearPath;
	private JToggleButton _tbAutoStepMode;
	private JSlider _sAutoStepMode;
	private JLabel _lSec;
	private GUIController _guiController;
	private JSlider _sAnimationSpeed;
    private JLabel _lAnimationSpeed;
	
	// End of variables declaration

	/**
	 * Constructor
	 * @param controller 
	 */
	public ControlPanel(GUIController controller) {
		super();
		this._guiController= controller;
		this.setBorder(BorderFactory.createTitledBorder("Control"));
		_bFindPath = new JButton("Find Path");
		_bStop = new JButton("Stop");
		_bStop.setEnabled(false);
		_bStep = new JButton("Step");
		_tbAutoStepMode = new JToggleButton("Auto");
		_bClearPath = new JButton("Clear Path");
		_bClearPath.setEnabled(false);
		_sAutoStepMode = new JSlider(1,30,1);
		_sAutoStepMode.setPaintTicks(true);
		_sAutoStepMode.setMajorTickSpacing(5);
		_lSec = new JLabel(_sAutoStepMode.getValue() * 100 + " msec");
		_sAnimationSpeed = new JSlider(1,30,1);
		_sAnimationSpeed.setPaintTicks(true);
		_sAnimationSpeed.setMajorTickSpacing(5);
		_lAnimationSpeed = new JLabel(_sAutoStepMode.getValue()*1000 + " msec");
	    
		initComponents();
	}

	/**
	 * initialize all the swing Components
	 */
	public void initComponents() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
	            layout.createParallelGroup()
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup()
	                	.addGroup(layout.createSequentialGroup()
	    	                        .addComponent(_bFindPath)
	    	                        .addComponent(_sAnimationSpeed)
	    	                        .addComponent(_lAnimationSpeed))
	                    .addComponent(_bStop)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(_bStep)
	                        .addComponent(_tbAutoStepMode)
	                        .addComponent(_sAutoStepMode, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
	                        .addComponent(_lSec))
	                     .addComponent(_bClearPath))));
		layout.setVerticalGroup(
				layout.createParallelGroup()
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
	                    .addComponent(_lSec)
	                    .addGroup(layout.createSequentialGroup()
	                    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    	                            .addComponent(_bFindPath)
	    	                            .addComponent(_sAnimationSpeed)
	    	                            .addComponent(_lAnimationSpeed))
	                        .addComponent(_bStop)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                            .addComponent(_bStep)
	                            .addComponent(_sAutoStepMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	                            .addComponent(_tbAutoStepMode))))
	                        .addComponent(_bClearPath)));
		
		_bFindPath.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bFindPathActionPerformed(evt);
	         }
	     });
       _bClearPath.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bClearPathActionPerformed(evt);
	         }
	     });
       
       _bStep.addActionListener(new ActionListener() {			
       		public void actionPerformed(ActionEvent evt) {
       			_guiController.bStepActionPerformed(evt);				
       		}
		});
       _bStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_guiController.stop();				
			}
		});
       
       _tbAutoStepMode.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				if (!_tbAutoStepMode.isSelected()){
					_guiController.AutoStepActionPerformed();	
				}
			}
		});
       
       _sAutoStepMode.addChangeListener(new ChangeListener() {
			//TODO remove duplicate listeners
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    setAutoStepLabel(value *100 + " msec");
			    _guiController.sAutoStepModeActionPerformed();
			}
		});
       
       _sAnimationSpeed.addChangeListener(new ChangeListener() {
			//TODO remove duplicate listeners
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    setAnimationSpeedLabel(value *1000 + " msec");
			    _guiController.sAnimationSpeedActionPerformed(value * 1000);
			}
		});

	    }
	
	public void setAnimationSpeedLabel(String string) {
		this._lAnimationSpeed.setText(string);
		
	}

	public JToggleButton getAutoButton(){
		return this._tbAutoStepMode;
	}


	public JButton getStepButton(){
		return this._bStep;
	}
	
	public int getAutoStepValue(){
		return this._sAutoStepMode.getValue();
	}

	public void setAutoStepLabel(String string) {
		this._lSec.setText(string);
		
	}

	public void enableFindPathButton(boolean b) {
		this._bFindPath.setEnabled(b);	
	}
	
	public void enableStopButton(boolean b) {
		this._bStop.setEnabled(b);
		
	}
	public void enableClearPathButton(boolean b) {
		this._bClearPath.setEnabled(b);
		
	}

	public void enableStepButton(boolean b) {
		this._bStep.setEnabled(b);
		
	}

	public boolean isSelectedAutoStep() {
		return this._tbAutoStepMode.isSelected();
	}

	public void setSelectedAutoStep(boolean b) {
		this._tbAutoStepMode.setSelected(b);
	}

	public void enableAutoStepMode(boolean b) {
		this._tbAutoStepMode.setEnabled(b);
		this._sAutoStepMode.setEnabled(b);
		
	}
}

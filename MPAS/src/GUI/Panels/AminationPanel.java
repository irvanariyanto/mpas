package GUI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.GUIController;

public class AminationPanel extends JPanel {

	// Variables declaration 
	private static final long serialVersionUID = 1L;
	private GUIController _guiController;
	private JButton _bFirst;
	private JButton _bPrevious;
	private JButton _bPlay;
	private JButton _bNext;
	private JButton _bLast;
	private JButton _bPause;
	private JSlider _sAnimationSpeed;
	private JLabel _lSpeed;
	private ImageIcon _iFirst = new ImageIcon("Icons/Animation/first.png","First");
	private ImageIcon _iPrevious = new ImageIcon("Icons/Animation/previous.png","Previous");
	private ImageIcon _iPlay = new ImageIcon("Icons/Animation/play.png","Play");
	private ImageIcon _iNext = new ImageIcon("Icons/Animation/next.png","Next");
	private ImageIcon _iLast = new ImageIcon("Icons/Animation/last.png","Last");
	private ImageIcon _iPause = new ImageIcon("Icons/Animation/pause.png","Pause");
	private JPanel _playerPanel;
	private JPanel _speedPanel;

	// End of variables declaration
	
	/**
	 * Constructor
	 * @param controller 
	 */
	public AminationPanel(GUIController controller){
		super();
		this._guiController= controller;
		this.setBorder(BorderFactory.createTitledBorder("Animation Panel"));
		_bFirst = new JButton(_iFirst);
		_bFirst.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		_bPrevious = new JButton(_iPrevious);
		_bPrevious.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		_bPlay = new JButton(_iPlay);
		_bPlay.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		_bNext = new JButton(_iNext);
		_bNext.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		_bLast = new JButton(_iLast);
		_bLast.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		_bPause = new JButton(_iPause);
		_bPause.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		_playerPanel = new JPanel();
		_speedPanel = new JPanel();
		_sAnimationSpeed = new JSlider(1,30,1);
		_sAnimationSpeed.setPaintTicks(true);
		_sAnimationSpeed.setMajorTickSpacing(5);
		_lSpeed = new JLabel(_sAnimationSpeed.getValue() * 100 + " msec");
		_speedPanel.setBorder(BorderFactory.createTitledBorder("Animation Speed"));
		_sAnimationSpeed.addChangeListener(new ChangeListener() {
			//TODO remove duplicate listeners
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				int value = slider.getValue();
			    setSpeedLabel(value *100 + " msec");
			    //_guiController.sAnimationSpeedActionPerformed();
			}
		});
		_bFirst.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bFirstActionPerformed(evt);
	         }
	     });
		_bPrevious.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bPreviousActionPerformed(evt);
	         }
	     });
		_bPlay.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bPlayActionPerformed(evt);
	         }
	     });
		_bNext.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bNextActionPerformed(evt);
	         }
	     });
		_bLast.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bLastActionPerformed(evt);
	         }
	     });
		_bPause.addActionListener(new  ActionListener() {
	    	 public void actionPerformed( ActionEvent evt) {
	    		 _guiController.bPauseActionPerformed(evt);
	         }
	     });
		initComponents();
		
	}
	
	
	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		_playerPanel.add(_bFirst);
		_playerPanel.add(_bPrevious);
		_playerPanel.add(_bPlay);
		_playerPanel.add(_bNext);
		_playerPanel.add(_bLast);
		_playerPanel.add(_bPause);
		_speedPanel.add(_sAnimationSpeed);
		_speedPanel.add(_lSpeed);
		this.add(_playerPanel);
		this.add(_speedPanel);
		
	}
	
	public void setSpeedLabel(String string) {
		this._lSpeed.setText(string);		
	}

}

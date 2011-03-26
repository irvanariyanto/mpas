package GUI;

import Controller.GridController;
import GUI.Panels.mainPanel;

public class AutoStepsThread implements Runnable{

	private int _interval;
	private boolean _finished;
	private mainPanel _mainpanel;
	public AutoStepsThread(int interval,mainPanel mainPanel){
		this._interval = interval;
		this._mainpanel = mainPanel;
		this._finished = false;
	}

	@Override
	public void run() {
		while (!_finished){
			_mainpanel.performStep();
			try {
				Thread.sleep(_interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public synchronized void setFinished(boolean finished){
		this._finished = finished;
	}

	
}

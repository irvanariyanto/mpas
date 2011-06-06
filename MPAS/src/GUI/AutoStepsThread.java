package GUI;


public class AutoStepsThread extends Thread{

	private int _interval;
	private boolean _finished;
	private GUIController _guiController;
	
	public AutoStepsThread(int interval,GUIController guiController){
		this._interval = interval;
		this._guiController = guiController;
		this._finished = false;
	}

	@Override
	public void run() {
		while (!_finished){
			_guiController.performStep();
			try {
				Thread.sleep(_interval);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	public synchronized void setFinished(boolean finished){
		this._finished = finished;
	}
	public synchronized void setInterval(int interval){
		this._interval = interval;
	}
	
}

package GUI;

public class FinalPathThread extends Thread{


	private int _interval;
	private GUIController _guiController;
	private boolean _finished;
	
	public FinalPathThread(int interval,GUIController guiController){
		this._interval = interval;
		this._guiController = guiController;
		this._finished = false;
	}

	@Override
	public void run() {
		while (!_finished){
			_guiController.preformFinalPathStep();
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
	public synchronized void setInterval(int interval){
		this._interval = interval;
	}
	
}

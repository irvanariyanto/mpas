package algorithms.Interfaces;

public interface Pausable {
	public void pause();
	public void resume();
	public void setPause(boolean shouldPause);
	public boolean getPause();
}

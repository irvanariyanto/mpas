package algorithms.AstarID;

public class IDGenerator {
	private int _id;
	public static IDGenerator INSTANCE = new IDGenerator();
	private IDGenerator(){
		_id = 0;		
	}
	public static IDGenerator getInstance(){
		return INSTANCE;
	}
	

	public int NextID() {
		return _id++;
	}

	public void reset() {
		_id = 0;
		
	}

}

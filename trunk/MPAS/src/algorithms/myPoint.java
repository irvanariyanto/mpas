package algorithms;

/**
 * a representation of coordinates in the tiled based map
 * @author amit ofer & liron katav
 *
 */

public class myPoint {
	/** The x coordinate at the given step */
	private int x;
	/** The y coordinate at the given step */
	private int y;
	
	/**
	 * Create a new step
	 * 
	 * @param x The x coordinate of the new step
	 * @param y The y coordinate of the new step
	 */
	public myPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x coordinate of the new step
	 * 
	 * @return The x coodindate of the new step
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y coordinate of the new step
	 * 
	 * @return The y coodindate of the new step
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(myPoint other) {
			return (other.x == x) && (other.y == y);
	}
	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}

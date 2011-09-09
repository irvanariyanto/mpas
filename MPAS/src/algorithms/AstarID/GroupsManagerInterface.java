package algorithms.AstarID;

import java.util.Vector;



public interface GroupsManagerInterface<E> {
	
	public int[] simulatePath();
	public void mergeGroups(int first,int second);
	public int getAgentGroup(int agentID);
	public Vector<E> combineAllPaths();
	
}

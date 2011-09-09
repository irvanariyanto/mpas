package algorithms.AstarID;

import java.util.Vector;

import EventMechanism.ApplicationEventSource;



public interface GroupsManagerInterface<E> extends ApplicationEventSource {
	
	public int[] simulatePath();
	public void mergeGroups(int first,int second);
	public int getAgentGroup(int agentID);
	public Vector<E> combineAllPaths();
	
}

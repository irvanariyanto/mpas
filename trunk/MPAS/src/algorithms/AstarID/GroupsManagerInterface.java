package algorithms.AstarID;

public interface GroupsManagerInterface<E> {
	
	public int[] simulatePath();
	public void mergeGroups(int first,int second);
	public int getAgentGroup(int agentID);
	
}

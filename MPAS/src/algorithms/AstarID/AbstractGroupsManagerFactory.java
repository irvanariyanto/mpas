/**
 * 
 */
package algorithms.AstarID;

import maps.MapInterface;
import algorithms.myPoint;
import algorithms.Astar.AStarSearch;
import algorithms.Interfaces.SearchInterface;
import algorithms.Interfaces.StateInterface;



/**
 * @author ofer
 * @param <T>
 *
 */
public abstract class AbstractGroupsManagerFactory<E> {

	public abstract GroupsManagerInterface<StateInterface<E>> createManager(
			StateInterface<E> start, StateInterface<E> goal,
			AStarSearch<E> pathfinder);
}

package algorithms.CooperativeAstar;

public abstract class AbstractKeyFactoy<E> {

	public abstract TableKeyInterface<E> createKey(E data,int time);
}

package algorithms.CooperativeAstar;

public abstract class AbstractKeyFactory<E> {

	public abstract TableKeyInterface<E> createKey(E data,int time);
}

package algorithms;

public abstract class AbstractKeyFactory<E> {

	public abstract TableKeyInterface<E> createKey(E data,int time);
}

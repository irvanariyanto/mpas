package algorithms;


public class PointKeyFactory extends AbstractKeyFactory<myPoint>{

	@Override
	public TableKeyInterface<myPoint> createKey(myPoint data, int time) {
		
		return new TableKey(data.getX(),data.getY(),time);
	}

}

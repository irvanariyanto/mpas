package algorithms.CooperativeAstar;

import algorithms.myPoint;

public class PointKeyFactory extends AbstractKeyFactoy<myPoint>{

	@Override
	public TableKeyInterface<myPoint> createKey(myPoint data, int time) {
		
		return new TableKey(data.getX(),data.getY(),time);
	}

}

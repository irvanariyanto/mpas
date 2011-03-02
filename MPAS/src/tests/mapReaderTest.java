package tests;

import java.io.File;

import maps.GridMapUtility;
import maps.TileBasedMap;
import maps.TiledMapImpl;

public class mapReaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TileBasedMap map = GridMapUtility.loadMap(new File("map1.xml"));
		System.out.println(((TiledMapImpl)map).toString());
	}

}

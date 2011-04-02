package maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import GUI.Utils.Utils;
import Utils.MyLogger;
import algorithms.myPoint;

public class GridMapUtility {

	public static TileBasedMap loadXMLMap(File file) {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			NodeList tList = root.getElementsByTagName("Map");
			Element mapElement = (Element)tList.item(0);
			int length = Integer.parseInt(mapElement.getAttribute("Length"));
			// boolean diagonal =
			// Boolean.parseBoolean(root.getAttribute("Diagonal"));
			TileBasedMap map = new TiledMapImpl(length, length, false);
			NodeList nList = mapElement.getElementsByTagName("*");
			for (int i = 0; i < nList.getLength(); i++) {
				Element tElement = (Element) nList.item(i);
				String type = tElement.getNodeName();
				if (type.equals("BlockedTile")) {
					int x = Integer.parseInt(tElement.getAttribute("x"));
					int y = Integer.parseInt(tElement.getAttribute("y"));
					if (x >= length || y >= length) {
						// throw exception
					} else {
						map.setTile(x, y, TileStatus.blocked);
					}
				}

			}
			return map;

		} catch (FileNotFoundException e) {

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void saveMap(File file, TileBasedMap tileBasedMap) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Scenario");
			doc.appendChild(rootElement);
			Element mapElement = doc.createElement("Map");
			rootElement.appendChild(mapElement);
			mapElement.setAttribute("Length", "" + tileBasedMap.getHeightInTiles());
			for (int i = 0; i < tileBasedMap.getHeightInTiles(); i++) {
				for (int j = 0; j < tileBasedMap.getWidthInTiles(); j++) {
					if (tileBasedMap.blocked(i, j)) {
						Element blockedTile = doc.createElement("BlockedTile");
						blockedTile.setAttribute("x", "" + i);
						blockedTile.setAttribute("y", "" + j);
						mapElement.appendChild(blockedTile);
					}
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void saveSecnario(Scenario scenario, File file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			TileBasedMap map = scenario.getMap();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Scenario");
			doc.appendChild(rootElement);
			Element mapElement = doc.createElement("Map");
			rootElement.appendChild(mapElement);
			mapElement.setAttribute("Length", "" + map.getHeightInTiles());
			for (int i = 0; i < map.getHeightInTiles(); i++) {
				for (int j = 0; j < map.getWidthInTiles(); j++) {
					if (map.blocked(i, j)) {
						Element blockedTile = doc.createElement("BlockedTile");
						blockedTile.setAttribute("x", "" + i);
						blockedTile.setAttribute("y", "" + j);
						mapElement.appendChild(blockedTile);
					}
				}
			}
			Element agentsElement = doc.createElement("Agents");
			rootElement.appendChild(agentsElement);
			for (int i = 0 ; i < scenario.getStartLocations().size();i++){
				myPoint tStart = scenario.getStartLocations().elementAt(i);
				myPoint tGoal = scenario.getGoalLocations().elementAt(i);
				Element tAgent = doc.createElement("Agent");
				tAgent.setAttribute("sx", "" + tStart.getX());
				tAgent.setAttribute("sy", "" + tStart.getY());
				tAgent.setAttribute("gx", "" + tGoal.getX());
				tAgent.setAttribute("gy", "" + tGoal.getY());
				agentsElement.appendChild(tAgent);
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Scenario loadXMLScenario(File file) {
		Scenario scenario = null;
		try {
			TileBasedMap map = loadXMLMap(file);
			Vector<myPoint> starts = new Vector<myPoint>();
			Vector<myPoint> goals  = new Vector<myPoint>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			NodeList nList = root.getElementsByTagName("Agent");
			for (int i = 0; i < nList.getLength(); i++){
				Element tElement = (Element)nList.item(i);
				if (tElement.getNodeName().equals("Agent")){
					int sx = Integer.parseInt(tElement.getAttribute("sx"));
					int sy = Integer.parseInt(tElement.getAttribute("sy"));
					int gx = Integer.parseInt(tElement.getAttribute("gx"));
					int gy = Integer.parseInt(tElement.getAttribute("gy"));
					myPoint tStart = new myPoint(sx,sy);
					myPoint tGoal = new myPoint(gx,gy);
					starts.add(tStart);
					goals.add(tGoal);
				}
			}
			scenario = new Scenario(map,starts,goals);
			return scenario;
		} catch (FileNotFoundException e) {

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
		}
		return scenario;
	}
	
	public static Scenario loadStandardScenario(File file){
		Scenario scenario = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			 
			// repeat until all lines is read
			text = reader.readLine();
			//do something with first line?
			text = reader.readLine();
			text = reader.readLine();
			String[] sizes = text.split(",");
			int height = Integer.parseInt(sizes[0]);
			int width = Integer.parseInt(sizes[1]);
			TileBasedMap map = new TiledMapImpl(width, height, false);
			for (int i = 0; i < height ; i++){
				text = reader.readLine();
				for (int j = 0 ; j < text.length() ;  j++){
					if (text.charAt(j) == '@'){
						map.setTile(i, j, TileStatus.blocked);
					}
				}
			}
			text = reader.readLine();
			text = reader.readLine();
			Vector<myPoint> starts = new Vector<myPoint>();
			Vector<myPoint> ends = new Vector<myPoint>();
			while ((text = reader.readLine()) != null){
				String[] args = text.split(",");
				int gx = Integer.parseInt(args[1]);
				int gy = Integer.parseInt(args[2]);
				int sx = Integer.parseInt(args[3]);
				int sy = Integer.parseInt(args[4]);
				myPoint s = new myPoint(sx, sy);
				myPoint g = new myPoint(gx, gy);
				starts.add(s);
				ends.add(g);
			}
			scenario = new Scenario(map, starts, ends);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
				reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
		}
			
		}
		return scenario;
	}
	public static TileBasedMap loadStandardMap(File file){
		TileBasedMap map = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			 
			// repeat until all lines is read
			text = reader.readLine();
			//do something with first line?
			text = reader.readLine();
			text = reader.readLine();
			String[] sizes = text.split(",");
			int height = Integer.parseInt(sizes[0]);
			int width = Integer.parseInt(sizes[1]);
			map = new TiledMapImpl(width, height, false);
			for (int i = 0; i < height ; i++){
				text = reader.readLine();
				for (int j = 0 ; j < text.length() ;  j++){
					if (text.charAt(j) == '@'){
						map.setTile(i, j, TileStatus.blocked);
					}
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
				reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
		}
			
		}
		return map;
	}

	public static TileBasedMap loadMap(File file) {
		TileBasedMap map = null;
		if (Utils.getExtension(file).equals("xml")){
			map = loadXMLMap(file);
		}
		else if (Utils.getExtension(file).equals("txt")){
			map = loadStandardMap(file);
		}
		else{
			MyLogger.getInstance().severe("Bad map format accepted.");
		}
		return map;
	}

	public static Scenario loadScenario(File file) {
		Scenario scenario = null;
		if (Utils.getExtension(file).equals("xml")){
			scenario = loadXMLScenario(file);
		}
		else if (Utils.getExtension(file).equals("txt")){
			scenario = loadStandardScenario(file);
		}
		else{
			MyLogger.getInstance().severe("Bad scenario format accepted.");
		}
		return scenario;
	}
}

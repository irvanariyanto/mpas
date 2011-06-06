package GUI.Utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

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

/**
 * The class is used for getting and changing color the color setting of the
 * application. the colors settings are saved on the file Colors.xml each
 * element in the application has a unique id and the colors.xml file keeps the
 * RGB value for every element.
 * 
 * @author amit ofer
 * 
 */
public class ColorManager {

	private static final ColorManager INSTANCE = new ColorManager();
	private HashMap<String, Color> _mapping;

	private ColorManager() {
		this._mapping = new HashMap<String, Color>();
		loadColors();
	}

	private void loadColors() {
		Color defaultColor = Color.GRAY;
		Color color = null;
		try {
			File file = new File("Colors.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList tList = doc.getElementsByTagName("Element");
			for (int i = 0; i < tList.getLength(); i++) {
				Element tElement = (Element) tList.item(i);
				int r = Integer.parseInt(tElement.getAttribute("r"));
				int g = Integer.parseInt(tElement.getAttribute("g"));
				int b = Integer.parseInt(tElement.getAttribute("b"));
				color = new Color(r, g, b);
				this._mapping.put(tElement.getAttribute("Name"), color);
			}
		} catch (FileNotFoundException e) {
			color = defaultColor;
			e.printStackTrace();
		} catch (SAXException e) {
			color = defaultColor;
			e.printStackTrace();
		} catch (IOException e) {
			color = defaultColor;
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			color = defaultColor;
			e.printStackTrace();
		}

	}

	public static ColorManager getInstance() {
		return INSTANCE;
	}

	public Color getColor(String elementName) {
		if (!this._mapping.containsKey(elementName)) {
			this._mapping.put(elementName, getColorFromFile(elementName));
		}
		return this._mapping.get(elementName);
	}

	/**
	 * search the colors.xml file for the element that corresponds with
	 * elementName and return its color. if such element does not exist or the
	 * file isn't found it returns Gray as a default.
	 * 
	 * @param elementName
	 *            -the name of the element we want to retrieve its color.
	 * @return the color of the element.
	 */
	public static Color getColorFromFile(String elementName) {
		Color defaultColor = Color.GRAY;
		Color color = null;
		try {
			File file = new File("Colors.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList tList = doc.getElementsByTagName("Element");
			for (int i = 0; i < tList.getLength(); i++) {
				Element tElement = (Element) tList.item(i);
				if (tElement.getAttribute("Name").equals(elementName)) {
					int r = Integer.parseInt(tElement.getAttribute("r"));
					int g = Integer.parseInt(tElement.getAttribute("g"));
					int b = Integer.parseInt(tElement.getAttribute("b"));
					color = new Color(r, g, b);
					break;
				}
			}
			if (color == null) {
				color = defaultColor;
			}
		} catch (FileNotFoundException e) {
			color = defaultColor;
			e.printStackTrace();
		} catch (SAXException e) {
			color = defaultColor;
			e.printStackTrace();
		} catch (IOException e) {
			color = defaultColor;
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			color = defaultColor;
			e.printStackTrace();
		}
		return color;
	}

	public void setColor(String elementName, Color color) {
		this._mapping.put(elementName, color);
		setColorInFile(elementName, color);
	}

	/**
	 * change the color settings of the element given in the colors.xml file if
	 * the element doesn't exist in the file it will add it.
	 * 
	 * @param elementName
	 *            - the name of the edited element
	 * @param color
	 *            - the new color value.
	 */
	public static void setColorInFile(String elementName, Color color) {
		try {
			File file = new File("Colors.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList tList = doc.getElementsByTagName("Element");
			Element root = doc.getDocumentElement();
			boolean exist = false;
			for (int i = 0; i < tList.getLength(); i++) {
				Element tElement = (Element) tList.item(i);
				if (tElement.getAttribute("Name").equals(elementName)) {
					tElement.setAttribute("r", "" + color.getRed());
					tElement.setAttribute("g", "" + color.getGreen());
					tElement.setAttribute("b", "" + color.getBlue());
					exist = true;
					break;
				}
			}
			if (!exist) {
				Element newElement = doc.createElement("Element");
				newElement.setAttribute("Name", elementName);
				newElement.setAttribute("r", "" + color.getRed());
				newElement.setAttribute("g", "" + color.getGreen());
				newElement.setAttribute("b", "" + color.getBlue());
				root.appendChild(newElement);
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns all of the color configuration in the format of Object[][] used
	 * for creating the table model for the colors editor dialog.
	 */
	public static Object[][] createDataModelFromFile() {
		Object[][] dataModel = null;
		try {
			File file = new File("Colors.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList tList = doc.getElementsByTagName("Element");
			dataModel = new Object[tList.getLength()][];
			for (int i = 0; i < tList.getLength(); i++) {
				Element tElement = (Element) tList.item(i);
				String tName = tElement.getAttribute("Name");
				int r = Integer.parseInt(tElement.getAttribute("r"));
				int g = Integer.parseInt(tElement.getAttribute("g"));
				int b = Integer.parseInt(tElement.getAttribute("b"));
				Color tColor = new Color(r, g, b);
				Object[] tItem = { tName, tColor };
				dataModel[i] = tItem;
			}
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataModel;
	}

	/**
	 * generates a random color
	 * 
	 * @return a randomized color.
	 */
	public static Color getRandomColor() {

		Random rand = new Random();
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		return new Color(r, g, b);
	}
}

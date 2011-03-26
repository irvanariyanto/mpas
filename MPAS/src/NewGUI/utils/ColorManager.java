package NewGUI.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
 * The class is used for getting and changing color the color setting of the application.
 * the colors settings are saved on the file Colors.xml
 * each element in the application has a unique id and the colors.xml file keeps the RGB value for every
 * element.
 * @author amit ofer
 *
 */
public class ColorManager {

	/**
	 * search the colors.xml file for the element that corresponds with elementName and return its color.
	 * if such element does not exist or the file isn't found it returns Gray as a default.
	 * @param elementName -the name of the element we want to retrieve its color.
	 * @return the color of the element.
	 */
	public static Color getColor(String elementName){
		Color defaultColor = Color.GRAY;
		Color color = null;
		try{
			File file = new File("Colors.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList tList = doc.getElementsByTagName("Element");
			for (int i = 0 ; i < tList.getLength(); i++){
				Element tElement = (Element)tList.item(i);
				if (tElement.getAttribute("Name").equals(elementName)){
					float r = Float.parseFloat(tElement.getAttribute("r"));
					float g = Float.parseFloat(tElement.getAttribute("g"));
					float b = Float.parseFloat(tElement.getAttribute("b"));
					color = new Color(r,g,b);	
					break;
				}
			}
			if (color == null){
				color = defaultColor;
			}
		}
		catch (FileNotFoundException e) {
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
	/**
	 * change the color settings of the element given in the colors.xml file
	 * if the element doesn't exist in the file it will add it.
	 * @param elementName - the name of the edited element
	 * @param color - the new color value.
	 */
	public static void setColor(String elementName,Color color){
		try {
			File file = new File("Colors.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			NodeList tList = doc.getElementsByTagName("Element");
			Element root = doc.getDocumentElement();
			boolean exist = false;
			for (int i = 0 ; i < tList.getLength();i++){
				Element tElement = (Element)tList.item(i);
				if (tElement.getAttribute("Name").equals(elementName)){
					tElement.setAttribute("r", ""+color.getRed());
					tElement.setAttribute("g", ""+color.getGreen());
					tElement.setAttribute("b", ""+color.getBlue());
					exist = true;
					break;
				}
			}
			if (!exist){
				Element newElement = doc.createElement("Element");
				newElement.setAttribute("Name", elementName);
				newElement.setAttribute("r",""+ color.getRed());
				newElement.setAttribute("g",""+ color.getGreen());
				newElement.setAttribute("b",""+ color.getBlue());
				root.appendChild(newElement);
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * generates a random color
	 * @return a randomized color.
	 */
	public static Color getRandomColor(){
		
		Random rand = new Random();
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		return new Color(r,g,b);
	}
}

package com.github.dementati.teeshop.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Player {
	private String name;
	private ArrayList<Round> rounds = new ArrayList<Round>();
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Round> getRounds() {
		return rounds;
	}
	
	public void addRound(Round round) {
		rounds.add(round);
	}
	
	public void save(File appDir) {
		File file = new File(appDir, name);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			
			Element root = doc.createElement("Player");
			doc.appendChild(root);
			
			for(Round round : rounds) {
				Element roundElement = doc.createElement("Round");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				String dateStr = sdf.format(round.getDate().getTime());
				roundElement.setAttribute("date", dateStr);
				
				for(Hole hole : round.getHoles()) {
					Element holeElement = doc.createElement("Hole");
					
					if(hole.isFairwayHit() == null) {
						holeElement.setAttribute("fairwayHit", "");
					} else {
						String fairwayHitStr = hole.isFairwayHit() ? "true" : "false";
						holeElement.setAttribute("fairwayHit", fairwayHitStr);
					}
					
					if(hole.getFairwayHitDist() == null) {
						holeElement.setAttribute("fairwayHitDist", "");
					} else {
						String fairwayHitDistStr = Float.toString(hole.getFairwayHitDist());
						holeElement.setAttribute("fairwayHitDist", fairwayHitDistStr);
					}
					
					if(hole.isGreenHit() == null) {
						holeElement.setAttribute("greenHit", "");
					} else {
						String greenHitStr = hole.isGreenHit() ? "true" : "false";
						holeElement.setAttribute("greenHit", greenHitStr);
					}
					
					if(hole.getGreenHitDist() == null) {
						holeElement.setAttribute("greenHitDist", "");
					} else {
						String greenHitDistStr = Float.toString(hole.getGreenHitDist());
						holeElement.setAttribute("greenHitDist", greenHitDistStr);
					}
					
					if(hole.getPuttCount() == null) {
						holeElement.setAttribute("puttCount", "");
					} else {
						String puttCountStr = Integer.toString(hole.getPuttCount());
						holeElement.setAttribute("puttCount", puttCountStr);
					}
					
					roundElement.appendChild(holeElement);
				}
				
				root.appendChild(roundElement);
			}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(file);
			Source input = new DOMSource(doc);
			
			transformer.transform(input, output);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(File appDir) {
		File file = new File(appDir, name);
		if(!file.exists()) {
			return;
		}
		
		rounds = new ArrayList<Round>();
		
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			
			NodeList roundNodes = doc.getElementsByTagName("Round");
			for(int i = 0; i < roundNodes.getLength(); i++) {
				Node roundNode = roundNodes.item(i);
				
				String dateStr = roundNode.getAttributes()
					.getNamedItem("date")
					.getTextContent();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				Calendar date = Calendar.getInstance();
				date.setTime(sdf.parse(dateStr));
				
				Round round = new Round(date);
				
				NodeList holeNodes = roundNode.getChildNodes();
				for(int j = 0; j < holeNodes.getLength(); j++) {
					Node holeNode = holeNodes.item(j);
					Boolean fairwayHit = holeNode.getAttributes()
							.getNamedItem("fairwayHit")
							.getTextContent().equals("true");
					
					String fairwayHitDistStr = holeNode.getAttributes()
							.getNamedItem("fairwayHitDist")
							.getTextContent();
					Float fairwayHitDist = null;
					if(!fairwayHitDistStr.isEmpty()) {
						fairwayHitDist = Float.valueOf(fairwayHitDistStr);
					}
		
					Boolean greenHit = holeNode.getAttributes()
							.getNamedItem("greenHit")
							.getTextContent().equals("true");
					
					String greenHitDistStr = holeNode.getAttributes()
							.getNamedItem("greenHitDist")
							.getTextContent();
					Float greenHitDist = null;
					if(!greenHitDistStr.isEmpty()) {
						greenHitDist = Float.valueOf(greenHitDistStr);
					}
					
					String puttCountStr = holeNode.getAttributes()
							.getNamedItem("puttCount")
							.getTextContent();
					Integer puttCount = null;
					if(!puttCountStr.isEmpty()) {
						puttCount = Integer.valueOf(puttCountStr);
					}
					
					round.getHoles().set(j, new Hole(fairwayHit, fairwayHitDist, greenHit, greenHitDist, puttCount));
				}
				
				rounds.add(round);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

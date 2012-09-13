package Programm;

import java.io.File;

import org.w3c.dom.*;

import javax.xml.xpath.*;
import javax.xml.parsers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import GoupSales.Auction;
import GoupSales.Bid;
import GoupSales.Customer;
import GoupSales.Sale;

public class Simulator {
	//Singleton class of settings
	//Reads from settings XML file and sets all the variables
	
	private int maxAuctionsAtTime; //Maximum simultaneous sales
	private Map<String, Sale> sales = new HashMap<String, Sale>(); 
	private List<Customer> customers = new ArrayList<Customer>();
	private static Simulator settingsObj; //Singleton object of settings
	
	private Simulator(){
		try{
			File settingsXML = new File("settings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true); 
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(settingsXML);
			doc.getDocumentElement().normalize();
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//@maxAuctionAtATime");
			Object maxAuctions = expr.evaluate(doc, XPathConstants.NODESET);

			maxAuctionsAtTime = Integer.parseInt(((NodeList) maxAuctions).item(0).getNodeValue());
			
			{ //For memory efficiency
				Sale tmpSale;
				Customer tmpCustomer;
				String tmpName;
				String tmpType;
				float tmpInitialPrice;
				int tmpLiftTime;
				String tmpMail;
				String tmpWhileWaiting;
				String tmpProduct;
				float tmpMaxPrice;
								
				expr = xpath.compile("/system/sales/item");
				Object nodeSales = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nl = (NodeList) nodeSales;
				
				for(int i = 0; i < nl.getLength(); i++){ 
					//Get all active sales
					NamedNodeMap item = nl.item(i).getAttributes();
					tmpName = item.getNamedItem("name").getNodeValue();
					tmpInitialPrice = Float.parseFloat(item.getNamedItem("initPrice").getNodeValue());
					tmpLiftTime = Integer.parseInt(item.getNamedItem("liftTimeInSeconds").getNodeValue());
					tmpType = item.getNamedItem("type").getNodeValue();
					if(tmpType.equalsIgnoreCase("bid")){
						tmpSale = new Bid(tmpName, tmpInitialPrice, tmpLiftTime);
					}else{
						tmpSale = new Auction(tmpName, tmpInitialPrice, tmpLiftTime);
					}
					
					sales.put(tmpSale.getSaleName(), tmpSale);
				}
				
				expr = xpath.compile("/system/customers/client");
				Object nodeCustomers = expr.evaluate(doc, XPathConstants.NODESET);
				nl = (NodeList) nodeCustomers;
				
				for(int i = 0; i < nl.getLength(); i++){
					//Get all customers
					NamedNodeMap item = nl.item(i).getAttributes();
					tmpName = item.getNamedItem("name").getNodeValue();
					tmpMail = item.getNamedItem("mail").getNodeValue();
					tmpProduct = item.getNamedItem("product").getNodeValue();
					tmpWhileWaiting = item.getNamedItem("whileWaiting").getNodeValue();
					tmpMaxPrice = Float.parseFloat(item.getNamedItem("price").getNodeValue());
					tmpCustomer = new Customer(tmpName, tmpMail, tmpProduct, tmpMaxPrice, tmpWhileWaiting, sales);
					//customers.add(tmpCustomer);
				}
			}	
			
			for(Customer i : customers)	i.start();
			
			for( Map.Entry<String, Sale> eSet : sales.entrySet() ) eSet.getValue().start();

		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public static synchronized Simulator getSimulator(){
		if(settingsObj == null){
			settingsObj = new Simulator();
		}
		
		return settingsObj;
	}

	public int getMaxAuctionsAtTime() {
		return maxAuctionsAtTime;
	}

	public Map<String, Sale> getAllSales() {
		return sales;
	}
	
	public List<Customer> getAllCustomers() {
		return customers;
	}

	public Object clone() throws CloneNotSupportedException { 
		//In order the object can't be cloned
		throw new CloneNotSupportedException();
	}
}

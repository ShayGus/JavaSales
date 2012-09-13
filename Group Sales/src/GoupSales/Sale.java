package GoupSales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import LoggerPKG.SaleFormatter;



public abstract class Sale extends Thread{
	private String productName;
	private float initialPrice;
	private boolean allowedToStart = false;
	public Customer winningCustomer = null;
	protected boolean allCustomersUp = false;
	public List<IAllowedBidsEventListener> bidderListeners = new ArrayList<IAllowedBidsEventListener>();
	protected Logger logger;
	protected FileHandler logHandler;
	protected String[] logPerfs = new String[2];
	
	
	public Sale(String productName, float initialPrice) throws SecurityException, IOException{
		this.productName = productName;
		this.initialPrice = initialPrice;
		logger = Logger.getLogger(productName);
		logHandler = new FileHandler(productName + ".log");
		logHandler.setFormatter(new SaleFormatter());
		logger.addHandler(logHandler);
		logger.setUseParentHandlers(false);
		
		logPerfs[0] = this.getClass().getSimpleName();
		logPerfs[1] = "Sale Begin";
		logger.log(Level.INFO, "Ok", logPerfs);
	}
	
	public String getSaleName() {
		return productName;
	}
	
	public void setSaleName(String productName) {
		this.productName = productName;
	}
	
	public float getInitialPrice() {
		return initialPrice;
	}
	
	public void setInitialPrice(float initialPrice) {
		this.initialPrice = initialPrice;
	}
	
	public void addCustomer(Customer customer){
		bidderListeners.add(customer);
	}
	
	public String getProductName(){
		return this.productName;
	}
	
	public Customer getWinner(){
		return winningCustomer;
	}
	
	public boolean isAllowedToStart(){
		return allowedToStart;
	}
	
	protected void setAllowedToStart(){
		allowedToStart = true;
	}
	
	public void setAllUp(){
		allCustomersUp = true;
	}

}

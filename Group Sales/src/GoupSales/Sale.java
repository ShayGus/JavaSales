package GoupSales;

import java.util.ArrayList;
import java.util.List;



public abstract class Sale extends Thread{
	private String productName;
	private float initialPrice;
	private boolean allowedToStart = false;
	public Customer winningCustomer = null;
	protected boolean allCustomersUp = false;
	public List<IAllowedBidsEventListener> bidderListeners = new ArrayList<IAllowedBidsEventListener>();
	
	public Sale(String productName, float initialPrice){
		this.productName = productName;
		this.initialPrice = initialPrice;
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

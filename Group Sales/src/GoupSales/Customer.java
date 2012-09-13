package GoupSales;

import java.util.Map;
import java.util.Random;

public class Customer extends Thread implements IAllowedBidsEventListener{
	private String custName;
	private String eMail;
	private String actionWhileWaiting;
	private String product;
	private float maxPrice;
	private Map<String, Sale> sales;
	private Sale mySale;
	private Random rndUp = new Random();
	private float nextUP;
	private boolean ended = false;

	public Customer(String custName, String eMail, String product, float maxPrice, String actionWhileWaiting, Map<String, Sale> sales){
		this.custName = custName;
		this.seteMail(eMail);
		this.setProduct(product);
		this.setMaxPrice(maxPrice);
		this.setActionWhileWaiting(actionWhileWaiting);
		this.sales = sales;
		nextUP = rndUp.nextFloat();
		registerSale();
	}
	
	private void registerSale(){
		mySale = sales.get(product);
		mySale.addCustomer(this);
	}
	
	public String getCustomerName(){
		return new String(custName);
	}
	
	public String geteMail() {
		return new String(eMail);
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	public String getActionWhileWaiting() {
		return new String(actionWhileWaiting);
	}

	public void setActionWhileWaiting(String actionWhileWaiting) {
		this.actionWhileWaiting = actionWhileWaiting;
	}

	public String getProduct() {
		return new String(product);
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	@Override
	public void run(){
		while (!ended){
			System.out.println("Customer: " + custName + "is " + actionWhileWaiting);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void onAllowedToBid(AllowedToBidEvent allowedToBid) {
		System.out.println(this.getCustomerName() + " received allowed to bid.");
		if(allowedToBid.getSale().getClass().getSimpleName().equals("Auction")){
			if( ((Auction)(allowedToBid.getSale())).getCurrenPrice() + nextUP <= maxPrice && ((Auction)(allowedToBid.getSale())).getCurrentBider() != this){
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				((Auction)(allowedToBid.getSale())).onBidEvent(new BidEvent(this, ((Auction)(allowedToBid.getSale())).getCurrenPrice() + nextUP));
			}
			else if( ((Auction) (allowedToBid.getSale())).getCurrenPrice() + nextUP > maxPrice && ((Auction)(allowedToBid.getSale())).getCurrentBider() != this){
					System.out.println("Customer: " + custName + " is outbided");
			}
		}else{
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			((Bid)(allowedToBid.getSale())).onBidEvent(new BidEvent(this, maxPrice));
		}
	}

	@Override
	public void onAllBidsAreClosed(BidsClosedEvent clossedEvent) {
		if(clossedEvent.getWinner() == this){
			System.out.println("Winner: Customer " + custName);
		}
		ended = true;
	}
	
	
	
}

package GoupSales;

public class BidEvent {
	private Customer customer;
	private float bidPrice;
	
	public BidEvent(Customer customer, float bidPrice){
		this.customer = customer;
		this.bidPrice = bidPrice;
	}
	
	public Customer getCustomer(){
		return this.customer;
	}
	
	public float getBidPrice(){
		return this.bidPrice;
	}
	
	
}

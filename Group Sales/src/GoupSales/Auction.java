package GoupSales;

import java.io.IOException;

public class Auction extends Sale implements IBidEventListener, ITimerListener{
	
	private int liftTimeInSeconds;
	private int currentTime;
	private float currentPrice;
	private Customer currentBidder;
	private Timer timer;


	public Auction(String name, float initialPrice, int liftTimeInSeconds) throws SecurityException, IOException {
		super(name, initialPrice);
		this.liftTimeInSeconds = liftTimeInSeconds;
		currentPrice = initialPrice;
		timer = new Timer(1);
		timer.addListener(this);
		currentTime = liftTimeInSeconds;
	}
	
	
	public int getLiftTimeInSeconds() {
		return liftTimeInSeconds;
	}
	
	public void setLiftTimeInSeconds(int liftTimeInSeconds) {
		this.liftTimeInSeconds = liftTimeInSeconds;
	}
	
	@Override
	public void addCustomer(Customer customer){
		bidderListeners.add(customer);
		
	}
	
	@Override
	public void run(){
		if(bidderListeners.size() > 0){
			timer.start();
			for(IAllowedBidsEventListener e : bidderListeners) e.onAllowedToBid(new AllowedToBidEvent(this));
			while(currentTime > 0){
				try {
				sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			for(IAllowedBidsEventListener c : bidderListeners) c.onAllBidsAreClosed(new BidsClosedEvent(this, currentBidder));
			winningCustomer = currentBidder;
			System.out.println("Winner for: " + getProductName() + "  is: " + winningCustomer.getCustomerName());
			timer.end();
		}
	}
		
	public Customer getCurrentBider(){
		return currentBidder;
	}
	

	public float getCurrenPrice(){
		return currentPrice;
	}
	
	@Override
	public synchronized void onBidEvent(BidEvent bidEvent) {
		System.out.println("Receved bid from: " + bidEvent.getCustomer().getCustomerName() + " Price: " + bidEvent.getBidPrice());
		currentPrice = bidEvent.getBidPrice();
		currentBidder = bidEvent.getCustomer();
		currentTime = liftTimeInSeconds;
		
		for(IAllowedBidsEventListener e : bidderListeners) e.onAllowedToBid(new AllowedToBidEvent(this));
	}


	@Override
	public void onTimer(TimerEvent e) {
		currentTime -= 1;
	}

}

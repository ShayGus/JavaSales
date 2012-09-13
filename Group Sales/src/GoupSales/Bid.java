package GoupSales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;


public class Bid extends Sale implements IBidEventListener, ITimerListener{
	private int endTime;
	private List<BidEvent> allBids = new ArrayList<BidEvent>();
	private Timer timer;
	private boolean timeEnded = false;  
	private Customer winner;
	
	public Bid(String name, float initialPrice, int endTime) throws SecurityException, IOException {
		super(name, initialPrice);
		this.endTime = endTime;
		timer = new Timer(endTime);
		timer.addListener(this);
	}

	public void addCustomer(Customer customer){
		bidderListeners.add(customer);
	}


	@Override
	public synchronized void onBidEvent(BidEvent bidEvent) {
		System.out.println("Bid recevid: " + bidEvent.getCustomer().getCustomerName() + " Price: " + bidEvent.getBidPrice());
		allBids.add(bidEvent);
	}
	
	@Override 
	public void run(){
		System.out.println("Product: " + this.getProductName());
		if(bidderListeners.size() > 0){
			for(IAllowedBidsEventListener l : bidderListeners) l.onAllowedToBid(new AllowedToBidEvent(this));
			timer.start();
			System.out.println("Entered Wait");
			while(!timeEnded){
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			
			Collections.sort(allBids, new Comparator<BidEvent>() {
				public int compare (BidEvent b1, BidEvent b2) {
					return (int) (b1.getBidPrice() - b2.getBidPrice());
				}
			});
			
			winner = allBids.get(allBids.size()-1).getCustomer();
			for(IAllowedBidsEventListener l : bidderListeners) l.onAllBidsAreClosed(new BidsClosedEvent(this, winner));
			
		}
	}

	@Override
	public void onTimer(TimerEvent e) {
		System.out.println(getProductName() + ": Timer");
		timer.end();
		timeEnded = true;
	}
	
}

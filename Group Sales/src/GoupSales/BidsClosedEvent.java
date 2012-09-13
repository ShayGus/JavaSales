package GoupSales;

public class BidsClosedEvent {
	private Sale sale;
	private Customer winner;
	
	public BidsClosedEvent(Sale sale, Customer winner) {
		this.sale = sale;
		this.winner = winner;
	}
	
	Customer getWinner(){
		return this.winner;
	}
}

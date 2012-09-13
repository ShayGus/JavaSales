package GoupSales;

public class AllowedToBidEvent {
	private Sale sale;
	
	public AllowedToBidEvent(Sale sale) {
		this.sale = sale;
	}
	
	public Sale getSale(){
		return this.sale;
	}
}

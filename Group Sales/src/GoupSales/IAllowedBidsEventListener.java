package GoupSales;

public interface IAllowedBidsEventListener {
	void onAllowedToBid(AllowedToBidEvent allowedToBid);
	void onAllBidsAreClosed(BidsClosedEvent clossedEvent);
}
